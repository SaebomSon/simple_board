package com.newsp.dao;

import java.util.List;
import java.util.Map;

import com.newsp.vo.BoardVO;

public interface BoardDao {
	// 새 글 작성
	public void insertNewContent(Map<String, Object> map);
	// attachment_idx_list update
	public void updateAttachIdx(Map<String, Object> map);
	// 조회수 업데이트
	public void updateHitsCount(int boardIdx);
	// 게시글 가져오기 : 최신순
	public List<BoardVO> getBoardByDate(Map<String, Object> map);
	// 게시글 가져오기 : 조회수순
	public List<BoardVO> getBoardByHits(Map<String, Object> map);
	// 게시글 총 개수 가져오기
	public Integer getCountAllBoard(int type);
	// 선택한 게시글 가져오기
	public BoardVO getBoardDetailInfo(int boardIdx);
	// 게시판 검색 하기
	public List<BoardVO> searchBoard(Map<String, Object> map);
}
