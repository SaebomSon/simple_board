package com.newsp.service;

import java.util.List;
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
	// [로그인] : 로그인 체크 결과를 message로 전달
	public Map<String, String> loginCheckMsg(Map<String, Object> map);
	// 회원정보 가져오기
	public List<UsersVO> getUserInfo(String id);
}
