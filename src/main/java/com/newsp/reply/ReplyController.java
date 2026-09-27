package com.newsp.reply;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newsp.security.CurrentUser;
import com.newsp.user.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService replyService;

	/** 게시글 상세 화면에 ajax로 끼워 넣는 댓글 목록 조각 (page 생략 시 마지막 페이지) */
	@GetMapping("/posts/{boardIdx}/replies")
	public String replies(@PathVariable int boardIdx, @RequestParam(defaultValue = "0") int page,
			@CurrentUser User me, Model model) {
		ReplyService.Page result = replyService.page(boardIdx, page, me);
		model.addAttribute("board", result.board());
		model.addAttribute("replies", result.replies());
		model.addAttribute("pagination", result.pagination());
		return "reply/list :: replies";
	}

	@GetMapping("/my/replies")
	public String myReplies(@CurrentUser User me, Model model) {
		model.addAttribute("replies", replyService.myReplies(me.getIdx()));
		return "my/replies";
	}

	@PostMapping("/my/replies/delete")
	public String deleteMyReplies(@RequestParam(name = "each", required = false) List<Integer> idxs,
			@CurrentUser User me) {
		if (idxs != null) {
			replyService.deleteAll(idxs, me);
		}
		return "redirect:/my/replies";
	}
}
