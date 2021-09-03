package com.newsp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.newsp.vo.ReplyVO;

public class ReplyDaoImpl implements ReplyDao {
	
	private static final String STATEMENT ="com.newsp.mapper.ReplyMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	// 해당 게시글의 댓글 가져오기
	@Override
	public List<ReplyVO> getReplyList(Map<String, Integer> map) {
		return sqlSession.selectList(STATEMENT + "getReplyList", map);
	}
	
	// 댓글 입력하기
	@Override
	public void insertReply(Map<String, Object> map) {
		sqlSession.insert(STATEMENT + "insertReply", map);
	}
	// parent idx update
	@Override
	public void updateParentIdx(Map<String, Object> map) {
		sqlSession.update(STATEMENT + "updateParentIdx", map);
	}
	
	// 댓글 수 구하기
	@Override
	public Integer getReplyCount(int boardIdx) {
		return sqlSession.selectOne(STATEMENT + "getReplyCount", boardIdx);
	}
	
	// 댓글 수정하기
	@Override
	public void modifyReply(Map<String, Object> map) {
		sqlSession.update(STATEMENT + "modifyReply", map);
	}
	
	// 댓글 삭제하기
	@Override
	public void deleteReply(Map<String, Object> map) {
		sqlSession.delete(STATEMENT + "deleteReply", map);
	}
	
	// 모댓글 정보 가져오기
	@Override
	public ReplyVO getParentReplyInfo(int idx) {
		return sqlSession.selectOne(STATEMENT + "getParentReplyInfo", idx);
	}
	
	// 대댓글 입력
	@Override
	public void insertMention(Map<String, Object> map) {
		sqlSession.insert(STATEMENT + "insertMention", map);
	}
	
	// 게시글 삭제시 게시글 내 댓글 모두 삭제
	@Override
	public void deleteAllReplyInBoard(int boardIdx) {
		sqlSession.delete(STATEMENT + "deleteAllReplyInBoard", boardIdx);
		
	}

	@Override
	public List<ReplyVO> getMyReply(int userIdx) {
		return sqlSession.selectList(STATEMENT + "getMyReply", userIdx);
	}
	

	

}
