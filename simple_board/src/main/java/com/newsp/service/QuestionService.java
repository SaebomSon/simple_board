package com.newsp.service;

import java.util.List;

import com.newsp.vo.QuestionVO;

public interface QuestionService {
	// 문의글 작성하기
	public void insertQuestion(int userIdx, String subject, String title, String content);
	// 문의글 가져오기
	public List<QuestionVO> getQuestionList();
	// 문의글 상세보기
	public QuestionVO getQuestionInfo(int idx);
	// 답변 완료된 질문글 status 변경하기
	public void updateStatus(int idx);
	// 나의 질문글 가져오기
	public List<QuestionVO> getMyQuestionList(int userIdx);
	// 나의 질문글 삭제하기
	public void deleteMyQuestion(int idx);

}
