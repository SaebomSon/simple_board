package com.newsp.dao;

import java.util.List;
import java.util.Map;

import com.newsp.vo.QuestionVO;

public interface QuestionDao {
	// 문의글 작성하기
	public void insertQuestion(Map<String, Object> map);
	// 문의글 가져오기
	public List<QuestionVO> getQuestionList();
}
