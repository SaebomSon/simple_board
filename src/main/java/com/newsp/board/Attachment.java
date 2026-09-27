package com.newsp.board;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attachment {
	private int idx;
	private int boardIdx;
	/** 저장된 파일 이름 (/uploads/{fileName}) */
	private String fileName;
	private String filePath;
	private LocalDateTime dateTime;
}
