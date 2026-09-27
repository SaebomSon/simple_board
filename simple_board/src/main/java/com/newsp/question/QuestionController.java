package com.newsp.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newsp.security.CurrentUser;
import com.newsp.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;

	@GetMapping("/questions/new")
	public String form(Model model) {
		model.addAttribute("form", new QuestionForm());
		model.addAttribute("subjects", QuestionSubject.values());
		return "question/form";
	}

	@PostMapping("/questions")
	public String ask(@Valid @ModelAttribute("form") QuestionForm form, BindingResult binding,
			@CurrentUser User me, Model model) {
		if (binding.hasErrors()) {
			model.addAttribute("subjects", QuestionSubject.values());
			return "question/form";
		}
		questionService.ask(form, me);
		return "redirect:/my/questions";
	}

	@GetMapping("/questions/{idx}")
	public String detail(@PathVariable int idx, @CurrentUser User me, Model model) {
		model.addAttribute("question", questionService.get(idx, me));
		model.addAttribute("answer", questionService.answerOf(idx).orElse(null));
		return "question/detail";
	}

	@GetMapping("/my/questions")
	public String mine(@CurrentUser User me, Model model) {
		model.addAttribute("questions", questionService.mine(me.getIdx()));
		return "my/questions";
	}

	@PostMapping("/my/questions/delete")
	public String deleteMine(@RequestParam(name = "each", required = false) List<Integer> idxs,
			@CurrentUser User me) {
		if (idxs != null) {
			questionService.deleteMine(idxs, me);
		}
		return "redirect:/my/questions";
	}

	// ---- 관리자 전용 ----

	@PostMapping("/admin/questions/{idx}/answer")
	public String answer(@PathVariable int idx, @RequestParam String content, @CurrentUser User me) {
		questionService.answer(idx, content, me);
		return "redirect:/questions/" + idx;
	}

	@PostMapping("/admin/questions/{idx}/done")
	public String done(@PathVariable int idx) {
		questionService.markDone(idx);
		return "redirect:/admin";
	}
}
