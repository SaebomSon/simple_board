package com.newsp.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
		@Pattern(regexp = "^[a-z0-9]{4,12}$", message = "아이디는 영문 소문자, 숫자 조합으로 4~12자리만 허용됩니다.")
		String id,

		@Pattern(regexp = PasswordPolicy.REGEX, message = PasswordPolicy.MESSAGE)
		String pw,

		@NotBlank(message = "닉네임을 입력해 주세요.")
		@Size(max = 45, message = "닉네임이 너무 깁니다.")
		String nickname,

		@NotBlank(message = "이메일을 입력해 주세요.")
		@Email(message = "잘못된 이메일 형식입니다.")
		@Size(max = 100, message = "이메일이 너무 깁니다.")
		String email) {
}
