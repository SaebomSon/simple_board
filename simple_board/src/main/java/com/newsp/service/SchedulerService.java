package com.newsp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.newsp.dao.UsersDao;
import com.newsp.vo.UsersVO;

@Service
public class SchedulerService {
	@Autowired
	private UsersDao userDao;

	private ArrayList<Integer> getUpgradeList() {
		List<UsersVO> userGradeList = userDao.getUserForUpgrade();
		ArrayList<Integer> upgradeList = new ArrayList<Integer>();
		
		for (UsersVO u : userGradeList) {
			int userIdx = 0;
//			System.out.println("user idx : " + u.getIdx() + " 가입일수 : " + u.getDay_count() + " 게시글 수 : " + u.getBoard_count() + " 댓글 수 : " + u.getReply_count());
			if (14 <= u.getDay_count() && 3 <= u.getBoard_count() && 10 <= u.getReply_count()) {
				if(u.getLevel() == 1) {
					System.out.println("준회원2 대상 : " + u.getIdx());
					upgradeList.add(u.getIdx());
				}
			}
			if (30 <= u.getDay_count() && 10 <= u.getBoard_count() && 30 <= u.getReply_count()) {
				if(u.getLevel() == 2) {
					System.out.println("정회원 대상 : " + u.getIdx());
					upgradeList.add(u.getIdx());
				}
			}
			if (90 <= u.getDay_count() && 25 <= u.getBoard_count() && 50 <= u.getReply_count()) {
				if(u.getLevel() == 3) {
					System.out.println("우수회원 대상 : " + u.getIdx());
					upgradeList.add(u.getIdx());
				}
			}
			if (180 <= u.getDay_count() && 50 <= u.getBoard_count() && 100 <= u.getReply_count()) {
				if(u.getLevel() == 4) {
					System.out.println("특별회원 대상 : " + u.getIdx());
					upgradeList.add(u.getIdx());
				}
			}
			
		}
		return upgradeList;
	}

	@Scheduled(cron = "*/30 * * * * *")
	void insertUserForGrade() {
		ArrayList<Integer> upgradeIdxList = getUpgradeList();
		System.out.println(upgradeIdxList);
//		System.out.println("30초 스케쥴러 실행");
		// insert grade
	}
}
