package com.newsp.dao;

import java.util.Map;

import com.newsp.vo.BoardVO;

public interface BoardDao {
	// 새 글 작성
	public void insertNewContent(Map<String, Object> map);
	// attachment_idx_list update
	public void updateAttachIdx(Map<String, Object> map);
}
