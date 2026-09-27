package com.newsp.board;

import java.time.LocalDateTime;

import com.newsp.common.BoardType;
import com.newsp.common.DateTimes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
	private int idx;
	private int userIdx;
	private int type;
	/** 말머리 (사담/질문/정보, 없으면 null) */
	private String subject;
	private String title;
	private String content;
	private int hits;
	private int replyCount;
	private int reportCount;
	private LocalDateTime writtenDate;
	private LocalDateTime modifyDate;

	// user
	private String nickname;
	private String levelImage;

	public BoardType getBoardType() {
		return BoardType.of(type);
	}

	public boolean isNew() {
		return DateTimes.isToday(writtenDate);
	}

	public String getDisplayTitle() {
		return subject == null ? title : "[" + subject + "] " + title;
	}
}
