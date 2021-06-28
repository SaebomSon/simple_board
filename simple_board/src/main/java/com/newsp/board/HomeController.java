package com.newsp.board;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.newsp.service.UsersService;
import com.newsp.vo.BoardVO;
import com.newsp.vo.UsersVO;

@Controller
public class HomeController {
	@Autowired
	UsersService userService;
	@Autowired
	BoardService boardService;
	@Autowired
	AttachmentService attachService;
	
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
			session.setAttribute("status", null);
		}else {
			session.setAttribute("status", "success");
			List<UsersVO> list = userService.getUserInfo(user_id);
			for(UsersVO vo : list) {
				session.setAttribute("user_idx", vo.getIdx());
				session.setAttribute("nickname", vo.getNickname());
				session.setAttribute("level", vo.getLevel());
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
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 로그아웃
		session.invalidate();
		
		return "redirect:/";
	}
	
	@GetMapping("/boardType")
	public String getPageType(@RequestParam Integer type, @RequestParam Integer page, Model model) {
		/* 게시판 유형별 구분
		 * param : 게시판 type을 파라미터로 받음(1:leaf, 2:flower, 3:diamond)
		 * return : 각 타입에 맞는 게시글을 list로 전달
		 * */
		// 가져올 게시글 개수
		int count = 15;
		// 클릭한 시작 페이지
		int start = (page - 1) * count;
		// 전체 게시글 개수
		int allBoardCount = boardService.getCountAllBoard(type);
		// 마지막 페이지 수(전체 페이지 / 페이지당 게시글 개수)
		int lastPageNum = (int)Math.ceil((float)allBoardCount / count);
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
		model.addAttribute("type", type);
		
		// 현재 페이지 넘버
		model.addAttribute("active", page);
		// 페이지 블럭
		model.addAttribute("lastPage", lastPageNum);
		model.addAttribute("blockIdx", blockIdx);
		model.addAttribute("blockStart", blockStartPage);
		model.addAttribute("blockEnd", blockEndPage);
		
		return "boardList";
	}
	
	@GetMapping("/newContent")
	public String newBoard(@RequestParam Integer type, HttpServletRequest req) {
		/* 글 작성 페이지로 이동
		 * param : type : 페이지 type(1:leaf, 2:flower, 3:diamond)
		 * */
		HttpSession session = req.getSession();
		if(session != null) {		
			if(session.getAttribute("user_idx") == null) {
				return "signIn";
			}else {
				int userIdx = Integer.parseInt(session.getAttribute("user_idx").toString());
				session.setAttribute("user_idx", userIdx);
				session.setAttribute("type", type);
				return "writeContent";
			}
		}
		return "writeContent";
	}
	
	@PostMapping("/write")
	public String writeBoard(HttpServletRequest req) throws IllegalStateException, IOException {
		// 새 글 작성 시 첨부파일이 존재하는 경우, attachment table에 insert하고, 그 idx를 board 테이블에 다시 update
		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest)req;
		int userIdx = Integer.parseInt(multipart.getParameter("userIdx"));
		int type = Integer.parseInt(multipart.getParameter("type"));
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
		for(MultipartFile mf : fileList) {
			if(mf.isEmpty() == false) {
				fileName = mf.getOriginalFilename();
				if("".equals(fileName)) {
					break;
				}else {
					File file = new File(filePath, fileName);
					if(file.exists() == false) {
						file.mkdirs();
					}
					mf.transferTo(file);
				}
				int attachIdx = attachService.insertAttachment(boardIdx, fileName, filePath);
				attachmentIdxList += attachIdx + "&";	
			}
		}
		
		// update attachment_idx_list in board table
		if(!("".equals(fileName))) {
			attachmentIdxList = attachmentIdxList.substring(0, attachmentIdxList.length()-1);
			boardService.updateAttachIdx(boardIdx, attachmentIdxList);
		}
		// 해당 게시판 목록으로 return
		return "redirect:/boardType?type=" + type + "&page=1";
	}
	
	@GetMapping("/detail")
	public String openContent(@RequestParam Integer type, @RequestParam Integer page, @RequestParam Integer idx, Model model, HttpSession session) {
		/* 게시글 상세보기
		 * param : type: 게시판 타입, idx: 게시글 번호
		 * return : 상세보기 페이지로 return
		 * */
		if(session != null) {		
			if(session.getAttribute("user_idx") == null) {
				return "signIn";
			}
		}
		
		boardService.updateHitsCount(idx);
		BoardVO info = boardService.getBoardDetailInfo(idx);
		// 이미지 파일 가져오기
		if(info.getAttachment_idx_list() != null) {
			String attachment_idx_list = info.getAttachment_idx_list();
			String[] imageArr = attachment_idx_list.split("&");
			String imgFiles = "";
			for(String img : imageArr) {
				imgFiles += attachService.getAttachmentFile(idx, Integer.parseInt(img)) + "&";
			}
			model.addAttribute("images", imgFiles);
		}
		model.addAttribute("type", type);
		model.addAttribute("page", page);
		model.addAttribute("info", info);
		
		return "boardDetail";
	}
	
	@RequestMapping(value="/search", method= {RequestMethod.GET, RequestMethod.POST})
	public String searchKeyword(@RequestParam Integer type, @RequestParam Integer page, @RequestParam String option, @RequestParam String keyword, Model model) {
		/* 게시판 내 원하는 조건으로 게시글 검색하기
		 * param : type: 게시판 타입, option: 검색 조건, keyword: 검색어
		 * return : 검색 후 화면으로  return
		 * */
		
		// 가져올 게시글 개수
		int count = 15;
		// 클릭한 시작 페이지
		int start = (page - 1) * count;
		System.out.println("page >> " + page);
		// 전체 게시글 개수
		int afterSearchCount = boardService.countAfterSearch(type, option, keyword);
		// 마지막 페이지 수(전체 페이지 / 페이지당 게시글 개수)
		int lastPageNum = (int)Math.ceil((float)afterSearchCount / count);
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
		model.addAttribute("type", type);
		// 현재 페이지 넘버
		model.addAttribute("active", page);
		// 페이지 블럭
		model.addAttribute("lastPage", lastPageNum);
		model.addAttribute("blockIdx", blockIdx);
		model.addAttribute("blockStart", blockStartPage);
		model.addAttribute("blockEnd", blockEndPage);
		// 검색 결과가 없는 경우
		
		return "afterSearch";
	}

	
}
