package com.newsp.admin;

import java.util.Arrays;
import java.util.Optional;

import com.newsp.user.User;

/**
 * 자동 등업 대상 조건 (현재 등급 → 다음 등급).
 * 기존 SchedulerService의 if 블록 4개를 표로 옮긴 것.
 */
public enum GradeRule {
	TO_ASSOCIATE_2(1, 14, 3, 10),
	TO_REGULAR(2, 30, 10, 30),
	TO_EXCELLENT(3, 90, 25, 50),
	TO_SPECIAL(4, 180, 50, 100);

	private final int fromLevel;
	private final int minDays;
	private final int minBoards;
	private final int minReplies;

	GradeRule(int fromLevel, int minDays, int minBoards, int minReplies) {
		this.fromLevel = fromLevel;
		this.minDays = minDays;
		this.minBoards = minBoards;
		this.minReplies = minReplies;
	}

	/** 조건을 만족하면 올라갈 등급 */
	public static Optional<Integer> nextLevelFor(User user) {
		return Arrays.stream(values())
				.filter(rule -> rule.fromLevel == user.getLevel())
				.filter(rule -> user.getDayCount() >= rule.minDays
						&& user.getBoardCount() >= rule.minBoards
						&& user.getReplyCount() >= rule.minReplies)
				.map(rule -> rule.fromLevel + 1)
				.findFirst();
	}
}
