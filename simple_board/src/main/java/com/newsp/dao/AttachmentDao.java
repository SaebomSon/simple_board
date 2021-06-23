package com.newsp.dao;

import java.util.Map;

public interface AttachmentDao {
	// 첨부파일 등록
	public void insertAttachment(Map<String, Object> map);
	// 첨부파일 가져오기
	public String getAttachmentFile(Map<String, Integer> map);

}
