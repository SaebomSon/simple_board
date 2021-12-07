package com.newsp.service;

import java.util.List;

import com.newsp.vo.QuestionVO;

public interface QuestionService {
	// 문의글 작성하기
	public void insertQuestion(int userIdx, String subject, String title, String content);
	// 문의글 가져오기
	public List<QuestionVO> getQuestionList();

}
