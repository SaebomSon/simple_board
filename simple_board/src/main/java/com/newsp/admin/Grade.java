package com.newsp.admin;

import java.time.LocalDateTime;

import com.newsp.user.MemberLevel;

import lombok.Getter;
import lombok.Setter;

/** 관리자 승인 대기 중인 등급 변경 요청 */
@Getter
@Setter
public class Grade {

	/** type 값 */
	public static final int TYPE_UPGRADE = 1;
	public static final int TYPE_DOWNGRADE = 2;
	public static final int TYPE_SUSPEND = 3;

	private int idx;
	private int userIdx;
	private int type;
	private int updateLevel;
	private LocalDateTime writtenDate;
	private LocalDateTime approvalDate;
	private int status;

	// user
	private String nickname;

	public String getUpdateLevelName() {
		return MemberLevel.nameOf(updateLevel);
	}
}
