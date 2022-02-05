package com.newsp.board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newsp.service.BoardService;
import com.newsp.service.GradeService;
import com.newsp.service.NoticeService;
import com.newsp.service.QuestionService;
import com.newsp.service.ReplyService;
import com.newsp.service.ReportService;
import com.newsp.service.UsersService;
import com.newsp.vo.BoardVO;
import com.newsp.vo.ReplyVO;
import com.newsp.vo.UsersVO;

@Controller
public class HomeController {

	@Autowired
	private UsersService userService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private GradeService gradeService;

	@GetMapping(value = "/")
	public String home(HttpSession session, Model model) {
		// 홈화면
//		session.invalidate();

		// 메인 화면에 보여줄 list 가져오기
		model.addAttribute("newestList", boardService.getListNewestInMain());
		model.addAttribute("hitsList", boardService.getListOrderbyHitsCount());
		model.addAttribute("replyList", boardService.getListOrderbyReplyCount());

		return "main";
	}

	@GetMapping("/signUp")
	public String signUp() {
		// 홈화면에서 회원가입 페이지로 이동
		return "signUp";
	}

	@GetMapping("/signIn")
	public String singIn() {
		// 홈화면에서 로그인 페이지로 이동
		return "signIn";
	}

	@RequestMapping(value = "/main", method = { RequestMethod.GET, RequestMethod.POST })
	public String main(HttpServletRequest req, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();

		try {
			if (principal != null) {
				// 로그인 후 메인화면
				HttpSession session = req.getSession();
				String userId = auth.getName();
				// 비로그인 상태
				if (userId == null) {
					session.setAttribute("status", "null");
				} else {
					session.setAttribute("status", "success");
					List<UsersVO> list = userService.getUserInfo(userId);
					for (UsersVO vo : list) {
						// sidebar에 들어가는 정보
						session.setAttribute("userIdx", vo.getIdx());
						session.setAttribute("nickname", vo.getNickname());
						session.setAttribute("level", vo.getLevel());
						break;
					}
				}
			}

		} catch (Exception e) {
			return "signIn";
		}
		// 메인 화면에 보여줄 list 가져오기
		model.addAttribute("newestList", boardService.getListNewestInMain());
		model.addAttribute("replyList", boardService.getListOrderbyReplyCount());
		model.addAttribute("hitsList", boardService.getListOrderbyHitsCount());

		return "main";
	}

	// 관리자 페이지
	@GetMapping("/admin")
	public String admin(HttpSession session, Model model) {
		try {
			String userId = session.getAttribute("id").toString();
			if (session != null) {
				if (userId != null) {
					session.setAttribute("status", "admin");
					List<UsersVO> list = userService.getUserInfo(userId);
					for (UsersVO vo : list) {
						session.setAttribute("userIdx", vo.getIdx());
						session.setAttribute("level", vo.getLevel());
						break;
					}
					// 신고글 list
					List<BoardVO> blist = boardService.getBoardListOverReportCountTen();
					model.addAttribute("reportedBoard", blist);
					int boardIdx;
					for (BoardVO b : blist) {
						boardIdx = b.getIdx();
					}
					// 문의글 list
					model.addAttribute("question", questionService.getQuestionList());
					// 공지글 list
					model.addAttribute("notice", noticeService.getNoticeList());
					// 등업글
					model.addAttribute("gradeTypeOne", gradeService.getGradeList(1));
					model.addAttribute("gradeTypeTwo", gradeService.getGradeList(2));
					model.addAttribute("gradeTypeThree", gradeService.getGradeList(3));
				}
			}
		} catch (Exception e) {
			return "redirect:/signIn";
		}

		return "admin";
	}

	@GetMapping("/signUp/confirm")
	public String signupConfirm(@RequestParam Map<String, String> map) {
		/*
		 * 인증메일에서 링크 클릭시, email과 authKey가 일치하면 authStatus 업데이트 param : url에 있는 email,
		 * authKey 파라미터를 map으로 받음 return : 링크 클릭 시 로그인 화면으로 redirect
		 */
		userService.updateAuthStatus(map);

		return "redirect:/signIn";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {

		return "redirect:/";
	}

	@GetMapping("/quit")
	public String quitBoard(HttpSession session) {
		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());

		userService.deleteMyAccount(userIdx);
		// 참조 테이블 확인해야함

		return "redirect:/";
	}

