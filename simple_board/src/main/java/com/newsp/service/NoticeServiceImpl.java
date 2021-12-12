package com.newsp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.NoticeDaoImpl;
import com.newsp.vo.NoticeVO;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDaoImpl noticeDao;
	
	@Override
	public List<NoticeVO> getNoticeList() {
		
		return noticeDao.getNoticeList();
	}

	@Override
	public NoticeVO getNoticeInfo(int idx) {
		
		return noticeDao.getNoticeInfo(idx);
	}

	@Override
	public void insertNotice(int userIdx, String type, String title, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_idx", userIdx);
		map.put("type", type);
		map.put("title", title);
		map.put("content", content);
		
		noticeDao.insertNotice(map);
	}

	@Override
	public void updateNotice(int idx, String type, String title, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idx", idx);
		map.put("type", type);
		map.put("title", title);
		map.put("content", content);
		
		noticeDao.updateNotice(map);
		
	}

	@Override
	public void deleteNotice(int idx) {
		noticeDao.deleteNotice(idx);
	}

	@Override
	public List<NoticeVO> getNoticeInBoard(int type) {
		return noticeDao.getNoticeInBoard(type);
	}

	@Override
	public void updateHitsCount(int idx) {
		noticeDao.updateHitsCount(idx);
	}

}
