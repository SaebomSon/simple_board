package com.newsp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsp.user.User;
import com.newsp.user.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService, UserDetailsPasswordService {

	private final UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return userMapper.findById(username)
				.map(LoginUser::new)
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

	/**
	 * 기존 평문 비밀번호로 로그인에 성공하면 Spring Security가 호출해 BCrypt 해시로 교체한다.
	 */
	@Override
	@Transactional
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		userMapper.updatePasswordById(user.getUsername(), newPassword);
		User updated = userMapper.findById(user.getUsername()).orElseThrow();
		return new LoginUser(updated);
	}
}
