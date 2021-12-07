package com.newsp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.ReportDaoImpl;
import com.newsp.vo.ReportVO;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDaoImpl reportDao;
	
	@Override
	public void insertReport(int boardIdx, String category, String content, int reportUser) {
		ReportVO vo = new ReportVO();
		vo.setBoard_idx(boardIdx);
		vo.setCategory(category);
		vo.setContent(content);
		vo.setReport_user_idx(reportUser);
		
		reportDao.insertReport(vo);
	}

	@Override
	public Integer getReportCount(int boardIdx) {
		int reportCount = reportDao.getReportCount(boardIdx);
		
		return reportCount;
	}

	@Override
	public List<ReportVO> getReportInfo(int boardIdx) {
		
		return reportDao.getReportInfo(boardIdx);
	}

	@Override
	public List<ReportVO> getReportList() {
		
		return reportDao.getReportList();
	}

}
