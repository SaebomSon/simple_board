package com.newsp.service;

import java.util.List;

import com.newsp.vo.BoardVO;

public interface BoardService {
	// 메인 화면 : 글 list 가져오기
	public List<BoardVO> getListNewestInMain();
	public List<BoardVO> getListOrderbyHitsCount();
	public List<BoardVO> getListOrderbyReplyCount();
	// 새 글 작성
	public Integer insertNewContent(BoardVO vo);
	// attachment_idx_list update
	public void updateAttachIdx(int boardIdx, String attachmentIdxList);
	// 조회수 업데이트
	public void updateHitsCount(int boardIdx);
	// 게시글 가져오기 : 최신순
	public List<BoardVO> getBoardByDate(int type, int start, int count);
	// 게시글 가져오기 : 조회수순
//	public List<BoardVO> getBoardByHits(int type, int start, int count);
	// 내 게시글 수정
	public void modifyMyBoard(BoardVO vo);
	// 내 게시글 삭제
	public void deleteMyBoard(int boardIdx, int userIdx);
	// 게시글 총 개수 가져오기
	public Integer getCountAllBoard(int type);
	// 선택한 게시글 가져오기
	public BoardVO getBoardDetailInfo(int boardIdx);
	// 게시판 내 검색하기
	public List<BoardVO> searchBoard(int type, String option, String keyword, int start, int count);
	// 검색 결과 count
	public Integer countAfterSearch(int type, String option, String keyword);
	// 댓글 수 업데이트
	public void updateReplyCount(int boardIdx, int replyCount);
	// 내가 쓴 게시물 가져오기
	public List<BoardVO> getMyBoard(int userIdx);
}
