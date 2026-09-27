package com.newsp.notice;

import java.time.LocalDateTime;

import com.newsp.common.BoardType;
import com.newsp.common.DateTimes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notice {

	/** 모든 게시판에 노출되는 공지 */
	public static final int TYPE_ALL = 0;

	private int idx;
	private int userIdx;
	private int type;
	private String title;
	private String content;
	private int hits;
	private LocalDateTime writtenDate;
	private LocalDateTime modifyDate;

	// user
	private String nickname;
	private String levelImage;

	public String getTypeLabel() {
		return type == TYPE_ALL ? "전체 공지" : BoardType.of(type).getLabel();
	}

	public String getWrittenAgo() {
		return DateTimes.ago(writtenDate);
	}

	public boolean isNew() {
		return DateTimes.isToday(writtenDate);
	}
}
