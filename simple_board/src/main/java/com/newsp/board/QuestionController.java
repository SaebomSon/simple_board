package com.newsp.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.newsp.service.QuestionService;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/question")
	public String questionPage(HttpSession session) {
		/*
		 * 문의글 페이지로 이동
		 * return : session 만료시 로그인 페이지로 이동, 그 외에는 문의글 페이지로 이동
		 * */
		try {
			if (session != null) {
				if (session.getAttribute("userIdx") == null) {
					return "signIn";
				} else {
					session.setAttribute("userIdx", session.getAttribute("userIdx"));
					return "question";
				}
			}
		} catch (Exception e) {
			return "redirect:/signIn";
		}
		return "question";
	}
	
	@PostMapping("/insertQuestion")
	public String insertQuestion(HttpServletRequest req) {
		/*
		 * 문의글 작성하기
		 * return : main으로 이동
		 * */
		int userIdx = Integer.parseInt(req.getParameter("userIdx"));
		String subject = req.getParameter("subject");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
//		System.out.println("user_idx : " + userIdx + "\nsubject : " + subject + "\ntitle : " + title + "\ncontent : " + content);
		
		// insert
		questionService.insertQuestion(userIdx, subject, title, content);
		
		return "redirect:/main";
	}
	
}
