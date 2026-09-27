package com.newsp.board;

import java.util.Arrays;
import java.util.Optional;

/**
 * 게시판 검색 조건. 기존에는 파라미터를 그대로 SQL(${option})에 넣어 SQL 인젝션이 가능했다.
 * 이제 정해진 값만 허용하고, 실제 컬럼은 BoardMapper.xml의 &lt;choose&gt;에서 고른다.
 */
public enum SearchOption {
	TITLE("title", "제목"),
	CONTENT("content", "내용"),
	NICKNAME("nickname", "작성자");

	private final String param;
	private final String label;

	SearchOption(String param, String label) {
		this.param = param;
		this.label = label;
	}

	public static Optional<SearchOption> from(String param) {
		return Arrays.stream(values()).filter(o -> o.param.equals(param)).findFirst();
	}

	public String getParam() {
		return param;
	}

	public String getLabel() {
		return label;
	}
}
