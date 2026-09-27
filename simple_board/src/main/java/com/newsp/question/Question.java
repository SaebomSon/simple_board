package com.newsp.question;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {

	public static final int STATUS_OPEN = 0;
	public static final int STATUS_DONE = 1;

	private int idx;
	private int userIdx;
	/** QuestionSubject 코드 (NONE/B/L/R/G) */
	private String subject;
	private String title;
	private String content;
	private LocalDateTime writtenDate;
	private int status;

	// user
	private String nickname;
	private String levelImage;

	public boolean isDone() {
		return status == STATUS_DONE;
	}

	public String getDisplayTitle() {
		String label = QuestionSubject.labelOf(subject);
		return label.isEmpty() ? title : "[" + label + "] " + title;
	}
}
