package com.newsp.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardVO {
	private int idx;
	private int userIdx;
	private int type;
	private String subject;
	private String title;
	private String content;
	private String writtenDate;
	private int hits;
	private String attachmentIdxList;
	private int replyCount;
	private MultipartFile[] multipart;
}
