package com.newsp.dao;

import java.util.List;

import com.newsp.vo.NoticeVO;

public interface NoticeDao {
	// 공지 list 가져오기
	public List<NoticeVO> getNoticeList();

}
