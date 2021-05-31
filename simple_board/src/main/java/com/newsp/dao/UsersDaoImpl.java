/*
 * 2021_0528 : [회원가입] 카테고리 method 작성
 * */
package com.newsp.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

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
			System.out.println("session is null");
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
		System.out.println("dao >> " + nickname);
		int count = 0;
		if(sqlSession == null) {
			System.out.println("session is null");
		}else {
			count = sqlSession.selectOne("com.newsp.mapper.UsersMapper.signUpNickChk", nickname);	
		}
		System.out.println("dao count >> " + count);
		return count;
	}

}

