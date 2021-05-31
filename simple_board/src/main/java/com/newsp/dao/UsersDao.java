/*
 * 2021_0528 : [회원가입] method 작성
 * */
package com.newsp.dao;

public interface UsersDao {
	// [회원가입] : 아이디 중복체크
	public Integer signupIdCheck(String id);
	// [회원가입] : 닉네임 중복체크
	public Integer signupNickCheck(String nickname);

}
