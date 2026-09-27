package com.newsp.notice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeForm {

	@Pattern(regexp = "^[0124]$", message = "공지 종류를 선택하세요.")
	private String type = "0";

	@NotBlank(message = "제목을 입력하세요.")
	@Size(max = 100)
	private String title;

	@NotBlank(message = "내용을 입력하세요.")
	@Size(max = 5000)
	private String content;

	public static NoticeForm from(Notice notice) {
		NoticeForm form = new NoticeForm();
		form.setType(String.valueOf(notice.getType()));
		form.setTitle(notice.getTitle());
		form.setContent(notice.getContent());
		return form;
	}
}
