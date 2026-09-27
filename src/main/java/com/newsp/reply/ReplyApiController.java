package com.newsp.reply;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newsp.security.CurrentUser;
import com.newsp.user.User;

import lombok.RequiredArgsConstructor;

/**
 * 댓글 ajax API. 작성자는 요청 본문이 아닌 로그인 정보로 정한다
 * (기존에는 user_idx를 클라이언트가 보내서 다른 사람 이름으로 작성·삭제가 가능했다).
 */
@RestController
@RequiredArgsConstructor
public class ReplyApiController {

	private final ReplyService replyService;

	public record ContentRequest(String content) {
	}

	@PostMapping("/api/posts/{boardIdx}/replies")
	public Map<String, Integer> write(@PathVariable int boardIdx, @RequestBody ContentRequest request,
			@CurrentUser User me) {
		return Map.of("lastPage", replyService.write(boardIdx, request.content(), me));
	}

	@PostMapping("/api/replies/{idx}/mentions")
	public Map<String, String> mention(@PathVariable int idx, @RequestBody ContentRequest request,
			@CurrentUser User me) {
		replyService.writeMention(idx, request.content(), me);
		return Map.of("result", "ok");
	}

	@PutMapping("/api/replies/{idx}")
	public Map<String, String> modify(@PathVariable int idx, @RequestBody ContentRequest request,
			@CurrentUser User me) {
		replyService.modify(idx, request.content(), me);
		return Map.of("result", "ok");
	}

	@DeleteMapping("/api/replies/{idx}")
	public Map<String, Integer> delete(@PathVariable int idx, @CurrentUser User me) {
		return Map.of("lastPage", replyService.delete(idx, me));
	}
}
