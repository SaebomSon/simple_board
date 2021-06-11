/*
 * 2021_0528 : [회원가입]아이디, 닉네임 중복 체크
 * 2021_0601 : [회원가입]이메일 중복 체크, 회원 정보 입력
 * 2021_0602 : [로그인]
 * */
package com.newsp.dao;

import java.util.List;
import java.util.Map;

import com.newsp.vo.UsersVO;

public interface UsersDao {
	// [회원가입] : 아이디 중복체크
	public Integer signupIdCheck(String id);
	// [회원가입] : 닉네임 중복체크
	public Integer signupNickCheck(String nickname);
	// [회원가입] : 닉네임 중복체크
	public Integer signupEmailCheck(String email);
	// [회원가입] : 회원정보 입력
	public void insertUserInfo(UsersVO vo);
	// [회원가입] : authKey update
	public void updateAuthKey(UsersVO vo);
	// [회원가입] : 메일 인증 후 authStatus update
	public void updateAuthStatus(Map<String, String> map);
	// [로그인] : 아이디, 패스워드 체크 및 승인 여부에 따라
	public String checkLogin(Map<String, Object> map);
	// 회원 정보 가져오기
	public List<UsersVO> getUserInfo(String id);
	

}
