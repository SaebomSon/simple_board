package com.newsp.common;

import java.util.Arrays;

/**
 * 게시판 종류. code는 DB board.type / notice.type 값이며,
 * 회원 등급(level)이 code 이상이어야 해당 게시판에 접근할 수 있다.
 */
public enum BoardType {
	LEAF(1, "Leaf", "leaf-outline"),
	FLOWER(2, "Flower", "flower-outline"),
	DIAMOND(4, "Diamond", "diamond-outline");

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

	public String getIcon() {
		return icon;
	}
}
