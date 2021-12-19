package com.newsp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.newsp.dao.GradeDao;
import com.newsp.dao.UsersDao;
import com.newsp.vo.GradeVO;
import com.newsp.vo.UsersVO;

@Service
public class SchedulerService {
	@Autowired
	private UsersDao userDao;
	@Autowired
	private GradeDao gradeDao;

	private ArrayList<ArrayList<Integer>> getUpgradeList() {
		List<UsersVO> userGradeList = userDao.getUserForUpgrade();
		
		ArrayList<ArrayList<Integer>> upgradeList = new ArrayList<ArrayList<Integer>>();
		Map<String, Integer> chkCountMap = new HashMap<String, Integer>();
		int gradeChk = 0;
		
		int type = 1;
		int updateLevel = 2;
		
		for (UsersVO u : userGradeList) {
			ArrayList<Integer> upgradeInfoList = new ArrayList<Integer>();
//			System.out.println("user idx : " + u.getIdx() + " 가입일수 : " + u.getDay_count() + " 게시글 수 : " + u.getBoard_count() + " 댓글 수 : " + u.getReply_count());
			if (14 <= u.getDay_count() && 3 <= u.getBoard_count() && 10 <= u.getReply_count()) {
				if(u.getLevel() == 1) {
					upgradeInfoList.add(type);
					upgradeInfoList.add(u.getIdx());
					upgradeInfoList.add(updateLevel);
					System.out.println("준회원2 대상 : " + u.getIdx());

					chkCountMap.put("user_idx", u.getIdx());
					chkCountMap.put("type", type);
					chkCountMap.put("update_level", updateLevel);
					gradeChk = gradeDao.checkGradeInfoIsExist(chkCountMap);
					if(gradeChk == 0) {
						upgradeList.add(upgradeInfoList);
					}
					System.out.println(gradeChk);
				}
			}
			if (30 <= u.getDay_count() && 10 <= u.getBoard_count() && 30 <= u.getReply_count()) {
				if(u.getLevel() == 2) {
					updateLevel = 3;
					upgradeInfoList.add(type);
					upgradeInfoList.add(u.getIdx());
					upgradeInfoList.add(updateLevel);
					System.out.println("정회원 대상 : " + u.getIdx());
					
					chkCountMap.put("user_idx", u.getIdx());
					chkCountMap.put("type", type);
					chkCountMap.put("update_level", updateLevel);
					gradeChk = gradeDao.checkGradeInfoIsExist(chkCountMap);
					if(gradeChk == 0) {
						upgradeList.add(upgradeInfoList);
					}
				}
			}
			if (90 <= u.getDay_count() && 25 <= u.getBoard_count() && 50 <= u.getReply_count()) {
				if(u.getLevel() == 3) {
					updateLevel = 4;
					upgradeInfoList.add(type);
					upgradeInfoList.add(u.getIdx());
					upgradeInfoList.add(updateLevel);
					System.out.println("우수회원 대상 : " + u.getIdx());
					
					chkCountMap.put("user_idx", u.getIdx());
					chkCountMap.put("type", type);
					chkCountMap.put("update_level", updateLevel);
					gradeChk = gradeDao.checkGradeInfoIsExist(chkCountMap);
					if(gradeChk == 0) {
						upgradeList.add(upgradeInfoList);
					}
				}
			}
			if (180 <= u.getDay_count() && 50 <= u.getBoard_count() && 100 <= u.getReply_count()) {
				if(u.getLevel() == 4) {
					updateLevel = 5;
					upgradeInfoList.add(type);
					upgradeInfoList.add(u.getIdx());
					upgradeInfoList.add(updateLevel);
					System.out.println("특별회원 대상 : " + u.getIdx());

					chkCountMap.put("user_idx", u.getIdx());
					chkCountMap.put("type", type);
					chkCountMap.put("update_level", updateLevel);
					gradeChk = gradeDao.checkGradeInfoIsExist(chkCountMap);
					if(gradeChk == 0) {
						upgradeList.add(upgradeInfoList);
					}
				}
			}
//			upgradeList.add(upgradeInfoList);
		}

		return upgradeList;
	}

	@Scheduled(cron = "*/10 * * * * *")
	void insertUserForGrade() {
		ArrayList<ArrayList<Integer>> upgradeInfoList = getUpgradeList();
		System.out.println(upgradeInfoList);
		
//		gradeDao.insertGradeInfo(map);
//		System.out.println("30초 스케쥴러 실행");
		// insert grade
	}
}
