package com.newsp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.newsp.vo.BoardVO;

public class BoardDaoImpl implements BoardDao {
	
	private static final String STATEMENT = "com.newsp.mapper.BoardMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	// 새 글 작성
	@Override
	public void insertNewContent(Map<String, Object> map) {		
		sqlSession.insert(STATEMENT + "insertNewContent", map);
	}
	
	// attachment_idx_list update
	@Override
	public void updateAttachIdx(Map<String, Object> map) {		
		sqlSession.update(STATEMENT + "attachIdUpdate", map);
	}
	
	// 조회수 업데이트
	@Override
	public void updateHitsCount(int boardIdx) {
		sqlSession.update(STATEMENT + "updateHitsCount", boardIdx);
	}
	
	// 최신순으로 게시글 가져오기
	@Override
	public List<BoardVO> getBoardByDate(Map<String, Object> map) {
		List<BoardVO> list = sqlSession.selectList(STATEMENT + "getBoardOrderbyDate", map); 
		return list;
	}
	
	// 조회수순으로 게시글 가져오기
	@Override
	public List<BoardVO> getBoardByHits(Map<String, Object> map) {
		return sqlSession.selectList(STATEMENT + "getBoardOrderbyHits", map);
	}
	
	// 전체 게시글 수 가져오기
	@Override
	public Integer getCountAllBoard(int type) {
		return sqlSession.selectOne(STATEMENT + "getCountAllBorad", type);
	}
	// 게시글 보기
	@Override
	public BoardVO getBoardDetailInfo(int boardIdx) {
		return sqlSession.selectOne(STATEMENT + "getBoardDetailInfo", boardIdx);
	}
	
	// 게시판 내 검색하기
	@Override
	public List<BoardVO> searchBoard(Map<String, Object> map) {
		List<BoardVO> list = sqlSession.selectList(STATEMENT + "searchBoard", map);
		return list;
	}
	
	// 검색 결과 count
	@Override
	public Integer countAfterSearch(Map<String, Object> map) {
		return sqlSession.selectOne(STATEMENT + "countAfterSearch", map);
	}
	
	// 댓글 수 업데이트
	@Override
	public void updateReplyCount(Map<String, Object> map) {
		sqlSession.update(STATEMENT + "updateReplyCount", map);
	}
}
