package com.newsp.dao;

import java.util.List;
import java.util.Map;

import com.newsp.vo.QuestionVO;

public interface QuestionDao {
	// 문의글 작성하기
	public void insertQuestion(Map<String, Object> map);
	// 문의글 가져오기
	public List<QuestionVO> getQuestionList();
	// 문의글 상세보기
	public QuestionVO getQuestionInfo(int idx);
	// 답변 완료된 질문글 status 변경하기
	public void updateStatus(int idx);
}
