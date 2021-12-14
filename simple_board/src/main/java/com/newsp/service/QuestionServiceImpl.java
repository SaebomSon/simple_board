package com.newsp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.QuestionDaoImpl;
import com.newsp.vo.QuestionVO;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionDaoImpl questionDao;
	
	@Override
	public void insertQuestion(int userIdx, String subject, String title, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_idx", userIdx);
		map.put("subject", subject);
		map.put("title", title);
		map.put("content", content);
		
		questionDao.insertQuestion(map);
	}

	@Override
	public List<QuestionVO> getQuestionList() {
		return questionDao.getQuestionList();
	}

	@Override
	public QuestionVO getQuestionInfo(int idx) {
		return questionDao.getQuestionInfo(idx);
	}

	@Override
	public void updateStatus(int idx) {
		questionDao.updateStatus(idx);
	}

}
