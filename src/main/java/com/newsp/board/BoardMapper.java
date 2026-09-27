package com.newsp.board;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {

	List<Board> findLatest(int limit);

	List<Board> findTopByHits(int limit);

	List<Board> findTopByReplyCount(int limit);

	List<Board> findPage(@Param("search") BoardSearch search, @Param("offset") int offset, @Param("size") int size);

	int count(@Param("search") BoardSearch search);

	Optional<Board> findByIdx(int idx);

	List<Board> findByUser(int userIdx);

	List<Board> findReported(int minReportCount);

	void insert(Board board);

	void update(Board board);

	void delete(int idx);

	void increaseHits(int idx);

	/** reply 테이블 기준으로 reply_count를 다시 계산 */
	void refreshReplyCount(int idx);

	/** report 테이블 기준으로 report_count를 다시 계산 */
	void refreshReportCount(int idx);
}
