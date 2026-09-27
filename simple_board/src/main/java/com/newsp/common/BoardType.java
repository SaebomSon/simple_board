package com.newsp.common;

import java.util.Arrays;

/**
 * 게시판 종류. code는 DB board.type / notice.type 값이며,
 * 회원 등급(level)이 code 이상이어야 해당 게시판에 접근할 수 있다.
 */
public enum BoardType {
	LEAF(1, "Leaf", "bi-tree"),
	FLOWER(2, "Flower", "bi-flower1"),
	DIAMOND(4, "Diamond", "bi-gem");

	private final int code;
	private final String label;
	private final String icon;

	BoardType(int code, String label, String icon) {
		this.code = code;
		this.label = label;
		this.icon = icon;
	}

	public static BoardType of(int code) {
		return Arrays.stream(values())
				.filter(type -> type.code == code)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다."));
	}

	public boolean isAccessibleBy(int level) {
		return level >= code;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public String getTitle() {
		return label + " Board";
	}

	/** Bootstrap Icons 클래스 */
	public String getIcon() {
		return icon;
	}

	/** 게시판별 색상 CSS 클래스 (board-leaf 등) */
	public String getKey() {
		return name().toLowerCase();
	}
}
