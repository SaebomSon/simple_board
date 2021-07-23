package com.newsp.vo;

import lombok.Data;

@Data
public class ReplyVO {
	private int idx;
	private int board_idx;
	private int user_idx;
	private String content;
	private String written_date;
	private String modify_date;
	private int reply_seq;
	private int reply_depth;
	String nickname;
	String level_image;
}
