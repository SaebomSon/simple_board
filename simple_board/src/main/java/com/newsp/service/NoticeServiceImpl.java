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
		List<NoticeVO> list = noticeDao.getNoticeList();
		
		for(NoticeVO n : list) {
			if("A".equals(n.getType())) {
				n.setType("전체 공지");
			}else if("L".equals(n.getType())) {
				n.setType("Leaf");
			}else if("F".equals(n.getType())) {
				n.setType("Flower");
			}else if("D".equals(n.getType())) {
				n.setType("Diamond");
			}
		}
		return list;
	}

	@Override
	public NoticeVO getNoticeInfo(int idx) {
		NoticeVO notice = noticeDao.getNoticeInfo(idx);
		
//		if("A".equals(notice.getType())) {
//			notice.setType("전체 공지");
//		}else if("L".equals(notice.getType())) {
//			notice.setType("Leaf");
//		}else if("F".equals(notice.getType())) {
//			notice.setType("Flower");
//		}else if("D".equals(notice.getType())) {
//			notice.setType("Diamond");
//		}
//		
		return notice;
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

}
