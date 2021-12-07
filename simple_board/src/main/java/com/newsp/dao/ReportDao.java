package com.newsp.dao;

import java.util.List;

import com.newsp.vo.ReportVO;

public interface ReportDao {
	// 게시글 신고하기
	public void insertReport(ReportVO vo);
	// report_count 가져오기
	public Integer getReportCount(int boardIdx);
	// report 정보 가져오기
	public List<ReportVO> getReportInfo(int boardIdx);
	public List<ReportVO> getReportList();

}
