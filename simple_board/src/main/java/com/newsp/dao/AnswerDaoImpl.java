package com.newsp.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.newsp.vo.AnswerVO;

public class AnswerDaoImpl implements AnswerDao {
	
	private static final String STATEMENT ="com.newsp.mapper.AnswerMapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertAnswer(Map<String, Object> map) {
		sqlSession.insert(STATEMENT + "insertAnswer", map);
	}

	@Override
	public AnswerVO getAnswerInfoInQuestion(int questionIdx) {
		return sqlSession.selectOne(STATEMENT + "getAnswerInfoInQuestion", questionIdx);
	}

}
