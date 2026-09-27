package com.newsp.common;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimes {

	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy.MM.dd");

	private DateTimes() {
	}

	/** 오늘 작성된 글인지(목록의 'new' 표시용). */
	public static boolean isToday(LocalDateTime dateTime) {
		return dateTime != null && !dateTime.isBefore(LocalDate.now().atStartOfDay());
	}

	/** "방금 전", "3분 전", "5시간 전", "2일 전", 일주일이 지나면 "2026.09.27" */
	public static String ago(LocalDateTime dateTime) {
		return ago(dateTime, LocalDateTime.now());
	}

	static String ago(LocalDateTime dateTime, LocalDateTime now) {
		if (dateTime == null) {
			return "";
		}
		Duration elapsed = Duration.between(dateTime, now);
		if (elapsed.toMinutes() < 1) {
			return "방금 전";
		}
		if (elapsed.toHours() < 1) {
			return elapsed.toMinutes() + "분 전";
		}
		if (elapsed.toDays() < 1) {
			return elapsed.toHours() + "시간 전";
		}
		if (elapsed.toDays() < 7) {
			return elapsed.toDays() + "일 전";
		}
		return dateTime.format(DATE);
	}
}
