package com.newsp.user;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsp.common.NotFoundException;
import com.newsp.security.LoginUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;

	/** 회원가입 화면의 실시간 중복 체크 (field: id | nickname | email) */
	@GetMapping("/api/signup/check/{field}")
	public Map<String, Boolean> checkAvailable(@PathVariable String field, @RequestParam String value) {
		boolean available = switch (field) {
			case "id" -> userService.isIdAvailable(value);
			case "nickname" -> userService.isNicknameAvailable(value);
			case "email" -> userService.isEmailAvailable(value);
			default -> throw new NotFoundException(field);
		};
		return Map.of("available", available);
	}

	@PostMapping("/api/signup")
	public Map<String, String> signUp(@Valid @RequestBody SignUpRequest request) {
		userService.signUp(request);
		return Map.of("result", "ok");
	}

	@PostMapping("/api/profile/nickname")
	public Map<String, String> changeNickname(@AuthenticationPrincipal LoginUser loginUser,
			@RequestBody Map<String, String> body) {
		boolean changed = userService.changeNickname(loginUser.getIdx(), body.get("nickname"));
		return Map.of("result", changed ? "ok" : "duplicate");
	}
}
