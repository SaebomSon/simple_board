package com.newsp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.newsp.vo.QuestionVO;

public class QuestionDaoImpl implements QuestionDao {
	
	private static final String STATEMENT = "com.newsp.mapper.QuestionMapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertQuestion(Map<String, Object> map) {
		sqlSession.insert(STATEMENT + "insertQuestion", map);
	}

	@Override
	public List<QuestionVO> getQuestionList() {
		return sqlSession.selectList(STATEMENT + "getQuestionList");
	}

}
