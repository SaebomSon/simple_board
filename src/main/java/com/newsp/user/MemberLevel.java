package com.newsp.user;

import java.util.Arrays;

public enum MemberLevel {
	ASSOCIATE_1(1, "준회원1"),
	ASSOCIATE_2(2, "준회원2"),
	REGULAR(3, "정회원"),
	EXCELLENT(4, "우수회원"),
	SPECIAL(5, "특별회원");

	private final int level;
	private final String displayName;

	MemberLevel(int level, String displayName) {
		this.level = level;
		this.displayName = displayName;
	}

	public static String nameOf(int level) {
		return Arrays.stream(values())
				.filter(l -> l.level == level)
				.map(l -> l.displayName)
				.findFirst()
				.orElse("");
	}
}
