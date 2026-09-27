package com.newsp.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.newsp.user.User;

/**
 * 세션에 저장되는 로그인 정보.
 * 닉네임/등급처럼 바뀔 수 있는 값은 담지 않고, 필요할 때 idx로 DB에서 다시 읽는다.
 */
public class LoginUser implements UserDetails {

	private final int idx;
	private final String username;
	private final String password;
	private final boolean admin;
	private final boolean enabled;
	private final boolean nonLocked;

	public LoginUser(User user) {
		this.idx = user.getIdx();
		this.username = user.getId();
		this.password = user.getPassword();
		this.admin = user.isAdmin();
		this.enabled = user.isEmailVerified();
		this.nonLocked = !user.isSuspended();
	}

	public int getIdx() {
		return idx;
	}

	public boolean isAdmin() {
		return admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(admin ? "ROLE_ADMIN" : "ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isAccountNonLocked() {
		return nonLocked;
	}
}
