package com.newsp.board;

import java.util.Arrays;

/**
 * 게시글 말머리. 폼에서는 code(C/Q/I)로 주고받고 DB에는 label이 저장된다(기존 데이터 호환).
 */
public enum Subject {
	CHAT("C", "사담"),
	QUESTION("Q", "질문"),
	INFO("I", "정보");

	public static final String NONE = "NONE";

	private final String code;
	private final String label;

	Subject(String code, String label) {
		this.code = code;
		this.label = label;
	}

	/** 폼 값(C/Q/I/NONE) → DB에 저장할 말머리. 없으면 null */
	public static String labelOf(String code) {
		return Arrays.stream(values())
				.filter(s -> s.code.equals(code))
				.map(s -> s.label)
				.findFirst()
				.orElse(null);
	}

	/** DB 말머리 → 폼 값 */
	public static String codeOf(String label) {
		return Arrays.stream(values())
				.filter(s -> s.label.equals(label))
				.map(s -> s.code)
				.findFirst()
				.orElse(NONE);
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
}
