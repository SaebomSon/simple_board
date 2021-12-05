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
	private int status;
}
