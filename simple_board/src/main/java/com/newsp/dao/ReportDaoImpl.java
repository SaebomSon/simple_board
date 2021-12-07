package com.newsp.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.newsp.vo.ReportVO;

public class ReportDaoImpl implements ReportDao {
	
	private static final String STATEMENT ="com.newsp.mapper.ReportMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insertReport(ReportVO vo) {
		sqlSession.insert(STATEMENT + "insertReport", vo);
	}

	@Override
	public Integer getReportCount(int boardIdx) {
		int reportCount = sqlSession.selectOne(STATEMENT + "getReportCount", boardIdx);
		
		return reportCount;
	}

	@Override
	public List<ReportVO> getReportInfo(int boardIdx) {
		return sqlSession.selectList(STATEMENT + "getReportInfo", boardIdx);
	}

	@Override
	public List<ReportVO> getReportList() {
		return sqlSession.selectList(STATEMENT + "getReportList");
	}

}
