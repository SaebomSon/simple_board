package com.newsp.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.newsp.common.BoardType;
import com.newsp.user.User;
import com.newsp.user.UserMapper;

import lombok.RequiredArgsConstructor;

/** 모든 화면(사이드바)에서 쓰는 로그인 회원 정보("me")와 게시판 목록을 model에 넣는다. */
@ControllerAdvice
@RequiredArgsConstructor
public class CurrentUserAdvice {

	private final UserMapper userMapper;

	@ModelAttribute("me")
	public User me(@AuthenticationPrincipal LoginUser loginUser) {
		if (loginUser == null) {
			return null;
		}
		return userMapper.findByIdx(loginUser.getIdx()).orElse(null);
	}

	/** 사이드바 게시판 메뉴 */
	@ModelAttribute("boardTypes")
	public BoardType[] boardTypes() {
		return BoardType.values();
	}
}
