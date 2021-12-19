package com.newsp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newsp.vo.GradeVO;

public interface GradeDao {
	// 등급 대상 insert
	public void insertGradeInfo(Map<String, Integer> map);
	// 등급 대상 list 가져오기
	public List<GradeVO> getGradeList();
	// 존재하는 등급관리 정보인지 체크하기
	public Integer checkGradeInfoIsExist(Map<String, Integer> map);

}
