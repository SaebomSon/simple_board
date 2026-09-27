package com.newsp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/main", "/signIn", "/signUp", "/signUp/confirm", "/api/signup/**",
						"/findId", "/findPassword", "/resetPassword",
						"/css/**", "/js/**", "/image/**", "/favicon.ico", "/error").permitAll()
				.requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage("/signIn")
				.loginProcessingUrl("/login")
				.usernameParameter("loginId")
				.passwordParameter("loginPw")
				.successHandler(loginSuccessHandler())
				.failureHandler(loginFailureHandler())
				.permitAll())
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/"))
			.exceptionHandling(ex -> ex
				// ajax 요청은 로그인 페이지로 리다이렉트하지 않고 401을 돌려준다.
				.defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
						PathPatternRequestMatcher.withDefaults().matcher("/api/**")));
		return http.build();
	}

	/**
	 * 새 비밀번호는 BCrypt로 저장한다. 접두어가 없는 기존 평문 비밀번호도 검증할 수 있고,
	 * 로그인 성공 시 {@link LoginUserDetailsService#updatePassword}로 해시가 교체된다.
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		DelegatingPasswordEncoder encoder =
				(DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
		encoder.setDefaultPasswordEncoderForMatches(new LegacyPlainTextPasswordEncoder());
		return encoder;
	}

	private AuthenticationSuccessHandler loginSuccessHandler() {
		return (request, response, authentication) -> {
			boolean admin = authentication.getAuthorities().stream()
					.anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
			response.sendRedirect(request.getContextPath() + (admin ? "/admin" : "/"));
		};
	}

	private AuthenticationFailureHandler loginFailureHandler() {
		return (request, response, exception) ->
				response.sendRedirect(request.getContextPath() + "/signIn?error=" + errorCode(exception));
	}

	private static String errorCode(AuthenticationException exception) {
		if (exception instanceof DisabledException) {
			return "unverified";
		}
		if (exception instanceof LockedException) {
			return "suspended";
		}
		return "bad";
	}
}
