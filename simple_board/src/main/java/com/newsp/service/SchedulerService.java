package com.newsp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.newsp.dao.GradeDaoImpl;
import com.newsp.dao.UsersDaoImpl;
import com.newsp.vo.UsersVO;

@Service
public class SchedulerService {
	@Autowired
	private UsersDaoImpl userDao;
	@Autowired
	private GradeDaoImpl gradeDao;
	
	private void getUpgradeList() {
		List<UsersVO> userGradeList = userDao.getUserForUpgrade();
		
		ArrayList<ArrayList<Integer>> upgradeList = new ArrayList<ArrayList<Integer>>();
		Map<String, Integer> upgradeInfoMap = new HashMap<String, Integer>();
		int gradeChk = 1;
		
		int type = 1;
		int updateLevel = 2;
		
		for (UsersVO u : userGradeList) {
			if (14 <= u.getDay_count() && 3 <= u.getBoard_count() && 10 <= u.getReply_count()) {
				if(u.getLevel() == 1) {
//					System.out.println("준회원2 대상 : " + u.getIdx());

					upgradeInfoMap.put("user_idx", u.getIdx());
					upgradeInfoMap.put("type", type);
					upgradeInfoMap.put("update_level", updateLevel);
					gradeChk = gradeDao.checkGradeInfoIsExist(upgradeInfoMap);
					if(gradeChk == 0) {
						gradeDao.insertGradeInfo(upgradeInfoMap);
					}
				}
			}
			if (30 <= u.getDay_count() && 10 <= u.getBoard_count() && 30 <= u.getReply_count()) {
				if(u.getLevel() == 2) {
//					System.out.println("정회원 대상 : " + u.getIdx());
					updateLevel = 3;
					
					upgradeInfoMap.put("user_idx", u.getIdx());
					upgradeInfoMap.put("type", type);
					upgradeInfoMap.put("update_level", updateLevel);
					gradeChk = gradeDao.checkGradeInfoIsExist(upgradeInfoMap);
					if(gradeChk == 0) {
						gradeDao.insertGradeInfo(upgradeInfoMap);
					}
				}
			}
			if (90 <= u.getDay_count() && 25 <= u.getBoard_count() && 50 <= u.getReply_count()) {
				if(u.getLevel() == 3) {
//					System.out.println("우수회원 대상 : " + u.getIdx());
					updateLevel = 4;
					
					upgradeInfoMap.put("user_idx", u.getIdx());
					upgradeInfoMap.put("type", type);
					upgradeInfoMap.put("update_level", updateLevel);
					gradeChk = gradeDao.checkGradeInfoIsExist(upgradeInfoMap);
					if(gradeChk == 0) {
						gradeDao.insertGradeInfo(upgradeInfoMap);
					}
				}
			}
			if (180 <= u.getDay_count() && 50 <= u.getBoard_count() && 100 <= u.getReply_count()) {
				if(u.getLevel() == 4) {
//					System.out.println("특별회원 대상 : " + u.getIdx());
					updateLevel = 5;

					upgradeInfoMap.put("user_idx", u.getIdx());
					upgradeInfoMap.put("type", type);
					upgradeInfoMap.put("update_level", updateLevel);
					gradeChk = gradeDao.checkGradeInfoIsExist(upgradeInfoMap);
					if(gradeChk == 0) {
						gradeDao.insertGradeInfo(upgradeInfoMap);
					}
				}
			}
		}
	}
	
	// 매주 월요일 자정에 실행하기
	@Scheduled(cron = "0 0 0 ? * WED")
	void insertUserForGrade() {
		getUpgradeList();
		System.out.println("스케쥴러 실행됨");
		
	}
}
