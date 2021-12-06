package com.newsp.vo;

import lombok.Data;

@Data
public class QuestionVO {
	private int idx;
	private int user_idx;
	private String subject;
	private String title;
	private String content;
	private String written_date;
	private int status;
}
