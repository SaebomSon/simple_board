package com.newsp.service;

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
	public List<ReplyVO> getReplyList(int boardIdx) {
		return replyDao.getReplyList(boardIdx);
	}
	
	// 댓글 입력하기
	@Override
	public void insertReply(int boardIdx, int userIdx, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardIdx", boardIdx);
		map.put("userIdx", userIdx);
		map.put("content", content);
		
		replyDao.insertReply(map);
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

}
