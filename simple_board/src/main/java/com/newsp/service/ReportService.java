package com.newsp.service;

import java.util.List;

import com.newsp.vo.ReportVO;

public interface ReportService {
	public void insertReport(int boardIdx, String category, String content, int reportUser);
	public Integer getReportCount(int boardIdx);
	public List<ReportVO> getReportInfo(int boardIdx);
	public List<ReportVO> getReportList();

}
