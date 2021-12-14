package com.newsp.service;

import com.newsp.vo.AnswerVO;

public interface AnswerService {
	// 답변글 작성하기
	public void insertAnswer(int userIdx, int questionIdx, String content);
	public AnswerVO getAnswerInfoInQuestion(int questionIdx);
}
