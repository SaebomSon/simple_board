package com.newsp.service;

import com.newsp.vo.BoardVO;

public interface BoardService {
	// 새 글 작성
	public Integer insertNewContent(BoardVO vo);
	// attachment_idx_list update
	public void updateAttachIdx(int boardIdx, String attachmentIdxList);
}
