package com.newsp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.AttachmentDaoImpl;
import com.newsp.vo.AttachmentVO;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	@Autowired
	private AttachmentDaoImpl attachDao;

	// 첨부파일 등록
	@Override
	public void insertAttachment(int boardIdx, String fileName, String filePath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardIdx", boardIdx);
		map.put("fileName", fileName);
		map.put("filePath", filePath);
		
		attachDao.insertAttachment(map);
	}
	
	// 첨부파일 가져오기
	@Override
	public String getAttachmentFile(int boardIdx, int attachIdx) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("boardIdx", boardIdx);
		map.put("attachIdx", attachIdx);
		
		return attachDao.getAttachmentFile(map);
	}
	
	// 첨부파일 개별 삭제
	@Override
	public void deleteAttachment(int boardIdx, String fileName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardIdx", boardIdx);
		map.put("fileName", fileName);
		
		attachDao.deleteAttachment(map);
	}
	
	// 첨부파일 idx 가져오기
	@Override
	public List<AttachmentVO> getAttachIdx(int boardIdx) {
		return attachDao.getAttachIdx(boardIdx);
	}

	// 첨부파일 전체 삭제
	@Override
	public void deleteAllAttachment(int boardIdx) {
		attachDao.deleteAllAttachment(boardIdx);
	}

}
