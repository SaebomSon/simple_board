package com.newsp.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.newsp.board.BoardService;
import com.newsp.notice.NoticeService;
import com.newsp.question.QuestionService;

import lombok.RequiredArgsConstructor;

/** /admin/** 은 SecurityConfig에서 ROLE_ADMIN만 허용 */
@Controller
@RequiredArgsConstructor
public class AdminController {

	private final BoardService boardService;
	private final QuestionService questionService;
	private final NoticeService noticeService;
	private final GradeService gradeService;

	@GetMapping("/admin")
	public String dashboard(Model model) {
		model.addAttribute("notices", noticeService.all());
		model.addAttribute("reportedBoards", boardService.reported());
		model.addAttribute("questions", questionService.open());
		model.addAttribute("upgrades", gradeService.pending(Grade.TYPE_UPGRADE));
		model.addAttribute("downgrades", gradeService.pending(Grade.TYPE_DOWNGRADE));
		model.addAttribute("suspensions", gradeService.pending(Grade.TYPE_SUSPEND));
		return "admin/dashboard";
	}

	@PostMapping("/admin/users/{idx}/upgrade")
	public String upgrade(@PathVariable int idx) {
		gradeService.approveUpgrade(idx);
		return "redirect:/admin";
	}
}
