package com.newsp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.UsersDao;
import com.newsp.dao.UsersDaoImpl;
import com.newsp.vo.UsersVO;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	private UsersDaoImpl usersDao;

	@Override
	public String idCheckMsg(String id) {
		/* [회원가입] : 아이디 중복 체크
		 * paran : user가 입력한 id
		 * return : id 중복 여부 String으로 return
		 * */
		String message = "";
		if(usersDao == null) {
			System.out.println("dao null");
		}
		int check = 0;
		if(usersDao != null) {
			check = usersDao.signupIdCheck(id);		
		}
		
		if(check == 0) {
			message = "ok";
		}else {
			message = "fail";
		}
		return message;
	}

	@Override
	public String nickCheckMsg(String nickname) {
		/* [회원가입] : 닉네임 중복 체크
		 * paran : user가 입력한 nickname
		 * return : nickname 중복 여부 String으로 return
		 * */
		String message = "";
		if(usersDao == null) {
			System.out.println("dao null");
		}
		int check = 0;
		if(usersDao != null) {
			check = usersDao.signupNickCheck(nickname);		
		}
		
		if(check == 0) {
			message = "ok";
		}else {
			message = "fail";
		}
		return message;
	}

	@Override
	public String emailCheckMsg(String email) {
		/* [회원가입] : 닉네임 중복 체크
		 * paran : user가 입력한 email
		 * return : email 중복 여부 String으로 return
		 * */
		String message = "";
		if(usersDao == null) {
			System.out.println("dao null");
		}
		int check = 0;
		if(usersDao != null) {
			check = usersDao.signupEmailCheck(email);
		}
		
		if(check == 0) {
			message = "ok";
		}else {
			message = "fail";
		}
		return message;
	}

	@Override
	public void insertUserInfo(UsersVO vo) {
		usersDao.insertUserInfo(vo);
	}

	@Override
	public void updateAuthKey(UsersVO vo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", vo.getEmail());
		map.put("authKey", vo.getAuthKey());
		usersDao.updateAuthKey(map);		
	}

	@Override
	public void updateAuthStatus(Map<String, String> map) {
		usersDao.updateAuthStatus(map);
	}


}
