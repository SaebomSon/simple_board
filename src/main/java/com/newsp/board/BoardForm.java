package com.newsp.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {

	private String subject = Subject.NONE;

	@NotBlank(message = "제목을 입력하세요.")
	@Size(max = 100, message = "제목은 100자까지 입력할 수 있습니다.")
	private String title;

	@NotBlank(message = "내용을 입력하세요.")
	@Size(max = 5000, message = "내용은 5000자까지 입력할 수 있습니다.")
	private String content;

	public static BoardForm from(Board board) {
		BoardForm form = new BoardForm();
		form.setSubject(Subject.codeOf(board.getSubject()));
		form.setTitle(board.getTitle());
		form.setContent(board.getContent());
		return form;
	}
}
