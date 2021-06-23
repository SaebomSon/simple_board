package com.newsp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.BoardDaoImpl;
import com.newsp.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDaoImpl boardDao;
	
	// 새 글 작성
	@Override
	public Integer insertNewContent(BoardVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userIdx", vo.getUser_idx());
		map.put("type", vo.getType());
		
		if("conversation".equals(vo.getSubject())) {
			map.put("subject", "사담");
		}else if("question".equals(vo.getSubject())){
			map.put("subject", "질문");
		}else if("information".equals(vo.getSubject())) {
			map.put("subject", "정보");			
		}else if("none".equals(vo.getSubject()))
			map.put("subject", null);
		map.put("title", vo.getTitle());
		map.put("content", vo.getContent());
		boardDao.insertNewContent(map);
		int boardIdx = Integer.parseInt(map.get("idx").toString());
		//System.out.println("service idx >>" + map.get("idx"));
		
		return boardIdx;
	}
	
	// attachment_idx_list update
	@Override
	public void updateAttachIdx(int boardIdx, String attachmentIdxList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardIdx", boardIdx);
		map.put("attachmentIdxList", attachmentIdxList);
		boardDao.updateAttachIdx(map);
		System.out.println("service >> " + map.get("attachmentIdxList"));
	}
	
	// 조회수 업데이트
	@Override
	public void updateHitsCount(int boardIdx) {
		boardDao.updateHitsCount(boardIdx);
	}
	
	// 최신순으로 게시글 가져오기
	@Override
	public List<BoardVO> getBoardByDate(int type, int start, int count) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("start", start);
		map.put("count", count);
		return boardDao.getBoardByDate(map);
	}
	
	// 조회수순으로 게시글 가져오기
	@Override
	public List<BoardVO> getBoardByHits(int type, int start, int count) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("start", start);
		map.put("count", count);
		return boardDao.getBoardByHits(map);
	}
	
	// 총 게시글 수 가져오기
	@Override
	public Integer getCountAllBoard(int type) {
		return boardDao.getCountAllBoard(type);
	}

	@Override
	public BoardVO getBoardDetailInfo(int boardIdx) {
		return boardDao.getBoardDetailInfo(boardIdx);
	}
}
