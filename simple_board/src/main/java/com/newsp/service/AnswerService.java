package com.newsp.service;

import com.newsp.vo.AnswerVO;

public interface AnswerService {
	// 답변글 작성하기
	public void insertAnswer(int userIdx, int questionIdx, String content);
	// 선택한 문의글에 답변 가져오기
	public AnswerVO getAnswerInfoInQuestion(int questionIdx);
}
