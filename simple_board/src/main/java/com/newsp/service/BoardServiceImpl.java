package com.newsp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.BoardDaoImpl;
import com.newsp.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDaoImpl boardDao;

	@Override
	public Integer insertNewContent(BoardVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userIdx", vo.getUserIdx());
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

	@Override
	public void updateAttachIdx(int boardIdx, String attachmentIdxList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardIdx", boardIdx);
		map.put("attachmentIdxList", attachmentIdxList);
		boardDao.updateAttachIdx(map);
		System.out.println("service >> " + map.get("attachmentIdxList"));
	}
}
