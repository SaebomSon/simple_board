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
		 * param : user가 입력한 id
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
		 * param : user가 입력한 nickname
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
		 * param : user가 입력한 email
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
		/* [회원가입] : 회원 정보 insert
		 * param : 회원 정보를 담은 vo
		 * */
		usersDao.insertUserInfo(vo);
	}

	@Override
	public void updateAuthKey(UsersVO vo) {
		/* [회원가입] : 일치하는 email을 가진 user정보에 authKey를 update
		 * param : email과 authKey를 담은 vo
		 * */
		usersDao.updateAuthKey(vo);		
	}

	@Override
	public void updateAuthStatus(Map<String, String> map) {
		/* [회원가입] : 인증 메일 확인 후 email과 authKey를 비교 확인하여 일치하는 user정보의 authStatus를 update
		 * param : email과 authKey를 담은 vo
		 * */
		usersDao.updateAuthStatus(map);
	}

	@Override
	public Map<String, String> loginCheckMsg(Map<String, Object> map) {
		/* [로그인] : user의 id와 pw를 비교하고 auth_status의 값에 따른 상태를 check 
		 * param : id와 pw를 담은 vo
		 * return : auth_status값을 String으로 return 받아 그 값에 따라 로그인 여부를 결정하는 message를 return
		 * */
		String check = usersDao.checkLogin(map);
		
		Map<String, String> message = new HashMap<String, String>();
		if(check == null) {
			message.put("result", "empty");
		}else if("1".equals(check)) {
			message.put("result", "ok");
		}else if("0".equals(check)){
			message.put("result", "fail");
		}
		return message;
	}


}