	@GetMapping("/getReply")
	public String getReplyList(Model model, @RequestParam Integer idx, @RequestParam Integer pageNum) {
		/*
		 * 댓글 list 가져오기 param : idx : 게시판 idx, pageNum : 현재 페이지 숫자 return :
		 * replyAjax.jsp 페이지로 return
		 */
		// 가져올 댓글 개수
		int count = 10;
		// 클릭한 댓글 페이지
		int start = (pageNum - 1) * count;
		// 전체 댓글 개수
		int totalReplyCount = replyService.getReplyCount(idx);
		// 전체 페이지 수(전체 데이터/ 페이지당 게시글 개수)
		int totalPageCount = (int) Math.ceil((float) totalReplyCount / count);
		// 페이지 블럭 사이즈
		int blockSize = 5;
		// 블럭의 idx
		int blockIdx = (pageNum - 1) / blockSize;
		// 블럭 start idx
		int blockStartPage = (blockIdx * blockSize) + 1;
		// 블럭 end idx
		int blockEndPage = blockStartPage + blockSize - 1;

		if (blockEndPage > totalPageCount)
			blockEndPage = totalPageCount;
		BoardVO info = boardService.getBoardDetailInfo(idx);

		if (info.getReply_count() > 0) {
			// 댓글 list
			List<ReplyVO> replyList = replyService.getReplyList(idx, start, count);
			model.addAttribute("replyInfo", replyList);
		}

		LocalDateTime localDate = LocalDateTime.now();
		String today = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));

		model.addAttribute("info", info);
		model.addAttribute("today", today);

		// 페이지 블럭
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("blockStart", blockStartPage);
		model.addAttribute("blockEnd", blockEndPage);
		model.addAttribute("activePage", pageNum);

		return "replyAjax";
	}

	@GetMapping("/profile")
	public String myProfile(HttpSession session, Model model) {
		/*
		 * 개인정보수정 페이지로 이동하기 param : return : 해당 페이지로 return
		 */
		String user_id = session.getAttribute("id").toString();
		System.out.println(user_id);
		List<UsersVO> userList = userService.getUserInfo(user_id);

		model.addAttribute("userList", userList);

		return "profile";
	}

	@PostMapping("/modifyPassword")
	public String updatePassword(HttpServletRequest req) {
		/*
		 * 개인정보 수정 : 비밀번호 수정하기 param : return : profile 페이지로 redirect
		 */
		String password = req.getParameter("password");
		int idx = Integer.parseInt(req.getParameter("idx").toString());

		userService.updatePassword(idx, password);

		return "redirect:/profile";
	}

	@GetMapping("/myReply")
	public String myReply(HttpSession session, Model model) {
		/*
		 * 내가 쓴 댓글 모아보기 param : return : 해당 페이지로 이동
		 */
		if (session != null) {
			if (session.getAttribute("userIdx") == null) {
				return "signIn";
			}
		}
		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
		List<ReplyVO> myList = replyService.getMyReply(userIdx);

		model.addAttribute("myList", myList);

		return "myReply";
	}

	@PostMapping("/deleteMyReply")
	public String deleteMyReply(HttpServletRequest req, HttpSession session) {
		/*
		 * 선택한 댓글 삭제하기 param : return : 삭제 후 다시 myReply 페이지 load
		 */
		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
		int boardIdx = Integer.parseInt(req.getParameter("boardIdx"));
		String[] checkArr = req.getParameterValues("each");

		for (String each : checkArr) {
			System.out.println(each);
			int idx = Integer.parseInt(each);
			replyService.deleteReply(idx, boardIdx, userIdx);
		}

		return "redirect:/myReply";
	}

	@RequestMapping(value = "/report", method = { RequestMethod.GET, RequestMethod.POST })
	public String reportBoard(HttpServletRequest req, @RequestParam Integer type, @RequestParam Integer page,
			@RequestParam Integer idx) {
		/*
		 * 게시글 신고하기 param : type: 게시판 type, page: 게시글의 현재 페이지, idx: 게시판 idx return : 현재
		 * 게시글로 redirect
		 */
		int userIdx = Integer.parseInt(req.getParameter("userIdx"));
		String reportCategory = req.getParameter("category");
		String reportContent = "";

		if ("G".equals(reportCategory)) {
			reportContent = req.getParameter("etc");
		} else {
			reportContent = req.getParameter("content");
		}

		// insert report
		reportService.insertReport(idx, reportCategory, reportContent, userIdx);
		// update report_count
		int reportCount = reportService.getReportCount(idx);
		boardService.updateReportCount(idx, reportCount);

		return "redirect:/detail?type=" + type + "&page=" + page + "&idx=" + idx;
	}

	@GetMapping("/upgradeLevel")
	public String upgradeUserLevel(@RequestParam Integer idx) {
		/*
		 * 사용자 등업하기 param : idx: 사용자 idx return : 결과 메시지를 map에 담아 return
		 */
		System.out.println("user idx : " + idx);
		if (idx != 0) {
			// user level update
			userService.upgradeUserLevel(idx);
			// grade status update
			gradeService.switchGradeStatus(idx);

		}
		return "redirect:/admin";
	}

}
