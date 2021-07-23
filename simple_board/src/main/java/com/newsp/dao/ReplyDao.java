package com.newsp.dao;

import java.util.List;
import java.util.Map;

import com.newsp.vo.ReplyVO;

public interface ReplyDao {
	// 해당 게시글의 댓글 가져오기
	public List<ReplyVO> getReplyList(int boardIdx);
	// 댓글 입력하기
	public void insertReply(Map<String, Object> map);
	// 댓글 개수
	public Integer getReplyCount(int boardIdx);
	public void modifyReply(Map<String, Object> map);
	public void deleteReply(Map<String, Object> map);

}
