package com.newsp.service;

import java.util.List;

import com.newsp.vo.ReplyVO;

public interface ReplyService {
	// 해당 게시글의 댓글 가져오기
	public List<ReplyVO> getReplyList(int boardIdx, int start, int count);
	// 댓글 입력하기
	public Integer insertReply(int boardIdx, int userIdx, String content);
	// parent idx update
	public void updateParentIdx(int boardIdx, int parentIdx);
	// 댓글 개수
	public Integer getReplyCount(int boardIdx);
	// 댓글 수정하기
	public void modifyReply(int idx, int boardIdx, int userIdx, String content);
	// 댓글 삭제하기
	public void deleteReply(int idx, int boardIdx, int userIdx);
	// 모댓글 정보 가져오기
	public ReplyVO getParentReplyInfo(int idx); 
	// 대댓글 입력
	public void insertMention(int boardIdx, int userIdx, String content, int parentIdx, int depth);
	// 게시글 삭제시 댓글 모두 삭제
	public void deleteAllReplyInBoard(int boardIdx);
	// 내가 쓴 댓글 가져오기
	public List<ReplyVO> getMyReply(int userIdx);
}
