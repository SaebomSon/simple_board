package com.newsp.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsp.service.AnswerService;
import com.newsp.service.AttachmentService;
import com.newsp.service.BoardService;
import com.newsp.service.MailSendService;
import com.newsp.service.ReplyService;
import com.newsp.service.ReportService;
import com.newsp.service.UsersService;
import com.newsp.vo.ReplyVO;
import com.newsp.vo.ReportVO;
import com.newsp.vo.UsersVO;

@RestController
@RequestMapping("/ajax")
public class AjaxController {
	
	@Autowired
	private UsersService userService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private AttachmentService attachService;
	@Autowired
	private MailSendService sender;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private ReportService reportService;
	
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
		String message = userService.nickCheckMsg(nickname);
		
		return message;
	}
	
	@PostMapping(value="/signUp/emailCheck")
	public String emailCheck(@RequestBody String email) {
		/* 회원가입 화면에서 이메일 중복 여부 ajax로 전달
		 * param : user가 입력한 email
		 * return : 중복 여부를 String으로 return
		 * */
		String message = userService.emailCheckMsg(email);
		
		return message;
		
	}
	
	@PostMapping("/signUp/sendEmail")
	public String sendEmail(@RequestBody Map<String, Object> info) {
		/* 회원가입 버튼을 누른 후 db에 user 정보를 insert하고 메일을 전송한 후 생성된 authKey를 update
		 * param : ajax로 넘어온 user info
		 * return : insert와 update가 잘 되었을 경우 결과를 map에 담아 return
		 * */
		String id = (String) info.get("id");
		String pw = (String) info.get("pw");
		String nickname = (String) info.get("nickname");
		String email = (String) info.get("email");

		UsersVO vo = new UsersVO();
		vo.setId(id);
		vo.setPassword(pw);
		vo.setNickname(nickname);
		vo.setEmail(email);
		// DB에 기본 정보 insert(id, pw, nick, email)
		userService.insertUserInfo(vo);
		// 이메일 발송
		String authKey = sender.sendAuthMail(vo.getEmail());
		// authKey 등록
		vo.setAuth_key(authKey);
		// DB에 authKey update
		userService.updateAuthKey(vo);
		
		if(vo.getEmail() != null && vo.getAuth_key() != null) {
			return "ok";
		}
		return "false";
	}
	
	@PostMapping("/signIn/login")
	public Map<String, String> loginCheck(@RequestBody Map<String, Object> user, HttpSession session) {
		/* 로그인 정보를 받아 check 후 로그인 여부 전달
		 * param : ajax로 넘어온 id,pw 정보(json)
		 * return : 결과값을 map에 담아 return
		 * */
		// 로그인 정보를 확인해 message값을 받아옴
		String message = userService.loginCheckMsg(user);
		session.setAttribute("id", user.get("id"));

		Map<String, String> result = new HashMap<String, String>();
		result.put("message", message);

		return result;
	}
	
	@PostMapping("/insertReply")
	public Map<String, Object> insertReply(@RequestBody Map<String, Object> reply) {
		/* 댓글 등록하기
		 * param : reply(boardIdx, userIdx, replyContent)
		 * return : 결과 메시지를 map에 담아 return
		 * */
		int boardIdx = Integer.parseInt(reply.get("board_idx").toString());
		int userIdx = Integer.parseInt(reply.get("user_idx").toString());
		String content = reply.get("content").toString();

		// 새 댓글 등록
		int replyIdx = replyService.insertReply(boardIdx, userIdx, content);
		// parent idx update
		replyService.updateParentIdx(boardIdx, replyIdx);
		// 전체 댓글 개수
		int totalReplyCount = replyService.getReplyCount(boardIdx);
		// reply_count update
		boardService.updateReplyCount(boardIdx, totalReplyCount);
		int count = 10;
		// 전체 페이지 수(전체 데이터/ 페이지당 게시글 개수)
		int totalPageCount = (int)Math.ceil((float)totalReplyCount / count);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "ok");
		result.put("pageNum", totalPageCount);
		
		return result;
	}
	
	@PostMapping("/modifyReply")
	public Map<String, String> modifyReply(@RequestBody Map<String, Object> modiReply){
		/* 댓글 수정하기
		 * param : modiReply (idx:reply idx, boardIdx, userIdx, content: 댓글 내용)
		 * return : 결과 메시지를 map에 담아 return
		 * */
		int idx = Integer.parseInt(modiReply.get("idx").toString());
		int boardIdx = Integer.parseInt(modiReply.get("board_idx").toString());
		int userIdx = Integer.parseInt(modiReply.get("user_idx").toString());
		
		String content = modiReply.get("content").toString();
		replyService.modifyReply(idx, boardIdx, userIdx, content);
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "modifyOk");
		
		return result;
	}
	
	@DeleteMapping("/deleteReply")
	public Map<String, Object> deleteReply(@RequestBody Map<String, Object> delReply){
		/* 댓글 삭제하기
		 * param : delReply(idx:댓글idx, boardIdx:게시판 idx, userIdx: user idx)
		 * return : 결과 메시지를 map에 담아 return
		 * */
		int idx = Integer.parseInt(delReply.get("idx").toString());
		int boardIdx = Integer.parseInt(delReply.get("board_idx").toString());
		int userIdx = Integer.parseInt(delReply.get("user_idx").toString());
		
		replyService.deleteReply(idx, boardIdx, userIdx);
		// 전체 댓글 개수
		int totalReplyCount = replyService.getReplyCount(boardIdx);
		// 댓글 수 업데이트
		boardService.updateReplyCount(boardIdx, totalReplyCount);
		
		int count = 10;
		// 전체 페이지 수(전체 데이터/ 페이지당 게시글 개수)
		int totalPageCount = (int)Math.ceil((float)totalReplyCount / count);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "deleteOk");
		result.put("pageNum", totalPageCount);
		
		return result;
	}
	
	@PostMapping("/insertMention")
	public Map<String, String> insertMention(@RequestBody Map<String, Object> mention){
		/* 대댓글 작성하기
		 * param : comment(boardIdx:게시판idx, userIdx:사용자idx, content:내용, parentIdx:모댓글idx, depth:대댓글 깊이)
		 * return : 결과 메시지를 map에 담아 return
		 * */
		int upperIdx = Integer.parseInt(mention.get("idx").toString());
		System.out.println("대댓글 입력시 parentIdx >> " + upperIdx);
		int userIdx = Integer.parseInt(mention.get("user_idx").toString());
		String content = mention.get("content").toString();
		
		// 모댓글 가져오기
		ReplyVO mentionVo = replyService.getParentReplyInfo(upperIdx);
		int boardIdx = mentionVo.getBoard_idx();
		int parentIdx = mentionVo.getParent_reply_idx();
		int depth = mentionVo.getReply_depth() + 1;
		
		// 대댓글 입력
		replyService.insertMention(boardIdx, userIdx, content, parentIdx, depth);
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "insertOk");
		
		return result;
	}
	
	@PostMapping("/delAttach")
	public Map<String, String> deleteAttachment(@RequestBody Map<String, Object> attach){
		/* 게시글 수정시 첨부파일 삭제하는 경우
		 * param : attach(idx: boardIdx, fileName: file name)
		 * return : 결과메시지를 map에 담아 return
		 * */
		int boardIdx = Integer.parseInt(attach.get("idx").toString());
		String fileName = attach.get("fileName").toString().trim();
		
		// 첨부파일 삭제
		attachService.deleteAttachment(boardIdx, fileName);
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "deleteAttachmentOk");
		
		return result;
	}
	
	@PostMapping("/modifyNickname")
	public Map<String, String> modifyNickname(@RequestBody Map<String, Object> user){
		/* 개인정보 수정 : 닉네임 수정
		 * param : user(idx: userIdx, nickname : nickname)
		 * return: 결과 메시지를 map에 담아 return
		 * */
		int userIdx = Integer.parseInt(user.get("idx").toString());
		String nickname = user.get("nickname").toString();
		System.out.println(userIdx);
		System.out.println(nickname);
		String message = userService.updateNickname(userIdx, nickname);
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", message);
		
		return result;
	}
	
	@PostMapping("/insertAnswer")
	public Map<String, String> insertAnswer(@RequestBody Map<String, Object> answer, HttpSession session) {
		/*
		 * 질문글에 답변 작성하기
		 * param : answer(userIdx: 관리자의 idx, questionIdx: 질문글의 idx, content: 답변 내용)
		 * return : 결과 메시지를 map에 담아 return
		 * */
		int userIdx = Integer.parseInt(session.getAttribute("userIdx").toString());
		int questionIdx = Integer.parseInt(answer.get("questionIdx").toString());
		String content = answer.get("content").toString();
		
		System.out.println("user idx : " + userIdx + "\nquestion idx : " + questionIdx + "\ncontent : " + content);
		// insert
		answerService.insertAnswer(userIdx, questionIdx, content);
			
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "insertAnswerOk");
		
		return result;
	}
	
	@PostMapping("/getReason")
	public Map<String, Object> getReportedReason(@RequestBody Integer bidx){
		
		List<ReportVO> reportList = reportService.getReportList(bidx);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("reportDetail", reportList);
		
		return result;
	}
	
}
