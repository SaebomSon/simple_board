package com.newsp.board;

import java.util.Arrays;

import com.newsp.common.BadRequestException;

public enum ReportCategory {
	C("욕설, 비방, 차별, 혐오"),
	A("홍보, 영리 목적"),
	U("불법 정보"),
	O("음란, 청소년 유해"),
	P("개인 정보 노출, 유포, 거래"),
	E("도배, 스팸"),
	G("기타");

	private final String label;

	ReportCategory(String label) {
		this.label = label;
	}

	public static ReportCategory from(String code) {
		return Arrays.stream(values())
				.filter(c -> c.name().equals(code))
				.findFirst()
				.orElseThrow(() -> new BadRequestException("신고 사유를 선택하세요."));
	}

	public String getLabel() {
		return label;
	}
}
