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

	@Override
	public QuestionVO getQuestionInfo(int idx) {
		return sqlSession.selectOne(STATEMENT + "getQuestionInfo", idx);
	}

	@Override
	public void updateStatus(int idx) {
		sqlSession.update(STATEMENT + "updateStatus", idx);
	}

	@Override
	public List<QuestionVO> getMyQuestionList(int userIdx) {
		return sqlSession.selectList(STATEMENT + "getMyQuestionList", userIdx);
	}

	@Override
	public void deleteMyQuestion(int idx) {
		sqlSession.delete(STATEMENT + "deleteMyQuestion", idx);
	}

}
