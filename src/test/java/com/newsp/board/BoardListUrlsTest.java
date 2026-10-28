package com.newsp.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BoardListUrlsTest {

	private static final BoardSearch NO_SEARCH = BoardSearch.of(1, null, null);
	private static final BoardSearch TITLE_SEARCH = BoardSearch.of(1, "title", "산책");

	@Test
	void 기본값은_주소에_붙이지_않는다() {
		BoardListUrls urls = new BoardListUrls("plaza", NO_SEARCH, SortOption.LATEST);

		assertThat(urls.page(1)).isEqualTo("/boards/plaza");
		assertThat(urls.sort("latest")).isEqualTo("/boards/plaza");
		assertThat(urls.clearSearch()).isEqualTo("/boards/plaza");
	}

	@Test
	void 기본값이_아닌_값만_붙인다() {
		BoardListUrls urls = new BoardListUrls("plaza", NO_SEARCH, SortOption.HITS);

		assertThat(urls.page(1)).isEqualTo("/boards/plaza?sort=hits");
		assertThat(urls.page(2)).isEqualTo("/boards/plaza?sort=hits&page=2");
		assertThat(urls.sort("latest")).isEqualTo("/boards/plaza");
	}

	@Test
	void 검색_중이면_검색_조건을_유지한다() {
		BoardListUrls urls = new BoardListUrls("plaza", TITLE_SEARCH, SortOption.HITS);

		assertThat(urls.page(3)).isEqualTo("/boards/plaza?option=title&keyword=%EC%82%B0%EC%B1%85&sort=hits&page=3");
		// 정렬을 바꾸면 검색은 유지하고 1페이지로
		assertThat(urls.sort("latest")).isEqualTo("/boards/plaza?option=title&keyword=%EC%82%B0%EC%B1%85");
		// 전체 보기는 검색만 지우고 정렬은 유지
		assertThat(urls.clearSearch()).isEqualTo("/boards/plaza?sort=hits");
	}

	@Test
	void 잘못된_정렬_값은_최신순으로() {
		BoardListUrls urls = new BoardListUrls("plaza", NO_SEARCH, SortOption.HITS);

		assertThat(urls.sort("abc")).isEqualTo("/boards/plaza");
	}
}
