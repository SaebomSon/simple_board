package com.newsp.service;

public interface AttachmentService {
	// insert Attachment
	public Integer insertAttachment(int boardIdx, String fileName, String filePath);
	// 첨부파일 가져오기
	public String getAttachmentFile(int boardIdx, int attachIdx);
}
