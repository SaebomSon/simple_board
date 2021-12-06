package com.newsp.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class QuestionDaoImpl implements QuestionDao {
	
	private static final String STATEMENT = "com.newsp.mapper.QuestionMapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertQuestion(Map<String, Object> map) {
		sqlSession.insert(STATEMENT + "insertQuestion", map);
	}

}
