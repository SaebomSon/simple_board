package com.newsp.board;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * 게시판 목록 화면의 링크 주소(페이지 번호, 정렬 토글, 전체 보기)를 만든다.
 * 기본값은 주소에 붙이지 않는다: 최신순이면 sort, 1페이지면 page, 검색 중이 아니면 option·keyword를 생략.
 * 예) /boards/plaza, /boards/plaza?sort=hits&page=2, /boards/plaza?option=title&keyword=산책
 */
public record BoardListUrls(String slug, BoardSearch search, SortOption sort) {

	/** 현재 검색·정렬을 유지한 채 다른 페이지로 */
	public String page(int page) {
		return build(search, sort, page);
	}

	/** 현재 검색은 유지하고 정렬만 바꿔서 1페이지로 (param: latest / hits) */
	public String sort(String param) {
		return build(search, SortOption.from(param), 1);
	}

	/** 검색을 지우고 현재 정렬로 1페이지 */
	public String clearSearch() {
		return build(BoardSearch.of(search.type(), null, null), sort, 1);
	}

	private String build(BoardSearch search, SortOption sort, int page) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/boards/" + slug);
		if (search.isSearching()) {
			builder.queryParam("option", search.option().getParam())
					.queryParam("keyword", search.keyword());
		}
		if (sort != SortOption.DEFAULT) {
			builder.queryParam("sort", sort.getParam());
		}
		if (page > 1) {
			builder.queryParam("page", page);
		}
		return builder.encode().toUriString();
	}
}
