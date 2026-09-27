package com.newsp.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.newsp.common.BadRequestException;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountRecoveryController {

	/** 가입 여부와 관계없이 같은 안내를 보여준다 */
	private static final String SENT_MESSAGE = "입력한 정보와 일치하는 계정이 있으면 메일을 보냈습니다. 메일함을 확인해 주세요.";

	private final AccountRecoveryService recoveryService;

	@GetMapping("/findId")
	public String findIdForm() {
		return "user/findId";
	}

	@PostMapping("/findId")
	public String findId(@RequestParam String email, RedirectAttributes redirect) {
		recoveryService.sendId(email);
		redirect.addFlashAttribute("message", SENT_MESSAGE);
		return "redirect:/signIn";
	}

	@GetMapping("/findPassword")
	public String findPasswordForm() {
		return "user/findPassword";
	}

	@PostMapping("/findPassword")
	public String findPassword(@RequestParam String id, @RequestParam String email, RedirectAttributes redirect) {
		recoveryService.requestPasswordReset(id, email);
		redirect.addFlashAttribute("message", SENT_MESSAGE);
		return "redirect:/signIn";
	}

	@GetMapping("/resetPassword")
	public String resetPasswordForm(@RequestParam(defaultValue = "") String token, Model model) {
		model.addAttribute("token", token);
		model.addAttribute("valid", recoveryService.isValidToken(token));
		return "user/resetPassword";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam String token, @RequestParam String newPassword,
			Model model, RedirectAttributes redirect) {
		try {
			recoveryService.resetPassword(token, newPassword);
		} catch (BadRequestException e) {
			model.addAttribute("token", token);
			model.addAttribute("valid", recoveryService.isValidToken(token));
			model.addAttribute("error", e.getMessage());
			return "user/resetPassword";
		}
		redirect.addFlashAttribute("message", "비밀번호를 변경했습니다. 새 비밀번호로 로그인해 주세요.");
		return "redirect:/signIn";
	}
}
