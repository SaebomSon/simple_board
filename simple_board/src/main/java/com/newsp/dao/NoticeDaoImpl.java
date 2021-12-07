package com.newsp.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.newsp.vo.NoticeVO;

public class NoticeDaoImpl implements NoticeDao {
	private static final String STATEMENT = "com.newsp.mapper.NoticeMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<NoticeVO> getNoticeList() {
		return sqlSession.selectList(STATEMENT + "getNoticeList");
	}

}
