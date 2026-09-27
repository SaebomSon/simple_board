package com.newsp.admin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.newsp.user.User;

class GradeRuleTest {

	private static User user(int level, int days, int boards, int replies) {
		User user = new User();
		user.setLevel(level);
		user.setDayCount(days);
		user.setBoardCount(boards);
		user.setReplyCount(replies);
		return user;
	}

	@Test
	void 조건을_만족하면_다음_등급() {
		assertThat(GradeRule.nextLevelFor(user(1, 14, 3, 10))).contains(2);
		assertThat(GradeRule.nextLevelFor(user(2, 30, 10, 30))).contains(3);
		assertThat(GradeRule.nextLevelFor(user(4, 180, 50, 100))).contains(5);
	}

	@Test
	void 조건이_부족하거나_최고등급이면_없음() {
		assertThat(GradeRule.nextLevelFor(user(1, 13, 3, 10))).isEmpty();
		assertThat(GradeRule.nextLevelFor(user(5, 999, 999, 999))).isEmpty();
	}

	/** 기존 코드는 루프 밖의 updateLevel 변수를 재사용해서, 앞 회원의 값이 다음 회원에게 새어 나갔다. */
	@Test
	void 회원마다_독립적으로_계산() {
		assertThat(GradeRule.nextLevelFor(user(3, 90, 25, 50))).contains(4);
		assertThat(GradeRule.nextLevelFor(user(1, 14, 3, 10))).contains(2);
	}
}
