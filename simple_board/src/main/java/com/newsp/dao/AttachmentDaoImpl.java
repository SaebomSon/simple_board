package com.newsp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.newsp.vo.AttachmentVO;

public class AttachmentDaoImpl implements AttachmentDao {
	
	private static final String STATEMENT ="com.newsp.mapper.AttachmentMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	// 첨부파일 등록
	@Override
	public void insertAttachment(Map<String, Object> map) {
		sqlSession.insert(STATEMENT + "insertAttachment", map);
	}
	
	// 첨부파일 가져오기
	@Override
	public String getAttachmentFile(Map<String, Integer> map) {		
		return sqlSession.selectOne(STATEMENT + "getAttachmentFile", map);
	}
	
	// 첨부파일 개별 삭제
	@Override
	public void deleteAttachment(Map<String, Object> map) {
		sqlSession.delete(STATEMENT + "deleteAttachment", map);
	}
	
	// 첨부파일 idx 가져오기
	@Override
	public List<AttachmentVO> getAttachIdx(int boardIdx) {
		List<AttachmentVO> list = sqlSession.selectList(STATEMENT + "getAttachIdx", boardIdx);
		
		return list;
	}
	
	// 첨부파일 전체 삭제
	@Override
	public void deleteAllAttachment(int boardIdx) {
		sqlSession.delete(STATEMENT + "deleteAllAttachment", boardIdx);
	}
}
