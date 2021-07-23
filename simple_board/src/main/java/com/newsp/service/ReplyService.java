package com.newsp.service;

import java.util.List;
import com.newsp.vo.ReplyVO;

public interface ReplyService {
	// 해당 게시글의 댓글 가져오기
	public List<ReplyVO> getReplyList(int boardIdx);
	// 댓글 입력하기
	public void insertReply(int boardIdx, int userIdx, String content);
	// 댓글 개수
	public Integer getReplyCount(int boardIdx);
	// 댓글 수정하기
	public void modifyReply(int idx, int boardIdx, int userIdx, String content);
	// 댓글 삭제하기
	public void deleteReply(int idx, int boardIdx, int userIdx);
}
