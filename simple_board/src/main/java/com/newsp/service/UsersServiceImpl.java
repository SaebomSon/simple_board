package com.newsp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public String loginCheckMsg(Map<String, Object> map) {
		/* [로그인] : user의 id와 pw를 비교하고 auth_status의 값에 따른 상태를 check 
		 * param : id와 pw를 담은 vo
		 * return : auth_status값을 String으로 return 받아 그 값에 따라 로그인 여부를 결정하는 message를 return
		 * */
		List<UsersVO> checkList = usersDao.checkLogin(map);
		
		String message = "";
		for(UsersVO check : checkList) {
			if(check != null) {
				if(check.getAuth_status() == 0) {
					// 이메일 승인을 하지 않은 경우
					message = "fail";
				}else {
					if(check.getUser_status() == 1) {
						// 관리자 계정인 경우
						message = "admin";
					}else if(check.getUser_status() == 2) {
						// 일반 회원인 경우
						message = "ok";
					}else {
						// 정지 회원인 경우
						message = "stop";
					}
				}
			}else{
				// 회원 정보가 틀린 경우
				message = "empty";
				}
			}
		
		return message;
	}

	@Override
	public List<UsersVO> getUserInfo(String id) {
		List<UsersVO> list = usersDao.getUserInfo(id);
		String levelName = "";
		for(UsersVO u : list) {
			if(u.getLevel() == 1) {
				levelName = "준회원1";
			}else if(u.getLevel() == 2) {
				levelName = "준회원2";
			}else if(u.getLevel() == 3) {
				levelName = "정회원";
			}else if(u.getLevel() == 4) {
				levelName = "우수회원";
			}else if(u.getLevel() == 5) {
				levelName = "특별회원";
			}
			u.setLevel_name(levelName);
		}
		return list;
	}

	@Override
	public String updateNickname(int idx, String nickname) {
		int check = usersDao.signupNickCheck(nickname);
		String message = "";
		if(check == 1) {
			message = "fail";
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idx", idx);
			map.put("nickname", nickname);
			usersDao.updateNickname(map);
			message = "ok";
		}
		return message;
	}

	@Override
	public void updatePassword(int idx, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idx", idx);
		map.put("password", password);
		usersDao.updatePassword(map);
	}

	@Override
	public void deleteMyAccount(int idx) {
		usersDao.deleteMyAccount(idx);
	}


}
