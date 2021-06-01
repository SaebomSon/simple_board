/*
 * 2021_0528 : 홈, 회원가입, 로그인 page mapping
 * 2021_0531 : 회원가입, 로그인 페이지와 main 페이지 mapping
 * */
package com.newsp.board;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
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
	
	@PostMapping(value="/completeJoin")
	public String afterJoin() {
		// 회원가입 페이지에서 가입 완료 후 메인페이지로 이동
		return "redirect:main";
	}
	
	@PostMapping(value="/successLogin")
	public String afterLogin() {
		// 로그인 페이지에서 로그인 성공 후 메인페이지로 이동
		return "redirect:main";
	}
	
	
}
