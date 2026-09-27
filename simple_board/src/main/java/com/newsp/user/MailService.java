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
		String link = UriComponentsBuilder.fromUriString(properties.baseUrl())
				.path("/signUp/confirm")
				.queryParam("email", email)
				.queryParam("authKey", authKey)
				.encode()
				.toUriString();

		if (!properties.mailEnabled()) {
			log.info("[mail disabled] 인증 링크 ({}): {}", email, link);
			return;
		}

		String content = "<h1>[SimpleBoard]</h1><br>"
				+ "<p>안녕하세요! 회원가입을 계속하시려면 하단의 링크를 클릭하세요.<br>"
				+ " 만약에 실수로 요청하셨거나, 본인이 요청하지 않았다면, 이 메일을 무시하세요.</p><br>"
				+ "<a href='" + link + "' target='_blank'>계속하기</a>";
		try {
			JavaMailSender sender = mailSender.getObject();
			MimeMessage mail = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");
			helper.setTo(email);
			helper.setSubject("[SimpleBoard] 회원가입 인증 메일입니다.");
			helper.setText(content, true);
			sender.send(mail);
		} catch (MessagingException | RuntimeException e) {
			log.error("인증 메일 발송 실패: {}", email, e);
		}
	}
}
