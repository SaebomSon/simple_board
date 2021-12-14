package com.newsp.dao;

import java.util.Map;

import com.newsp.vo.AnswerVO;

public interface AnswerDao {
	// 답변 등록하기
	public void insertAnswer(Map<String, Object> map);
	// 선택한 문의글의 답변 가져오기
	public AnswerVO getAnswerInfoInQuestion(int questionIdx);
}
