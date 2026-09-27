package com.newsp.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

	int countById(String id);

	int countByNickname(String nickname);

	int countByEmail(String email);

	void insert(User user);

	void updateAuthKey(@Param("email") String email, @Param("authKey") String authKey);

	int verifyEmail(@Param("email") String email, @Param("authKey") String authKey);

	Optional<User> findById(String id);

	Optional<User> findByIdx(int idx);

	Optional<User> findByEmail(String email);

	void updateResetToken(@Param("idx") int idx, @Param("resetToken") String resetToken,
			@Param("expires") LocalDateTime expires);

	Optional<User> findByResetToken(String resetToken);

	/** 새 비밀번호를 저장하고 재설정 토큰을 비운다 */
	void resetPassword(@Param("idx") int idx, @Param("password") String password);

	void updateNickname(@Param("idx") int idx, @Param("nickname") String nickname);

	void updatePassword(@Param("idx") int idx, @Param("password") String password);

	void updatePasswordById(@Param("id") String id, @Param("password") String password);

	void deleteByIdx(int idx);

	/** 가입일수, 게시글 수, 댓글 수를 포함한 전체 회원 */
	List<User> findGradeCandidates();

	void increaseLevel(int idx);
}
