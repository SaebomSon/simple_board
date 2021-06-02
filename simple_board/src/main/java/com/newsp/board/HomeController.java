/*
 * 2021_0528 : 홈, 회원가입, 로그인 page mapping
 * 2021_0531 : 회원가입, 로그인 페이지와 main 페이지 mapping
 * 2021_0601 : 이메일 인증 후 로그인 페이지로 redirect
 * */
package com.newsp.board;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newsp.service.UsersService;

@Controller
public class HomeController {
	@Autowired
	UsersService userService;
	
	@GetMapping(value = "/")
	public String home() {
		// 홈화면
		return "home";
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
	public String main() {
		// 메인화면
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
	
	@PostMapping(value="/successLogin")
	public String afterLogin() {
		// 로그인 페이지에서 로그인 성공 후 메인페이지로 이동
		return "redirect:main";
	}
	
	
}
