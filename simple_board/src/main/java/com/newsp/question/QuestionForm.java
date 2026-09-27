package com.newsp.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {

	private String subject = QuestionSubject.NONE.name();

	@NotBlank(message = "제목을 입력하세요.")
	@Size(max = 100)
	private String title;

	@NotBlank(message = "내용을 입력하세요.")
	@Size(max = 5000)
	private String content;
}
