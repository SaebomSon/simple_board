/*
 * 2021_0528 : 홈, 회원가입, 로그인 page mapping
 * 2021_0531 : 회원가입, 로그인 페이지와 main 페이지 mapping
 * 2021_0601 : 이메일 인증 후 로그인 페이지로 redirect
 * */
package com.newsp.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newsp.service.UsersService;
import com.newsp.vo.UsersVO;

@Controller
public class HomeController {
	@Autowired
	UsersService userService;
	
	@GetMapping(value = "/")
	public String home(HttpSession session) {
		// 홈화면
		session.invalidate();
		return "main";
	}
	
	@GetMapping(value="/signUp")
	public String signUp() {
		// 홈화면에서 회원가입 페이지로 이동
		return "signUp";
	}
	
	@GetMapping(value="/signIn")
	public String singIn() {
		// 홈화면에서 로그인 페이지로 이동
		return "signIn";
	}
	
	@RequestMapping(value="/main", method= {RequestMethod.GET, RequestMethod.POST})
	public String main(HttpServletRequest req) {
		// 메인화면
		HttpSession session = req.getSession();
		String user_id = session.getAttribute("id").toString();
		if(user_id == null) {
			System.out.println("user_id" + user_id);
			session.setAttribute("status", null);
		}else {
			session.setAttribute("status", "success");
			List<UsersVO> list = userService.getUserInfo(user_id);
			for(UsersVO vo : list) {
				String nickname = vo.getNickname();
				session.setAttribute("nickname", nickname);
				break;
			}
			
		}
		return "main";
	}
	
	@GetMapping("/signUp/confirm")
	public String signupConfirm(@RequestParam Map<String, String> map) {
		/* 인증메일에서 링크 클릭시, email과 authKey가 일치하면 authStatus 업데이트
		 * param : url에 있는 email, authKey 파라미터를 map으로 받음
		 * return : 링크 클릭 시 로그인 화면으로 redirect
		 * */

		userService.updateAuthStatus(map);
		
		return "redirect:/signIn";
	}
	
//	@RequestMapping(value="/main")
//	public ModelAndView getNickname() {
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("index");
//		String nickname = "안녕나야";
//		mv.addObject("nickname", nickname);
//		
//		return mv;
//	}
	@GetMapping("/leaf")
	public String leafPage() {
		// leaf등급 페이지로 이동
		return "leaf";
	}
	
	@GetMapping("/flower")
	public String flowerPage() {
		// flower등급 페이지로 이동
		return "flower";
	}
	
	@GetMapping("/diamond")
	public String diamondPage() {
		// diamond등급 페이지로 이동
		return "diamond";
	}
	
	
}
