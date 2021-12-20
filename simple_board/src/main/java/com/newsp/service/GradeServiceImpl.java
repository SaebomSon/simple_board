package com.newsp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsp.dao.GradeDaoImpl;
import com.newsp.vo.GradeVO;

@Service
public class GradeServiceImpl implements GradeService {
	
	@Autowired
	private GradeDaoImpl gradeDao;

	@Override
	public List<GradeVO> getGradeList(int type) {
		return gradeDao.getGradeList(type);
	}

	@Override
	public void switchGradeStatus(int userIdx) {
		gradeDao.switchGradeStatus(userIdx);
	}

}
