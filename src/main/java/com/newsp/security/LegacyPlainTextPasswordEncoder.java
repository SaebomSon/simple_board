package com.newsp.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * v1에서 평문으로 저장된 비밀번호("{id}" 접두어 없음)를 검증하기 위한 인코더.
 * 새 비밀번호를 만드는 데는 사용하지 않으며, 로그인 성공 시 BCrypt로 자동 교체된다.
 */
class LegacyPlainTextPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		throw new UnsupportedOperationException("평문 비밀번호는 저장하지 않습니다.");
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (rawPassword == null || encodedPassword == null) {
			return false;
		}
		return MessageDigest.isEqual(
				rawPassword.toString().getBytes(StandardCharsets.UTF_8),
				encodedPassword.getBytes(StandardCharsets.UTF_8));
	}
}
