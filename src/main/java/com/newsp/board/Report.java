package com.newsp.board;

import lombok.Getter;
import lombok.Setter;

/** 관리자 화면의 신고 사유별 집계 */
@Getter
@Setter
public class Report {
	private String category;
	private String content;
	private int count;
}
