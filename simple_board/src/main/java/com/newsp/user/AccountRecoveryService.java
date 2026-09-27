package com.newsp.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsp.common.BadRequestException;

import lombok.RequiredArgsConstructor;

/**
 * 아이디 찾기 / 비밀번호 재설정.
 * 가입 여부를 떠볼 수 없도록 요청 결과(회원 존재 여부)는 호출한 쪽에 알려주지 않는다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountRecoveryService {

	public static final int RESET_VALID_MINUTES = 30;
	public static final String INVALID_LINK = "유효하지 않거나 만료된 링크입니다. 비밀번호 찾기를 다시 요청해 주세요.";

	private static final SecureRandom RANDOM = new SecureRandom();

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;

	/** 인증을 마친 회원이면 아이디를 메일로 보낸다 */
	public void sendId(String email) {
		userMapper.findByEmail(email.trim())
				.filter(User::isEmailVerified)
				.ifPresent(user -> mailService.sendIdMail(user.getEmail(), user.getId()));
	}

	/** 아이디와 이메일이 일치하면 재설정 링크를 메일로 보낸다 */
	@Transactional
	public void requestPasswordReset(String id, String email) {
		Optional<User> found = userMapper.findById(id.trim())
				.filter(user -> user.getEmail().equalsIgnoreCase(email.trim()))
				.filter(User::isEmailVerified);
		if (found.isEmpty()) {
			return;
		}
		User user = found.get();
		String token = newToken();
		userMapper.updateResetToken(user.getIdx(), hash(token), LocalDateTime.now().plusMinutes(RESET_VALID_MINUTES));
		mailService.sendPasswordResetMail(user.getEmail(), token, RESET_VALID_MINUTES);
	}

	public boolean isValidToken(String token) {
		return findByToken(token).isPresent();
	}

	/** 토큰은 한 번 쓰면 사라진다 */
	@Transactional
	public void resetPassword(String token, String newPassword) {
		User user = findByToken(token).orElseThrow(() -> new BadRequestException(INVALID_LINK));
		if (!PasswordPolicy.isValid(newPassword)) {
			throw new BadRequestException(PasswordPolicy.MESSAGE);
		}
		userMapper.resetPassword(user.getIdx(), passwordEncoder.encode(newPassword));
	}

	private Optional<User> findByToken(String token) {
		if (token == null || token.isBlank()) {
			return Optional.empty();
		}
		return userMapper.findByResetToken(hash(token))
				.filter(user -> user.getResetTokenExpires() != null
						&& user.getResetTokenExpires().isAfter(LocalDateTime.now()));
	}

	private static String newToken() {
		byte[] bytes = new byte[32];
		RANDOM.nextBytes(bytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	/** DB에는 토큰 원문 대신 SHA-256 해시를 저장한다 */
	public static String hash(String token) {
		try {
			byte[] digest = MessageDigest.getInstance("SHA-256").digest(token.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}
}
