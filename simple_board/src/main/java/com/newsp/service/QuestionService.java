package com.newsp.service;

public interface QuestionService {
	// 문의글 작성하기
	public void insertQuestion(int userIdx, String subject, String title, String content);

}
