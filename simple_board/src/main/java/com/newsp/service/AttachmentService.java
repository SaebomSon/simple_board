package com.newsp.service;

import java.util.List;

import com.newsp.vo.AttachmentVO;

public interface AttachmentService {
	// insert Attachment
	public void insertAttachment(int boardIdx, String fileName, String filePath);
	// 첨부파일 가져오기
	public String getAttachmentFile(int boardIdx, int attachIdx);
	// 첨부파일 개별 삭제
	public void deleteAttachment(int boardIdx, String fileName);
	// 첨부파일 idx 가져오기
	public List<AttachmentVO> getAttachIdx(int boardIdx);
	// 첨부파일 전체 삭제
	public void deleteAllAttachment(int boardIdx);
}
