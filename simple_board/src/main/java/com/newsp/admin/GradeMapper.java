package com.newsp.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GradeMapper {

	List<Grade> findPending(int type);

	boolean existsPending(@Param("userIdx") int userIdx, @Param("type") int type,
			@Param("updateLevel") int updateLevel);

	void insert(@Param("userIdx") int userIdx, @Param("type") int type, @Param("updateLevel") int updateLevel);

	/** 해당 회원의 대기 중인 요청을 처리 완료로 변경 */
	int approve(@Param("userIdx") int userIdx, @Param("type") int type);
}
