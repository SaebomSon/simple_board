package com.newsp.common;

import java.util.Arrays;

/**
 * 게시판 종류. code는 DB board.type / notice.type 값이며,
 * 회원 등급(level)이 code 이상이어야 해당 게시판에 접근할 수 있다.
 * (v1의 Leaf / Flower / Diamond 게시판과 code가 같다)
 */
public enum BoardType {
	PLAZA(1, "광장", "bi-people", "누구나 편하게 모이는 곳"),
	LOUNGE(2, "라운지", "bi-cup-hot", "조금 더 가까워진 사람들의 대화"),
	ATTIC(4, "다락방", "bi-lamp", "아는 사람만 아는 아늑한 공간");

	private final int code;
	private final String label;
	private final String icon;
	private final String description;

	BoardType(int code, String label, String icon, String description) {
		this.code = code;
		this.label = label;
		this.icon = icon;
		this.description = description;
	}

	public static BoardType of(int code) {
		return Arrays.stream(values())
				.filter(type -> type.code == code)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다."));
	}

	/** URL 이름(/boards/plaza)으로 찾기 */
	public static BoardType fromSlug(String slug) {
		return Arrays.stream(values())
				.filter(type -> type.getSlug().equals(slug))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다."));
	}

	public boolean isAccessibleBy(int level) {
		return level >= code;
	}

	public int getCode() {
		return code;
	}

	/** 게시판 이름 */
	public String getLabel() {
		return label;
	}

	/** 게시판 소개 한 줄 */
	public String getDescription() {
		return description;
	}

	/** 입장 가능 등급 안내 */
	public String getAccessText() {
		return code == 1 ? "모든 회원" : "등급 " + code + " 이상";
	}

	/** Bootstrap Icons 클래스 */
	public String getIcon() {
		return icon;
	}

	/** 게시판 URL 이름 (/boards/plaza) */
	public String getSlug() {
		return name().toLowerCase();
	}

	/** 게시판별 색상 CSS 클래스 (board-plaza 등) */
	public String getKey() {
		return getSlug();
	}
}
