package com.newsp.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class AttachmentDaoImpl implements AttachmentDao {
	
	private static final String STATEMENT ="com.newsp.mapper.AttachmentMapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertAttachment(Map<String, Object> map) {
		sqlSession.insert(STATEMENT + "insertAttachment", map);
		
	}
}
