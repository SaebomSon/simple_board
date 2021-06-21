package com.newsp.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class BoardDaoImpl implements BoardDao {
	
	private static final String STATEMENT = "com.newsp.mapper.BoardMapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertNewContent(Map<String, Object> map) {		
		sqlSession.insert(STATEMENT + "insertNewContent", map);
	}

	@Override
	public void updateAttachIdx(Map<String, Object> map) {		
		sqlSession.update(STATEMENT + "attachIdUpdate", map);
	}
}
