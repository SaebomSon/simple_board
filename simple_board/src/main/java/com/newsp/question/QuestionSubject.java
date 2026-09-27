package com.newsp.question;

import java.util.Arrays;

public enum QuestionSubject {
	NONE("말머리 없음"),
	B("게시글 문의"),
	L("등급 문의"),
	R("신고글 문의"),
	G("기타");

	private final String label;

	QuestionSubject(String label) {
		this.label = label;
	}

	public static boolean isValid(String code) {
		return Arrays.stream(values()).anyMatch(s -> s.name().equals(code));
	}

	/** 제목 앞에 붙는 말머리. 없으면 빈 문자열 */
	public static String labelOf(String code) {
		return Arrays.stream(values())
				.filter(s -> s != NONE && s.name().equals(code))
				.map(s -> s.label)
				.findFirst()
				.orElse("");
	}

	public String getLabel() {
		return label;
	}
}
