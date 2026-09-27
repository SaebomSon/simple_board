package com.newsp.user;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.newsp.config.AppProperties;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

	private final ObjectProvider<JavaMailSender> mailSender;
	private final AppProperties properties;

	public void sendAuthMail(String email, String authKey) {
		String link = link("/signUp/confirm", "email", email, "authKey", authKey);
		send(email, "[SimpleBoard] 회원가입 인증 메일입니다.",
				"<p>안녕하세요! 회원가입을 계속하시려면 하단의 링크를 클릭하세요.<br>"
						+ " 만약에 실수로 요청하셨거나, 본인이 요청하지 않았다면, 이 메일을 무시하세요.</p><br>"
						+ "<a href='" + link + "' target='_blank'>계속하기</a>",
				"인증 링크: " + link);
	}

	/** 아이디 찾기: 가입한 이메일로 아이디를 알려준다 (아이디는 영문 소문자·숫자만 허용) */
	public void sendIdMail(String email, String id) {
		send(email, "[SimpleBoard] 아이디 안내",
				"<p>회원님의 아이디는 <b>" + id + "</b> 입니다.</p>"
						+ "<a href='" + link("/signIn") + "' target='_blank'>로그인하기</a>",
				"아이디: " + id);
	}

	public void sendPasswordResetMail(String email, String token, int validMinutes) {
		String link = link("/resetPassword", "token", token);
		send(email, "[SimpleBoard] 비밀번호 재설정",
				"<p>아래 링크에서 새 비밀번호를 설정하세요. 링크는 " + validMinutes + "분 동안 한 번만 사용할 수 있습니다.<br>"
						+ " 본인이 요청하지 않았다면 이 메일을 무시하세요.</p><br>"
						+ "<a href='" + link + "' target='_blank'>비밀번호 재설정</a>",
				"비밀번호 재설정 링크: " + link);
	}

	private String link(String path, String... params) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(properties.baseUrl()).path(path);
		for (int i = 0; i < params.length; i += 2) {
			builder.queryParam(params[i], params[i + 1]);
		}
		return builder.encode().toUriString();
	}

	/** mail-enabled=false(local)이면 보내지 않고 logText를 로그로 남긴다 */
	private void send(String email, String subject, String body, String logText) {
		if (!properties.mailEnabled()) {
			log.info("[mail disabled] {} ({})", logText, email);
			return;
		}
		try {
			JavaMailSender sender = mailSender.getObject();
			MimeMessage mail = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText("<h1>[SimpleBoard]</h1><br>" + body, true);
			sender.send(mail);
		} catch (MessagingException | RuntimeException e) {
			log.error("메일 발송 실패: {} ({})", subject, email, e);
		}
	}
}
