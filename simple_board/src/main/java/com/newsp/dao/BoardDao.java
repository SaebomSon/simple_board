package com.newsp.dao;

import java.util.List;
import java.util.Map;

import com.newsp.vo.BoardVO;

public interface BoardDao {
	// 메인 화면 : 글 list 가져오기
	public List<BoardVO> getListNewestInMain();
	public List<BoardVO> getListOrderbyHitsCount();
	public List<BoardVO> getListOrderbyReplyCount();
	// 새 글 작성
	public void insertNewContent(Map<String, Object> map);
	// attachment_idx_list update
	public void updateAttachIdx(Map<String, Object> map);
	// 조회수 업데이트
	public void updateHitsCount(int boardIdx);
	// 게시글 가져오기 : 최신순
	public List<BoardVO> getBoardByDate(Map<String, Object> map);
	// 게시글 가져오기 : 조회수순
//	public List<BoardVO> getBoardByHits(Map<String, Object> map);
	// 내 게시글 수정
	public void modifyMyBoard(Map<String, Object> map);
	// 내 게시글 삭제
	public void deleteMyBoard(Map<String, Object> map);
	// 게시글 총 개수 가져오기
	public Integer getCountAllBoard(int type);
	// 선택한 게시글 가져오기
	public BoardVO getBoardDetailInfo(int boardIdx);
	// 게시판 검색 하기
	public List<BoardVO> searchBoard(Map<String, Object> map);
	// 검색 결과 count
	public Integer countAfterSearch(Map<String, Object> map);
	// 댓글 수 업데이트
	public void updateReplyCount(Map<String, Object> map);
	// 내가 쓴 게시물 가져오기
	public List<BoardVO> getMyBoard(int userIdx);
	public List<BoardVO> getUserIdxForGrade(int count);
	// report count update
	public void updateReportCount(Map<String, Integer> map);
	// report count 10개 이상인 board 가져오기
	public List<BoardVO> getBoardListOverReportCountTen();
}
