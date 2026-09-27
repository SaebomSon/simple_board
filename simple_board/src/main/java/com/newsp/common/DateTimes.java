package com.newsp.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class DateTimes {

	private DateTimes() {
	}

	/** 오늘 작성된 글인지(목록의 'new' 표시용). */
	public static boolean isToday(LocalDateTime dateTime) {
		return dateTime != null && !dateTime.isBefore(LocalDate.now().atStartOfDay());
	}
}
