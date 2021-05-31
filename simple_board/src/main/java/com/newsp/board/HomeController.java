/*
 * 2021_0528 : simple_board 게시판
 * */
package com.newsp.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.newsp.vo.UsersVO;

@Controller
public class HomeController {
	
	@GetMapping(value = "/")
	public String home() {
		//메인페이지
		return "home";
	}
	
	@GetMapping(value="/signUp")
	public String signUp() {
		// 메인화면에서 회원가입 페이지로 이동
		return "signUp";
	}
	
	@GetMapping(value="/signIn")
	public String singIn() {
		// 메인화면에서 로그인 페이지로 이동
		return "signIn";
	}
	
	@GetMapping(value="/goToMain")
	public String main() {
		return "main";
	}
	
}
