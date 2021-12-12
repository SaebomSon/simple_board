package com.newsp.dao;

import java.util.List;
import java.util.Map;

import com.newsp.vo.NoticeVO;

public interface NoticeDao {
	// 공지 list 가져오기
	public List<NoticeVO> getNoticeList();
	// 선택한 공지 불러오기
	public NoticeVO getNoticeInfo(int idx);
	// 공지 작성하기
	public void insertNotice(Map<String, Object> map);
	// 공지 수정하기
	public void updateNotice(Map<String, Object> map);
	//공지 삭제하기
	public void deleteNotice(int idx);
	// 게시판에 맞는 공지 불러오기
	public List<NoticeVO> getNoticeInBoard(int type);
	// 조회수 update
	public void updateHitsCount(int idx);
}
