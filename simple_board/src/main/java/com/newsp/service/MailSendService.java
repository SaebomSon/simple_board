package com.newsp.service;

import java.util.Random;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSendService {
	@Autowired
	private JavaMailSenderImpl mailSender;

	// 인증키 생성하기
	private String getAuthCode() {
		Random random = new Random();
		String key = "";

		for (int i = 0; i < 3; i++) {
			// A~Z까지 랜덤
			int index = random.nextInt(25) + 65;
			key += (char) index;
		}
		// 4자리 정수 랜덤
		int number = random.nextInt(9999) + 1000;
		key += number;
		System.out.println("key >> " + key);

		return key;
	}

	public String sendAuthMail(String email) {
		String authKey = getAuthCode();

		// 인증 메일 보내기
		MimeMessage mail = mailSender.createMimeMessage();
		String mailContent = "<h1>[SimpleBoard]</h1><br>"
				+ "<p>안녕하세요! 회원가입을 계속하시려면 하단의 링크를 클릭하세요.<br> 만약에 실수로 요청하셨거나, 본인이 요청하지 않았다면, 이 메일을 무시하세요.</p><br>"
				+ "<a href='http://localhost:8181/board/signUp/confirm?email="
				+ email + "&authKey="+ authKey +"' target='_blank'>계속하기</a>";
		try {
			mail.setSubject("[SimpleBoard] 회원가입 인증 메일입니다.", "utf-8");
			mail.setText(mailContent, "utf-8", "html");
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authKey;
	}

}
