package com.newsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.newsp.vo.GradeVO;

public class GradeDaoImpl implements GradeDao {
	private static final String STATEMENT = "com.newsp.mapper.GradeMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insertGradeInfo(Map<String, Integer> map) {
		sqlSession.insert(STATEMENT + "insertGradeInfo", map);
	}

	@Override
	public List<GradeVO> getGradeList() {
		return sqlSession.selectList(STATEMENT + "getGradeList");
	}

	@Override
	public Integer checkGradeInfoIsExist(Map<String, Integer> map) {
		return sqlSession.selectOne(STATEMENT + "checkGradeInfoIsExist", map);
	}

}
