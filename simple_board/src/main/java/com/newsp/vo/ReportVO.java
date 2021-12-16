package com.newsp.vo;

import lombok.Data;

@Data
public class ReportVO {
	private int idx;
	private int board_idx;
	private String category;
	private String content;
	private int report_user_idx;
	private String reported_date;
	// count
	private int count;
	// board
	private int type;
	private String title;
	// user
	private String nickname;
}
