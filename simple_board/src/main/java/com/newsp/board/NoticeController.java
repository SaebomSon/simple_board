package com.newsp.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newsp.service.NoticeService;
import com.newsp.vo.NoticeVO;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/notice")
	public String noticePage(HttpSession session) {
		/*
		 * 공지작성 페이지로 이동
		 * return : session 만료시 로그인 페이지로 이동, 그 외에는 공지 작성 페이지로 이동
		 * */
		try {
			if (session != null) {
				if (session.getAttribute("userIdx") == null) {
					return "signIn";
				} else {
					session.setAttribute("userIdx", session.getAttribute("userIdx"));
					return "notice";
				}
			}
		} catch (Exception e) {
			return "redirect:/signIn";
		}
		return "notice";
	}
	
	@GetMapping("/noticeDetail")
	public String getNoticeDetail(@RequestParam Integer idx, HttpSession session, Model model) {
		/*
		 * 공지 글 클릭한 경우 해당 공지글 가져오기
		 * param : idx: notice idx
		 * return : 해당 페이지로 이동
		 * */
		try {
			if (session != null) {
				if (session.getAttribute("userIdx") == null) {
					return "signIn";
				} else {
					session.setAttribute("userIdx", session.getAttribute("userIdx"));
					// 해당 공지글 불러오기
					NoticeVO noticeInfo = noticeService.getNoticeInfo(idx);
					// 공지글 조회수 업데이트
					noticeService.updateHitsCount(idx);
					
					model.addAttribute("info", noticeInfo);
					return "noticeDetail";
				}
			}
		} catch (Exception e) {
			return "redirect:/signIn";
		}
		
		return "noticeDetail";
	}
	
	@PostMapping("/insertNotice")
	public String insertNotice(HttpServletRequest req, HttpSession session) {
		/*
		 * 공지글 작성하기
		 * */
		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
		String type = req.getParameter("type");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		noticeService.insertNotice(userIdx, type, title, content);
		
		return "redirect:/admin";
	}
	
	@GetMapping("/modifyNotice")
	public String modifyNotice(@RequestParam Integer idx, HttpServletRequest req, Model model) {
		/*
		 * 공지글 수정 페이지로 이동
		 * param : idx: notice idx
		 * return : 수정 페이지로 이동
		 * */
		NoticeVO noticeInfo = noticeService.getNoticeInfo(idx);
		model.addAttribute("info", noticeInfo);
		
		return "modifyNotice";
	}
	
	@PostMapping("/modifyNoticeDone")
	public String modifyNoticeDone(HttpServletRequest req) {
		/*
		 * 공지글 수정 정보 전달하기
		 * */
		int idx = Integer.parseInt(req.getParameter("idx"));
		String type = req.getParameter("type");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		noticeService.updateNotice(idx, type, title, content);
		
		return "redirect:/admin";
	}
	
	@GetMapping("/deleteNotice")
	public String deleteNotice(@RequestParam Integer idx) {
		/*
		 * 공지글 삭제하기
		 * param : idx: notice idx
		 * */
		noticeService.deleteNotice(idx);
		return "redirect:/admin";
	}

}
