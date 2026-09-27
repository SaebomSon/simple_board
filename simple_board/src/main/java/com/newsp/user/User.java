package com.newsp.user;

import java.time.LocalDateTime;

import com.newsp.common.BoardType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	/** user_status 값 */
	public static final int STATUS_ADMIN = 1;
	public static final int STATUS_MEMBER = 2;

	private int idx;
	private String id;
	private String password;
	private String nickname;
	private String email;
	private int level;
	private String levelImage;
	private LocalDateTime signupDate;
	private String authKey;
	private int authStatus;
	private int userStatus;
	private int warning;
	/** 비밀번호 재설정 토큰의 SHA-256 해시 (원문은 메일 링크에만 있다) */
	private String resetToken;
	private LocalDateTime resetTokenExpires;

	// 등업 대상 집계용 (getGradeCandidates)
	private int dayCount;
	private int boardCount;
	private int replyCount;

	public boolean isAdmin() {
		return userStatus == STATUS_ADMIN;
	}

	public boolean isEmailVerified() {
		return authStatus == 1;
	}

	/** 관리자 또는 일반 회원만 로그인 가능(그 외 값은 정지 회원). */
	public boolean isSuspended() {
		return userStatus != STATUS_ADMIN && userStatus != STATUS_MEMBER;
	}

	public boolean canAccess(BoardType type) {
		return isAdmin() || type.isAccessibleBy(level);
	}

	/** 아바타에 표시할 첫 글자 */
	public String getInitial() {
		return nickname == null || nickname.isEmpty() ? "?" : nickname.substring(0, 1);
	}

	public String getLevelName() {
		return MemberLevel.nameOf(level);
	}
}
