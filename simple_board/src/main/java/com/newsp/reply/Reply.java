package com.newsp.reply;

import java.time.LocalDateTime;

import com.newsp.common.DateTimes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reply {
	private int idx;
	private int boardIdx;
	private int userIdx;
	private String content;
	/** 최상위 댓글의 idx (최상위 댓글은 자기 자신) */
	private int parentReplyIdx;
	private int replyDepth;
	private LocalDateTime writtenDate;
	private LocalDateTime modifyDate;

	// user
	private String nickname;
	private String levelImage;

	// board (내가 쓴 댓글 화면)
	private String title;
	private int type;
	private int boardReplyCount;

	public boolean isNew() {
		return DateTimes.isToday(writtenDate);
	}

	/** 수정된 댓글이면 수정일, 아니면 작성일 */
	public LocalDateTime getDisplayDate() {
		return modifyDate != null ? modifyDate : writtenDate;
	}
}
