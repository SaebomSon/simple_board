package com.newsp.user;

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

	void updateNickname(@Param("idx") int idx, @Param("nickname") String nickname);

	void updatePassword(@Param("idx") int idx, @Param("password") String password);

	void updatePasswordById(@Param("id") String id, @Param("password") String password);

	void deleteByIdx(int idx);

	/** 가입일수, 게시글 수, 댓글 수를 포함한 전체 회원 */
	List<User> findGradeCandidates();

	void increaseLevel(int idx);
}
