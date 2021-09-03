package com.newsp.dao;

import java.util.List;
import java.util.Map;

import com.newsp.vo.ReplyVO;

public interface ReplyDao {
	// 해당 게시글의 댓글 가져오기
	public List<ReplyVO> getReplyList(Map<String, Integer> map);
	// 댓글 입력하기
	public void insertReply(Map<String, Object> map);
	// parent idx update
	public void updateParentIdx(Map<String, Object> map);
	// 댓글 개수
	public Integer getReplyCount(int boardIdx);
	// 댓글 수정
	public void modifyReply(Map<String, Object> map);
	// 댓글 삭제
	public void deleteReply(Map<String, Object> map);
	// 모댓글 정보 가져오기
	public ReplyVO getParentReplyInfo(int idx);
	// 대댓글 입력
	public void insertMention(Map<String, Object> map);
	// 게시글 삭제시 게시글 내 댓글 모두 삭제하기
	public void deleteAllReplyInBoard(int boardIdx);
	// 내가 쓴 댓글 가져오기
	public List<ReplyVO> getMyReply(int userIdx);

}
