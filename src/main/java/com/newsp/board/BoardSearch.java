package com.newsp.board;

import org.springframework.util.StringUtils;

/** 목록 조회 조건. option/keyword가 없으면 전체 목록. */
public record BoardSearch(int type, SearchOption option, String keyword) {

	public static BoardSearch of(int type, String option, String keyword) {
		if (!StringUtils.hasText(keyword)) {
			return new BoardSearch(type, null, null);
		}
		SearchOption searchOption = SearchOption.from(option).orElse(SearchOption.TITLE);
		return new BoardSearch(type, searchOption, keyword.trim());
	}

	public boolean isSearching() {
		return option != null;
	}
}
