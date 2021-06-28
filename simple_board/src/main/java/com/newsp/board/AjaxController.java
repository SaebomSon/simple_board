package com.newsp.board;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsp.service.MailSendService;
import com.newsp.service.UsersService;
import com.newsp.vo.UsersVO;

@RestController
@RequestMapping("/ajax")
public class AjaxController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private MailSendService sender;
	
	@PostMapping(value="/signUp/idCheck")
	public String idCheck(@RequestBody String id) {
		/* 회원가입 화면에서 아이디 중복 여부 ajax로 전달
		 * param : user가 입력한 id
		 * return : 중복 여부를 String으로 return
		 * */
		String message = userService.idCheckMsg(id);
		
		return message;
	}
	
	@PostMapping(value="/signUp/nickCheck")
	public String nickCheck(@RequestBody String nickname) {
		/* 회원가입 화면에서 닉네임 중복 여부 ajax로 전달
		 * param : user가 입력한 nickname
		 * return : 중복 여부를 String으로 return
		 * */
		String message = userService.nickCheckMsg(nickname);
		
		return message;
	}
	
	@PostMapping(value="/signUp/emailCheck")
	public String emailCheck(@RequestBody String email) {
		/* 회원가입 화면에서 이메일 중복 여부 ajax로 전달
		 * param : user가 입력한 email
		 * return : 중복 여부를 String으로 return
		 * */
		String message = userService.emailCheckMsg(email);
		
		return message;
		
	}
	
	@PostMapping("/signUp/sendEmail")
	public Map<String, String> sendEmail(@RequestBody Map<String, Object> info) {
		/* 회원가입 버튼을 누른 후 db에 user 정보를 insert하고 메일을 전송한 후 생성된 authKey를 update
		 * param : ajax로 넘어온 user info
		 * return : insert와 update가 잘 되었을 경우 결과를 map에 담아 return
		 * */
		String id = (String) info.get("id");
		String pw = (String) info.get("pw");
		String nickname = (String) info.get("nickname");
		String email = (String) info.get("email");

		UsersVO vo = new UsersVO();
		vo.setId(id);
		vo.setPassword(pw);
		vo.setNickname(nickname);
		vo.setEmail(email);
		// DB에 기본 정보 insert(id, pw, nick, email)
		userService.insertUserInfo(vo);
		// 이메일 발송
		String authKey = sender.sendAuthMail(vo.getEmail());
		// authKey 등록
		vo.setAuth_key(authKey);
		// DB에 authKey update
		userService.updateAuthKey(vo);

		Map<String, String> result = new HashMap<String, String>();
		if(vo.getEmail() != null && vo.getAuth_key() != null) {
			result.put("result", "ok");
		}

		return result;
	}
	
	@PostMapping("/signIn/login")
	public Map<String, String> loginCheck(@RequestBody Map<String, Object> user, HttpSession session) {
		/* 로그인 정보를 받아 check 후 로그인 여부 전달
		 * param : ajax로 넘어온 id,pw 정보(json)
		 * return : 결과값을 map에 담아 return
		 * */
		Map<String, String> result = new HashMap<String, String>();
		// 로그인 정보를 확인해 message값을 받아옴
		result = userService.loginCheckMsg(user);
		session.setAttribute("id", user.get("id"));
		return result;
	}
	
	
}
