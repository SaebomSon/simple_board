package com.newsp.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.newsp.common.BadRequestException;
import com.newsp.security.LoginUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/signIn")
	public String signIn() {
		return "user/signIn";
	}

	@GetMapping("/signUp")
	public String signUp() {
		return "user/signUp";
	}

	/** 인증 메일의 링크: email과 authKey가 일치하면 인증 완료 처리 */
	@GetMapping("/signUp/confirm")
	public String confirm(@RequestParam String email, @RequestParam String authKey, RedirectAttributes redirect) {
		boolean confirmed = userService.confirmEmail(email, authKey);
		redirect.addFlashAttribute("message",
				confirmed ? "이메일 인증이 완료되었습니다. 로그인해 주세요." : "유효하지 않은 인증 링크입니다.");
		return "redirect:/signIn";
	}

	@GetMapping("/profile")
	public String profile() {
		// 회원 정보는 CurrentUserAdvice가 "me"로 넣어준다.
		return "user/profile";
	}

	@PostMapping("/profile/password")
	public String changePassword(@AuthenticationPrincipal LoginUser loginUser,
			@RequestParam String currentPassword, @RequestParam String newPassword,
			RedirectAttributes redirect) {
		try {
			userService.changePassword(loginUser.getIdx(), currentPassword, newPassword);
			redirect.addFlashAttribute("message", "비밀번호가 변경되었습니다.");
		} catch (BadRequestException e) {
			redirect.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/profile";
	}

	/** 회원 탈퇴: 사이드바에서 입력한 아이디가 본인 아이디와 같아야 한다. */
	@PostMapping("/quit")
	public String quit(@AuthenticationPrincipal LoginUser loginUser, @RequestParam String confirmId,
			HttpServletRequest request, RedirectAttributes redirect) throws ServletException {
		if (!loginUser.getUsername().equals(confirmId)) {
			redirect.addFlashAttribute("error", "입력한 아이디가 틀립니다. 다시 시도하세요.");
			return "redirect:/";
		}
		userService.deleteAccount(loginUser.getIdx());
		request.logout();
		redirect.addFlashAttribute("message", "SIMPLE BOARD에서 탈퇴했습니다.");
		return "redirect:/";
	}
}
