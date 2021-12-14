package com.newsp.board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newsp.service.AnswerService;
import com.newsp.service.QuestionService;
import com.newsp.vo.AnswerVO;
import com.newsp.vo.BoardVO;
import com.newsp.vo.QuestionVO;
import com.newsp.vo.ReplyVO;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;
	
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
		
		// insert
		questionService.insertQuestion(userIdx, subject, title, content);
		
		return "redirect:/main";
	}
	
	@GetMapping("/questionDetail")
	public String questionDetail(@RequestParam Integer idx, Model model, HttpSession session) {
		/*
		 * 문의글 상세히 보기
		 * */
		
		try {
			if (session != null) {
				if (session.getAttribute("userIdx") == null) {
					return "signIn";
				} else {
					session.setAttribute("userIdx", session.getAttribute("userIdx"));
					QuestionVO questionInfo = questionService.getQuestionInfo(idx);
					model.addAttribute("info", questionInfo);
					
					// 답변 존재시
					AnswerVO answerInfo = answerService.getAnswerInfoInQuestion(idx);
					model.addAttribute("answer", answerInfo);
					
					return "questionDetail";
				}
			}
		} catch (Exception e) {
			return "redirect:/signIn";
		}
		
		
		return "questionDetail";
	}
	
	@GetMapping("/getAnswer")
	public String getAnswerList(Model model, @RequestParam Integer idx, HttpSession session) {
		/*
		 * 답변 list 가져오기 
		 * param : idx : 게시판 idx
		*/
		
		QuestionVO questionInfo = questionService.getQuestionInfo(idx);
		model.addAttribute("info", questionInfo);
		
		// 답변 존재시
		AnswerVO answerInfo = answerService.getAnswerInfoInQuestion(idx);
		model.addAttribute("answer", answerInfo);
					
		
		return "answerAjax";
	}
	@GetMapping("/updateQuestionStatus")
	public String updatQuestionStatus(@RequestParam Integer idx) {
		/*
		 * 답변 완료된 문의글 status 1로 변경하기
		 * */
		questionService.updateStatus(idx);
		
		return "redirect:/admin";
	}
	
	@GetMapping("/myQuestion")
	public String myQuestion(HttpSession session, Model model) {
		/*
		 * 내가 쓴 질문글 모아보기
		 * param : 
		 * return : 해당 페이지로 이동
		 */
		if (session != null) {
			if (session.getAttribute("userIdx") == null) {
				return "signIn";
			}
		}
		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
		List<QuestionVO> myQuestionList = questionService.getMyQuestionList(userIdx);
		model.addAttribute("myQuestionList", myQuestionList);
		
		return "myQuestion";
	}
	
	@PostMapping("/deleteMyQuestion")
	public String deleteMyQuestion(HttpServletRequest req, HttpSession session) {
		/*
		 * 선택한 게시글 삭제하기
		 * param : 
		 * return : 삭제 완료 후 다시 myBoard 페이지 load
		 */
		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
		String[] checkArr = req.getParameterValues("each");

		for (String each : checkArr) {
			int questionIdx = Integer.parseInt(each);
			questionService.deleteMyQuestion(questionIdx);
//			// 해당 게시글에 첨부파일이 존재하면 모두 삭제
//			attachService.deleteAllAttachment(boardIdx);
//			// 해당 게시글에 댓글이 존재하면 모두 삭제
//			replyService.deleteAllReplyInBoard(boardIdx);
//			// 게시글 삭제
//			boardService.deleteMyBoard(boardIdx, userIdx);
		}
		
		return "redirect:/myQuestion";
	}
	
}
