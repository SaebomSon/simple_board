package com.newsp.notice;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper {

	List<Notice> findAll();

	/** 전체 공지 + 해당 게시판 공지 */
	List<Notice> findForBoard(int type);

	Optional<Notice> findByIdx(int idx);

	void insert(Notice notice);

	void update(Notice notice);

	void delete(int idx);

	void increaseHits(int idx);
}
