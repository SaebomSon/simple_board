package com.newsp.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final BoardService boardService;

	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("latest", boardService.latest());
		model.addAttribute("topHits", boardService.topByHits());
		model.addAttribute("topReplies", boardService.topByReplyCount());
		return "main";
	}

	/** 기존 주소 호환 */
	@GetMapping("/main")
	public String legacyMain() {
		return "redirect:/";
	}
}
