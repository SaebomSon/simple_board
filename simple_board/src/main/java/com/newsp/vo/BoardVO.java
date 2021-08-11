package com.newsp.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardVO {
	private int idx;
	private int user_idx;
	private int type;
	private String subject;
	private String title;
	private String content;
	private int hits;
	private String attachment_idx_list;
	private int reply_count;
	private String written_date;
	private String modify_date;
	private MultipartFile[] multipart;
	private String nickname;
	private String level_image;
}
