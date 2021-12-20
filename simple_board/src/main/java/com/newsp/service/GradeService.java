package com.newsp.service;

import java.util.List;

import com.newsp.vo.GradeVO;

public interface GradeService {
	// 등급 list 가져오기
	public List<GradeVO> getGradeList(int type);
	// grade status 변경
	public void switchGradeStatus(int userIdx);
}
