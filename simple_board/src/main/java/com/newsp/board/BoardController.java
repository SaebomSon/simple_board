package com.newsp.board;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.newsp.service.AttachmentService;
import com.newsp.service.BoardService;
import com.newsp.service.NoticeService;
import com.newsp.service.ReplyService;
import com.newsp.service.ReportService;
import com.newsp.vo.AttachmentVO;
import com.newsp.vo.BoardVO;
import com.newsp.vo.NoticeVO;
import com.newsp.vo.ReplyVO;
import com.newsp.vo.ReportVO;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private AttachmentService attachService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/boardType")
	public String getPageType(@RequestParam Integer type, @RequestParam Integer page, Model model,
			HttpServletRequest req) {
		/*
		 * 게시판 유형별 구분 
		 * param : 게시판 type을 파라미터로 받음(1:leaf, 2:flower, 3:diamond) 
		 * return : 각 타입에 맞는 게시글을 list로 전달
		 */
		HttpSession session = req.getSession();

		// 게시판 타입과 현재 페이지 session으로 저장
		session.setAttribute("page", page);

		// 가져올 게시글 개수
		int count = 15;
		// 클릭한 시작 페이지
		int start = (page - 1) * count;
		// 전체 게시글 개수
		int allBoardCount = boardService.getCountAllBoard(type);
		// 마지막 페이지 수(전체 페이지 / 페이지당 게시글 개수)
		int lastPageNum = (int) Math.ceil((float) allBoardCount / count);
		// 페이지 블럭 사이즈
		int blockSize = 3;
		// 블럭의 idx
		int blockIdx = (page - 1) / blockSize;
		// 블럭 start idx
		int blockStartPage = (blockIdx * blockSize) + 1;

		// 블럭 end idx
		int blockEndPage = blockStartPage + blockSize - 1;

		List<BoardVO> list = boardService.getBoardByDate(type, start, count);
		model.addAttribute("list", list);

		// 페이지 블럭
		model.addAttribute("lastPage", lastPageNum);
//		model.addAttribute("blockIdx", blockIdx);
		model.addAttribute("blockStart", blockStartPage);
		model.addAttribute("blockEnd", blockEndPage);
		model.addAttribute("type", type);

		// 오늘 날짜(new tag 표시 위해)
		LocalDateTime localDate = LocalDateTime.now();
		String today = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
		model.addAttribute("today", today);
		
		// 공지 보여주기
		List<NoticeVO> notice = noticeService.getNoticeInBoard(type);
		model.addAttribute("notice", notice);

		return "boardList";
	}

	@GetMapping("/newContent")
	public String newBoard(@RequestParam Integer type, HttpServletRequest req) {
		/*
		 * 글 작성 페이지로 이동 
		 * param : type:페이지 type(1:leaf, 2:flower, 3:diamond)
		 */
		HttpSession session = req.getSession();
		if (session != null) {
			if (session.getAttribute("userIdx") == null) {
				return "signIn";
			} else {
				int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
				session.setAttribute("user_idx", userIdx);
				session.setAttribute("type", type);
				return "writeContent";
			}
		}
		return "writeContent";
	}

	@PostMapping("/write")
	public String writeBoard(HttpServletRequest req, HttpSession session) throws IllegalStateException, IOException {
		// 새 글 작성 시 첨부파일이 존재하는 경우, attachment table에 insert하고, 그 idx를 board 테이블에 다시 update

		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
		int type = Integer.parseInt(session.getAttribute("type").toString());

		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) req;
		String subject = multipart.getParameter("subject");
		String title = multipart.getParameter("title");
		String content = multipart.getParameter("content");

		// 새 글 작성(insert into board)
		BoardVO board = new BoardVO();
		board.setUser_idx(userIdx);
		board.setType(type);
		board.setSubject(subject);
		board.setTitle(title);
		board.setContent(content);
		int boardIdx = boardService.insertNewContent(board);

		// 첨부파일 존재시 작성(insert into attachment)
		List<MultipartFile> fileList = multipart.getFiles("multiparts");
		String filePath = req.getSession().getServletContext().getRealPath("resources/image/").replace("\\", "/");
		String fileName = "";
		String attachmentIdxList = "";

		for (MultipartFile mf : fileList) {
			if (mf.isEmpty() == false) {
				fileName = mf.getOriginalFilename();
				if ("".equals(fileName)) {
					break;
				} else {
					File file = new File(filePath, fileName);
					if (file.exists() == false) {
						file.mkdirs();
					}
					mf.transferTo(file);
				}
				// insert attachment
				attachService.insertAttachment(boardIdx, fileName.trim(), filePath);
				// get attachmentIdx
				List<AttachmentVO> attachList = attachService.getAttachIdx(boardIdx);
				for (AttachmentVO attach : attachList) {
					int attachIdx = attach.getIdx();
					attachmentIdxList += attachIdx + "&";
				}
			}
		}

		// update attachment_idx_list in board table
		if (!("".equals(fileName))) {
			attachmentIdxList = attachmentIdxList.substring(0, attachmentIdxList.length() - 1);
			boardService.updateAttachIdx(boardIdx, attachmentIdxList);
		}
		// 해당 게시판 목록으로 return
		return "redirect:/boardType?type=" + type + "&page=1";
	}

	@GetMapping("/detail")
	public String openContent(@RequestParam Integer type, @RequestParam(required = false) Integer page, @RequestParam Integer idx, Model model, HttpSession session) {
		/*
		 * 게시글 상세보기 
		 * param : type: 게시판 타입, page: 게시판 페이지 number, idx: 게시글 번호 
		 * return : 상세보기 페이지로 return
		 */
		if (session != null) {
			if (session.getAttribute("userIdx") == null) {
				return "signIn";
			} else {
				session.setAttribute("userIdx", session.getAttribute("userIdx"));
			}
		}
		boardService.updateHitsCount(idx);
		BoardVO info = boardService.getBoardDetailInfo(idx);
		// 이미지 파일 가져오기
		if (info.getAttachment_idx_list() != null) {
			String attachment_idx_list = info.getAttachment_idx_list();
			String[] imageArr = attachment_idx_list.split("&");
			String imgFiles = "";
			for (String img : imageArr) {
				imgFiles += attachService.getAttachmentFile(idx, Integer.parseInt(img)) + "&";
			}
			model.addAttribute("images", imgFiles);
		}
		// 가져올 댓글 개수
		int count = 10;
		/// 전체 댓글 개수
		int totalReplyCount = replyService.getReplyCount(idx);
		// 전체 페이지 수(전체 데이터/ 페이지당 게시글 개수)
		int totalPageCount = (int) Math.ceil((float) totalReplyCount / count);
		// 페이지 블럭 사이즈
		int blockSize = 5;
		// 블럭의 idx
		int blockIdx = 0;
		// 블럭 start idx
		int blockStartPage = (blockIdx * blockSize) + 1;
		// 블럭 end idx
		int blockEndPage = blockStartPage + blockSize - 1;

		if (blockEndPage > totalPageCount)
			blockEndPage = totalPageCount;

		if (info.getReply_count() > 0) {
			// 댓글 list
			List<ReplyVO> replyList = replyService.getReplyList(idx, 0, count);
			model.addAttribute("replyInfo", replyList);
		}
		// report 정보
		List<ReportVO> reportList = reportService.getReportInfo(idx);
		String reportMessage = "";
		for(ReportVO report : reportList) {
			int reportUserIdx = report.getReport_user_idx();
			if(session.getAttribute("userIdx").equals(reportUserIdx)) {
				// 이미 신고한 게시물
				reportMessage = "done";
			}
		}
		model.addAttribute("reportMessage", reportMessage);
		

		// 댓글 count update
		int replyCount = replyService.getReplyCount(idx);
		boardService.updateReplyCount(idx, replyCount);
		// 오늘 날짜(new tag 표시 위해)
		LocalDateTime localDate = LocalDateTime.now();
		String today = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));

		model.addAttribute("idx", idx);
		model.addAttribute("info", info);
		model.addAttribute("today", today);
		model.addAttribute("type", type);

		// 댓글 페이지 블럭
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("blockStart", blockStartPage);
		model.addAttribute("blockEnd", blockEndPage);

		return "boardDetail";
	}

	@GetMapping("/modifyPage")
	public String modifyBoard(@RequestParam Integer type, @RequestParam Integer page, @RequestParam Integer idx,
			Model model, HttpSession session) {
		/*
		 * 게시글 수정 페이지로 이동 
		 * param : type:게시판 타입, page: 페이지, idx=boardIdx 
		 * return : 게시판 수정 페이지로 이동
		 */
		if (session != null) {
			if (session.getAttribute("userIdx") == null) {
				return "signIn";
			} else {
				session.setAttribute("userIdx", session.getAttribute("userIdx"));
			}
		}

		// 내 게시글 가져오기
		BoardVO info = boardService.getBoardDetailInfo(idx);

		// 이미지 파일 가져오기
		int imgCount = 0;
		if (info.getAttachment_idx_list() != null) {
			String attachment_idx_list = info.getAttachment_idx_list();
			String[] imageArr = attachment_idx_list.split("&");
			String imgFiles = "";
			for (String img : imageArr) {
				imgFiles += attachService.getAttachmentFile(idx, Integer.parseInt(img)) + "&";
				imgCount++;
			}
			model.addAttribute("images", imgFiles);
			model.addAttribute("imgCount", imgCount);
		}

		model.addAttribute("info", info);
		model.addAttribute("type", type);

		return "modifyBoard";
	}

	@RequestMapping(value = "/modify", method = { RequestMethod.POST, RequestMethod.GET })
	public String completeModify(HttpServletRequest req, HttpSession session, Model model) throws IllegalStateException, IOException {
		/*
		 * 내 게시글 수정하기 
		 * return : 해당 페이지로 return
		 */
		if (session != null) {
			if (session.getAttribute("userIdx") == null) {
				return "signIn";
			} else {
				session.setAttribute("userIdx", session.getAttribute("userIdx"));
			}
		}

		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) req;
		int boardIdx = Integer.parseInt(req.getParameter("idx"));
		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
		String subject = multipart.getParameter("subject");
		String title = multipart.getParameter("title");
		String content = multipart.getParameter("content");
		String[] orgFileNames = multipart.getParameterValues("org_fileName");

		// 기존에 있는 파일이름 가져오기
		if (orgFileNames != null) {
			for (String a : orgFileNames) {
				System.out.println("original file name : " + a);
			}
		}
		BoardVO board = new BoardVO();
		board.setIdx(boardIdx);
		board.setUser_idx(userIdx);
		board.setSubject(subject);
		board.setTitle(title);
		board.setContent(content);

		// 게시글 수정
		boardService.modifyMyBoard(board);

		// 첨부파일 수정
		List<MultipartFile> fileList = multipart.getFiles("multiparts");
		String filePath = req.getSession().getServletContext().getRealPath("resources/image/").replace("\\", "/");
		String fileName = "";
		String attachmentIdxList = "";

		// 새로 추가되는 첨부파일이 있을 때
		for (MultipartFile mf : fileList) {
			if (mf.isEmpty() == false) {
				fileName = mf.getOriginalFilename();
				if ("".equals(fileName)) {
					break;
				} else {
					File file = new File(filePath, fileName);
					if (file.exists() == false) {
						file.mkdirs();
					}
					mf.transferTo(file);
				}
				System.out.println("multipart file name : " + fileName);
				// insert attachment
				attachService.insertAttachment(boardIdx, fileName.trim(), filePath);
			}
		}

		// get attachmentIdx
		List<AttachmentVO> attachList = attachService.getAttachIdx(boardIdx);
		for (AttachmentVO attach : attachList) {
			System.out.println("attach idx > " + attach.getIdx());
			int attachIdx = attach.getIdx();
			attachmentIdxList += attachIdx + "&";
		}

		// update attachment_idx_list in board table
		if (!("".equals(fileName))) {
			attachmentIdxList = attachmentIdxList.substring(0, attachmentIdxList.length() - 1);
			boardService.updateAttachIdx(boardIdx, attachmentIdxList);
		}

		model.addAttribute("idx", boardIdx);

		return "completeModify";
	}

	@GetMapping("/delete")
	public String deleteBoard(@RequestParam Integer type, @RequestParam Integer idx, @RequestParam Integer user,
			HttpSession session) {
		/*
		 * 내가 쓴 게시글 삭제하기 / 게시글 내 댓글 삭제하기 
		 * param : idx: 게시글 idx, user: userIdx 
		 * return : 해당 게시판 첫번째 페이지로 return
		 */
		if (session != null) {
			if (session.getAttribute("userIdx") == null) {
				return "signIn";
			} else {
				session.setAttribute("userIdx", session.getAttribute("userIdx"));
			}
		}
		// 게시글에 있는 댓글 먼저 삭제(fk key 속성 때문)
		replyService.deleteAllReplyInBoard(idx);
		// 첨부파일 존재시 삭제하기
		attachService.deleteAllAttachment(idx);
		// 게시글 삭제
		boardService.deleteMyBoard(idx, user);

		return "redirect:boardType?type=" + type + "&page=1";
	}

	@GetMapping("/search")
	public String searchKeyword(@RequestParam Integer type, @RequestParam Integer page, Model model, HttpServletRequest req) {
		/*
		 * 게시판 내 원하는 조건으로 게시글 검색하기 
		 * param : type: 게시판 타입, option: 검색 조건, keyword: 검색어
		 * return : 검색 후 화면으로 return
		 */
		String option = req.getParameter("option");
		String keyword = req.getParameter("keyword");

		HttpSession session = req.getSession();

		if (session == null) {
			System.out.println("search session is null");
		}
		if (session != null) {
			session.setAttribute("option", option);
			session.setAttribute("keyword", keyword);
		}
		// 가져올 게시글 개수
		int count = 15;
		// 클릭한 시작 페이지
		int start = (page - 1) * count;
		// 전체 게시글 개수
		int afterSearchCount = boardService.countAfterSearch(type, option, keyword);
		// 마지막 페이지 수(전체 페이지 / 페이지당 게시글 개수)
		int lastPageNum = (int) Math.ceil((float) afterSearchCount / count);
		// 페이지 블럭 사이즈
		int blockSize = 3;
		// 블럭의 idx
		int blockIdx = (page - 1) / blockSize;
		// 블럭 start idx
		int blockStartPage = (blockIdx * blockSize) + 1;
		// 블럭 end idx
		int blockEndPage = blockStartPage + blockSize - 1;

		List<BoardVO> searchList = boardService.searchBoard(type, option, keyword, start, count);
		model.addAttribute("searchList", searchList);

		// 페이지 블럭
		model.addAttribute("lastPage", lastPageNum);
		model.addAttribute("blockIdx", blockIdx);
		model.addAttribute("blockStart", blockStartPage);
		model.addAttribute("blockEnd", blockEndPage);
		model.addAttribute("type", type);

		// 오늘 날짜(new tag 표시 위해)
		LocalDateTime localDate = LocalDateTime.now();
		String today = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
		model.addAttribute("today", today);

		// 검색 결과가 없는 경우

		return "afterSearch";
	}

	@GetMapping("/searchDetail")
	public String openContent_afterSearch(@RequestParam Integer type, @RequestParam Integer page, @RequestParam Integer idx, Model model, HttpSession session) {
		/*
		 * 게시글 상세보기 
		 * param : type: 게시판 타입, idx: 게시글 번호 
		 * return : 상세보기 페이지로 return
		 */
		if (session != null) {
			if (session.getAttribute("userIdx") == null) {
				return "signIn";
			} else {
				session.setAttribute("userIdx", session.getAttribute("userIdx"));
			}
		}

		boardService.updateHitsCount(idx);
		BoardVO info = boardService.getBoardDetailInfo(idx);
		// 이미지 파일 가져오기
		if (info.getAttachment_idx_list() != null) {
			String attachment_idx_list = info.getAttachment_idx_list();
			String[] imageArr = attachment_idx_list.split("&");
			String imgFiles = "";
			for (String img : imageArr) {
				imgFiles += attachService.getAttachmentFile(idx, Integer.parseInt(img)) + "&";
			}
			model.addAttribute("images", imgFiles);
		}
		model.addAttribute("info", info);
		model.addAttribute("type", type);

		return "boardDetail_afterSearch";
	}
	
	@GetMapping("/myBoard")
	public String myBoard(HttpSession session, Model model) {
		/*
		 * 내가 쓴 게시글 모아보기
		 * param : 
		 * return : 해당 페이지로 이동
		 */
		if (session != null) {
			if (session.getAttribute("userIdx") == null) {
				return "signIn";
			}
		}
		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
		List<BoardVO> myList = boardService.getMyBoard(userIdx);

		model.addAttribute("myList", myList);

		return "myBoard";
	}
	
	@PostMapping("/deleteMyBoard")
	public String deleteMyBoard(HttpServletRequest req, HttpSession session) {
		/*
		 * 선택한 게시글 삭제하기
		 * param : 
		 * return : 삭제 완료 후 다시 myBoard 페이지 load
		 */
		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
		String[] checkArr = req.getParameterValues("each");

		for (String each : checkArr) {
			int boardIdx = Integer.parseInt(each);
			// 해당 게시글에 첨부파일이 존재하면 모두 삭제
			attachService.deleteAllAttachment(boardIdx);
			// 해당 게시글에 댓글이 존재하면 모두 삭제
			replyService.deleteAllReplyInBoard(boardIdx);
			// 게시글 삭제
			boardService.deleteMyBoard(boardIdx, userIdx);
		}

		return "redirect:/myBoard";
	}

}
