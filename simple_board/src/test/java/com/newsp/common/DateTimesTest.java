package com.newsp.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class DateTimesTest {

	private static final LocalDateTime NOW = LocalDateTime.of(2026, 9, 27, 15, 0);

	@Test
	void 상대_시간() {
		assertThat(DateTimes.ago(NOW.minusSeconds(30), NOW)).isEqualTo("방금 전");
		assertThat(DateTimes.ago(NOW.minusMinutes(3), NOW)).isEqualTo("3분 전");
		assertThat(DateTimes.ago(NOW.minusHours(5), NOW)).isEqualTo("5시간 전");
		assertThat(DateTimes.ago(NOW.minusDays(2), NOW)).isEqualTo("2일 전");
		assertThat(DateTimes.ago(NOW.minusDays(10), NOW)).isEqualTo("2026.09.17");
		assertThat(DateTimes.ago(null, NOW)).isEmpty();
	}
}
