package com.newsp.vo;

import lombok.Data;

@Data
public class ReplyVO {
	private int idx;
	private int board_idx;
	private int user_idx;
	private String content;
	private int parent_reply_idx;
	private int reply_seq;
	private int reply_depth;
	private String written_date;
	private String modify_date;
	// user
	String nickname;
	String level_image;
	// board
	String title;
	int type;
	int reply_count;
}
