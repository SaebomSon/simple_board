package com.newsp.service;

import java.util.List;

import com.newsp.vo.NoticeVO;

public interface NoticeService {
	// 공지 list 가져오기
	public List<NoticeVO> getNoticeList();
	// 선택한 공지 불러오기
	public NoticeVO getNoticeInfo(int idx);
	// 공지 작성하기
	public void insertNotice(int userIdx, String type, String title, String content);
	// 공지 수정하기
	public void updateNotice(int idx, String type, String title, String content);
	// 공지 삭제하기
	public void deleteNotice(int idx);
}
