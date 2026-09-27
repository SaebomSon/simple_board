package com.newsp.user;

public final class PasswordPolicy {

	/** 영문과 특수문자를 포함한 6~18자 (가입 화면의 기존 규칙) */
	public static final String REGEX = "^(?=.*[a-zA-Z])(?=.*[#?!@$%^&*-]).{6,18}$";
	public static final String MESSAGE = "비밀번호는 영문,특수문자 포함 6~18자리만 허용됩니다.";

	private PasswordPolicy() {
	}

	public static boolean isValid(String password) {
		return password != null && password.matches(REGEX);
	}
}
