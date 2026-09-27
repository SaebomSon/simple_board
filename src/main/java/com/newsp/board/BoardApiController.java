package com.newsp.board;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.newsp.security.CurrentUser;
import com.newsp.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

	private final BoardService boardService;

	/** 게시글 수정 화면에서 기존 첨부파일 삭제 */
	@DeleteMapping("/api/attachments/{idx}")
	public Map<String, String> deleteAttachment(@PathVariable int idx, @CurrentUser User me) {
		boardService.deleteAttachment(idx, me);
		return Map.of("result", "ok");
	}

	/** 관리자 화면: 게시글의 신고 사유별 건수 */
	@GetMapping("/api/admin/posts/{idx}/reports")
	public List<Report> reports(@PathVariable int idx) {
		return boardService.reportSummary(idx);
	}
}
