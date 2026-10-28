package com.newsp.board;

import java.util.Arrays;

/**
 * 게시판 목록 정렬
 */
public enum SortOption {
	LATEST("latest", "최신순"),
	HITS("hits", "인기순");

	/**
	 * 값이 없거나 잘못된 값일 때 쓰는 기본 정렬
	 */
	public static final SortOption DEFAULT = LATEST;

	private final String param;
	private final String label;

	SortOption(String param, String label) {
		this.param = param;
		this.label = label;
	}

	/**
	 * URL 파라미터(?sort=hits) → enum. null이거나 모르는 값이면 기본값
	 */
	public static SortOption from(String param) {
		return Arrays.stream(values())
				.filter(s -> s.param.equals(param))
				.findFirst()
				.orElse(DEFAULT);
	}

	public String getParam() {
		return param;
	}

	public String getLabel() {
		return label;
	}
}
