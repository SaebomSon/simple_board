/*
 * 2021_0528 : [회원가입] 카테고리 method 작성
 * */
package com.newsp.service;

public interface UsersService {
	// [회원가입] : 아이디 중복  체크
	public String idCheckMsg(String id);
	// [회원가입] : 닉네임 중복 체크
	public String nickCheckMsg(String nickname);
}
