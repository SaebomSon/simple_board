package com.newsp.common;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/** 화면 요청에서 발생한 사용자 오류를 안내 페이지로 보여준다. */
@ControllerAdvice(annotations = Controller.class)
public class PageExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String notFound(NotFoundException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error/message";
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String badRequest(BadRequestException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error/message";
	}
}
