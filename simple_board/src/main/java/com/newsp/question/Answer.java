package com.newsp.question;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {
	private int idx;
	private int userIdx;
	private int questionIdx;
	private String content;
	private LocalDateTime answeredDate;

	// user
	private String nickname;
	private String levelImage;
}
