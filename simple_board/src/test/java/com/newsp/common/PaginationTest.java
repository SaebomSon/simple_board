package com.newsp.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PaginationTest {

	@Test
	void 첫_블럭() {
		Pagination p = new Pagination(1, 15, 100, 3);

		assertThat(p.offset()).isZero();
		assertThat(p.lastPage()).isEqualTo(7);
		assertThat(p.pages()).containsExactly(1, 2, 3);
		assertThat(p.hasPrevBlock()).isFalse();
		assertThat(p.hasNextBlock()).isTrue();
	}

	@Test
	void 마지막_블럭은_마지막_페이지까지만() {
		Pagination p = new Pagination(7, 15, 100, 3);

		assertThat(p.offset()).isEqualTo(90);
		assertThat(p.pages()).containsExactly(7);
		assertThat(p.hasPrevBlock()).isTrue();
		assertThat(p.hasNextBlock()).isFalse();
	}

	@Test
	void 글이_없어도_1페이지() {
		Pagination p = new Pagination(0, 10, 0, 5);

		assertThat(p.page()).isEqualTo(1);
		assertThat(p.lastPage()).isEqualTo(1);
		assertThat(p.pages()).containsExactly(1);
	}
}
