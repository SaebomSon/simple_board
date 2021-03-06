package com.newsp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.ReplyDaoImpl;
import com.newsp.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyDaoImpl replyDao;
	
	// 해당 게시글의 댓글 가져오기
	@Override
	public List<ReplyVO> getReplyList(int boardIdx, int start, int count) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("boardIdx", boardIdx);
		map.put("start", start);
		map.put("count", count);
		
		return replyDao.getReplyList(map);
	}
	
	// 댓글 입력하기
	@Override
	public Integer insertReply(int boardIdx, int userIdx, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardIdx", boardIdx);
		map.put("userIdx", userIdx);
		map.put("content", content);
		
		replyDao.insertReply(map);
		
		int replyIdx = Integer.parseInt(map.get("idx").toString());
		System.out.println("replyIdx >> " + replyIdx);
		return replyIdx;
	}
	
	// parent idx update
	@Override
	public void updateParentIdx(int boardIdx, int parentIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardIdx", boardIdx);
		map.put("parentIdx", parentIdx);
		
		replyDao.updateParentIdx(map);
	}
	
	// 댓글 수 구하기
	@Override
	public Integer getReplyCount(int boardIdx) {
		return replyDao.getReplyCount(boardIdx);
	}
	
	// 댓글 수정하기
	@Override
	public void modifyReply(int idx, int boardIdx, int userIdx, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idx", idx);
		map.put("boardIdx", boardIdx);
		map.put("userIdx", userIdx);
		map.put("content", content);
		
		replyDao.modifyReply(map);
	}
	
	// 댓글 삭제하기
	@Override
	public void deleteReply(int idx, int boardIdx, int userIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idx", idx);
		map.put("boardIdx", boardIdx);
		map.put("userIdx", userIdx);

		replyDao.deleteReply(map);
	}
	
	// 모댓글 정보 가져오기
	@Override
	public ReplyVO getParentReplyInfo(int idx) {
		
		return replyDao.getParentReplyInfo(idx);
	}
	
	// 대댓글 입력
	@Override
	public void insertMention(int boardIdx, int userIdx, String content, int parentIdx, int depth) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardIdx", boardIdx);
		map.put("userIdx", userIdx);
		map.put("content", content);
		map.put("parentIdx", parentIdx);
		map.put("depth", depth);
		
		replyDao.insertMention(map);
		
	}

	@Override
	public void deleteAllReplyInBoard(int boardIdx) {
		replyDao.deleteAllReplyInBoard(boardIdx);
	}

	@Override
	public List<ReplyVO> getMyReply(int userIdx) {
		return replyDao.getMyReply(userIdx);
	}
	


}
