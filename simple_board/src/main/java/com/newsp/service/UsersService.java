/*
 * 2021_0528 : [회원가입]아이디, 닉네임 중복 체크
 * 2021_0601 : [회원가입]이메일 중복 체크, 회원 정보 입력
 * */
package com.newsp.service;

import java.util.Map;

import com.newsp.vo.UsersVO;

public interface UsersService {
	// [회원가입] : 아이디 중복  체크
	public String idCheckMsg(String id);
	// [회원가입] : 닉네임 중복 체크
	public String nickCheckMsg(String nickname);
	// [회원가입] : 이메일 중복 체크
	public String emailCheckMsg(String email);
	// [회원가입] : 회원 정보 입력
	public void insertUserInfo(UsersVO vo);
	// [회원가입] : update authKey
	public void updateAuthKey(UsersVO vo);
	// [회원가입] : update authStatus
	public void updateAuthStatus(Map<String, String> map);
}
