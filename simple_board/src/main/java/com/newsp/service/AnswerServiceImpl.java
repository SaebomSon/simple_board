package com.newsp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.AnswerDao;
import com.newsp.vo.AnswerVO;

@Service
public class AnswerServiceImpl implements AnswerService {
	
	@Autowired
	private AnswerDao answerDao;
	
	@Override
	public void insertAnswer(int userIdx, int questionIdx, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_idx", userIdx);
		map.put("question_idx", questionIdx);
		map.put("content", content);
		
		answerDao.insertAnswer(map);
	}

	@Override
	public AnswerVO getAnswerInfoInQuestion(int questionIdx) {
		return answerDao.getAnswerInfoInQuestion(questionIdx);
	}
	
}
