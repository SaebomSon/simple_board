package com.newsp.vo;

import lombok.Data;

@Data
public class AnswerVO {
	private int idx;
	private int user_idx;
	private int question_idx;
	private String content;
	private String answered_date;
	
	// user
	private String nickname;
	private String level_image;
}
