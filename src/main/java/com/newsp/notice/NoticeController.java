package com.newsp.notice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.newsp.security.CurrentUser;
import com.newsp.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;

	@GetMapping("/notices/{idx}")
	public String detail(@PathVariable int idx, Model model) {
		model.addAttribute("notice", noticeService.view(idx));
		return "notice/detail";
	}

	// ---- 관리자 전용 (/admin/** 은 SecurityConfig에서 ROLE_ADMIN만 허용) ----

	@GetMapping("/admin/notices/new")
	public String writeForm(Model model) {
		model.addAttribute("form", new NoticeForm());
		return "notice/form";
	}

	@PostMapping("/admin/notices")
	public String write(@Valid @ModelAttribute("form") NoticeForm form, BindingResult binding,
			@CurrentUser User me) {
		if (binding.hasErrors()) {
			return "notice/form";
		}
		noticeService.write(form, me.getIdx());
		return "redirect:/admin";
	}

	@GetMapping("/admin/notices/{idx}/edit")
	public String editForm(@PathVariable int idx, Model model) {
		model.addAttribute("noticeIdx", idx);
		model.addAttribute("form", NoticeForm.from(noticeService.get(idx)));
		return "notice/form";
	}

	@PostMapping("/admin/notices/{idx}/edit")
	public String edit(@PathVariable int idx, @Valid @ModelAttribute("form") NoticeForm form, BindingResult binding,
			Model model, RedirectAttributes redirect) {
		if (binding.hasErrors()) {
			model.addAttribute("noticeIdx", idx);
			return "notice/form";
		}
		noticeService.update(idx, form);
		redirect.addFlashAttribute("message", "공지를 수정했습니다.");
		return "redirect:/notices/" + idx;
	}

	@PostMapping("/admin/notices/{idx}/delete")
	public String delete(@PathVariable int idx) {
		noticeService.delete(idx);
		return "redirect:/admin";
	}
}
