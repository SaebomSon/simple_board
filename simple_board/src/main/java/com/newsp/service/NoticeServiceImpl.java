package com.newsp.service;

import java.util.List;

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

}
