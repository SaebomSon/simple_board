package com.newsp.dao;

import java.util.List;
import java.util.Map;

import com.newsp.vo.AttachmentVO;

public interface AttachmentDao {
	// 첨부파일 등록
	public void insertAttachment(Map<String, Object> map);
	// 첨부파일 가져오기
	public String getAttachmentFile(Map<String, Integer> map);
	// 첨부파일 개별 삭제
	public void deleteAttachment(Map<String, Object> map);
	// 첨부파일의 idx 가져오기
	public List<AttachmentVO> getAttachIdx(int boardIdx);
	// 첨부파일 전체 삭제
	public void deleteAllAttachment(int boardIdx);

}
