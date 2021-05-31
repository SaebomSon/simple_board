/*
 * 2021_0528 : 회원가입 ajax api 작성
 * */

package com.newsp.board;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.newsp.service.UsersService;

@RestController
@RequestMapping("/ajax")
public class AjaxController {
	
	@Autowired
	private UsersService userService;
	
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
		System.out.println("닉네임 >> " + nickname);
		
		String message = userService.nickCheckMsg(nickname);
		System.out.println("message >> "+ message);
		
		return message;
	}
}
