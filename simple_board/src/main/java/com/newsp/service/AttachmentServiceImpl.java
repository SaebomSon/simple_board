package com.newsp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.AttachmentDaoImpl;
import com.newsp.vo.AttachmentVO;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	@Autowired
	private AttachmentDaoImpl attachDao;

	@Override
	public Integer insertAttachment(int boardIdx, String fileName, String filePath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardIdx", boardIdx);
		map.put("fileName", fileName);
		map.put("filePath", filePath);
		System.out.println("service >> " + boardIdx +"/"+ fileName +"/"+ filePath);
		attachDao.insertAttachment(map);
		int attachIdx = Integer.parseInt(map.get("idx").toString());
		
		return attachIdx;
	}

	@Override
	public String getAttachmentFile(int boardIdx, int attachIdx) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("boardIdx", boardIdx);
		map.put("attachIdx", attachIdx);
		
		return attachDao.getAttachmentFile(map);
	}

}
