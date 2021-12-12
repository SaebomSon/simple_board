package com.newsp.vo;

import lombok.Data;

@Data
public class NoticeVO {
	private int idx;
	private int user_idx;
	private int type;
	private String title;
	private String content;
	private String written_date;
	private String modify_date;
	private int hits;
	//user
	private String nickname;
	private String level_image;
}
