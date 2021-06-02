package com.newsp.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.newsp.vo.UsersVO;

public class UsersDaoImpl implements UsersDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Integer signupIdCheck(String id) {
		/*
		 * [회원가입] : 아이디 중복 체크
		 * param : user가 입력한 id
		 * return : 중복여부 int형으로 return
		 */
		int count = 0;
		if(sqlSession == null) {
			System.out.println("id check >> session is null");
		}else {
			count = sqlSession.selectOne("com.newsp.mapper.UsersMapper.signUpIdChk", id);	
		}
		return count;
	}

	@Override
	public Integer signupNickCheck(String nickname) {
		/*
		 * [회원가입] : 닉네임 중복 체크
		 * param : user가 입력한 nickname
		 * return : 중복여부 int형으로 return
		 */
		int count = 0;
		if(sqlSession == null) {
			System.out.println("nick check >> session is null");
		}else {
			count = sqlSession.selectOne("com.newsp.mapper.UsersMapper.signUpNickChk", nickname);
		}
		return count;
	}

	@Override
	public Integer signupEmailCheck(String email) {
		/*
		 * [회원가입] : 이메일 중복 체크
		 * param : user가 입력한 email
		 * return : 중복여부 int형으로 return
		 */
		int count = 0;
		if(sqlSession == null) {
			System.out.println("email check >> session is null");
		}else {
			count = sqlSession.selectOne("com.newsp.mapper.UsersMapper.signUpEmailChk", email);	
		}
		return count;
	}

	@Override
	public void insertUserInfo(UsersVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", vo.getId());
		map.put("pw", vo.getPassword());
		map.put("nickname", vo.getNickname());
		map.put("email", vo.getEmail());
		
		sqlSession.insert("com.newsp.mapper.UsersMapper.insertUserInfo", map);
		
	}

	@Override
	public void updateAuthKey(Map<String, String> map) {
		sqlSession.update("com.newsp.mapper.UsersMapper.updateAuthKey", map);
		
	}

	@Override
	public void updateAuthStatus(Map<String, String> map) {
		sqlSession.update("com.newsp.mapper.UsersMapper.updateAuthStatus", map);
		
	}

}

