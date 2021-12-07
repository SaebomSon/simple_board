package com.newsp.vo;

import lombok.Data;

@Data
public class NoticeVO {
	private int idx;
	private int user_idx;
	private String type;
	private String title;
	private String content;
	private String written_date;
}
