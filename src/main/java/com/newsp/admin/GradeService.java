package com.newsp.admin;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsp.common.BadRequestException;
import com.newsp.user.User;
import com.newsp.user.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GradeService {

	private final GradeMapper gradeMapper;
	private final UserMapper userMapper;

	public List<Grade> pending(int type) {
		return gradeMapper.findPending(type);
	}

	/** 등업 요청 승인: 대기 중인 등업 요청이 있을 때만 등급을 1 올린다. */
	@Transactional
	public void approveUpgrade(int userIdx) {
		if (gradeMapper.approve(userIdx, Grade.TYPE_UPGRADE) == 0) {
			throw new BadRequestException("대기 중인 등업 요청이 없습니다.");
		}
		userMapper.increaseLevel(userIdx);
	}

	/** 등업 조건을 만족하는 회원을 승인 대기 목록에 추가 */
	@Transactional
	@Scheduled(cron = "${app.grade-cron}")
	public void collectUpgradeCandidates() {
		int added = 0;
		for (User user : userMapper.findGradeCandidates()) {
			Integer nextLevel = GradeRule.nextLevelFor(user).orElse(null);
			if (nextLevel != null && !gradeMapper.existsPending(user.getIdx(), Grade.TYPE_UPGRADE, nextLevel)) {
				gradeMapper.insert(user.getIdx(), Grade.TYPE_UPGRADE, nextLevel);
				added++;
			}
		}
		log.info("등업 대상 집계 완료: {}명 추가", added);
	}
}
