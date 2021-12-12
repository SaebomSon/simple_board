package com.newsp.dao;

import java.util.List;
import java.util.Map;

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

	@Override
	public NoticeVO getNoticeInfo(int idx) {
		return sqlSession.selectOne(STATEMENT + "getNoticeInfo", idx);
	}

	@Override
	public void insertNotice(Map<String, Object> map) {
		sqlSession.insert(STATEMENT + "insertNotice", map);
	}

	@Override
	public void updateNotice(Map<String, Object> map) {
		sqlSession.update(STATEMENT + "updateNotice", map);
	}

	@Override
	public void deleteNotice(int idx) {
		sqlSession.delete(STATEMENT + "deleteNotice", idx);
	}

}
