package com.newsp.user;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsp.common.BadRequestException;
import com.newsp.common.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;

	public User get(int idx) {
		return userMapper.findByIdx(idx).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
	}

	public boolean isIdAvailable(String id) {
		return userMapper.countById(id) == 0;
	}

	public boolean isNicknameAvailable(String nickname) {
		return userMapper.countByNickname(nickname) == 0;
	}

	public boolean isEmailAvailable(String email) {
		return userMapper.countByEmail(email) == 0;
	}

	/** 회원 정보를 저장하고 이메일 인증 메일을 보낸다. */
	@Transactional
	public void signUp(SignUpRequest request) {
		if (!isIdAvailable(request.id())) {
			throw new BadRequestException("동일한 아이디가 존재합니다.");
		}
		if (!isNicknameAvailable(request.nickname())) {
			throw new BadRequestException("동일한 닉네임이 존재합니다.");
		}
		if (!isEmailAvailable(request.email())) {
			throw new BadRequestException("동일한 이메일이 존재합니다.");
		}

		User user = new User();
		user.setId(request.id());
		user.setPassword(passwordEncoder.encode(request.pw()));
		user.setNickname(request.nickname());
		user.setEmail(request.email());
		userMapper.insert(user);

		String authKey = UUID.randomUUID().toString().replace("-", "");
		userMapper.updateAuthKey(user.getEmail(), authKey);
		mailService.sendAuthMail(user.getEmail(), authKey);
	}

	@Transactional
	public boolean confirmEmail(String email, String authKey) {
		return userMapper.verifyEmail(email, authKey) > 0;
	}

	/** @return 변경 성공 여부 (닉네임 중복이면 false) */
	@Transactional
	public boolean changeNickname(int idx, String nickname) {
		if (nickname == null || nickname.isBlank() || nickname.length() > 45) {
			throw new BadRequestException("닉네임을 확인해 주세요.");
		}
		if (!isNicknameAvailable(nickname)) {
			return false;
		}
		userMapper.updateNickname(idx, nickname);
		return true;
	}

	@Transactional
	public void changePassword(int idx, String currentPassword, String newPassword) {
		User user = get(idx);
		if (currentPassword == null || !passwordEncoder.matches(currentPassword, user.getPassword())) {
			throw new BadRequestException("현재 비밀번호가 일치하지 않습니다.");
		}
		if (!PasswordPolicy.isValid(newPassword)) {
			throw new BadRequestException(PasswordPolicy.MESSAGE);
		}
		userMapper.updatePassword(idx, passwordEncoder.encode(newPassword));
	}

	@Transactional
	public void deleteAccount(int idx) {
		userMapper.deleteByIdx(idx);
	}
}
