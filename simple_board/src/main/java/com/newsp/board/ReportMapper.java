package com.newsp.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportMapper {

	void insert(@Param("boardIdx") int boardIdx, @Param("category") String category,
			@Param("content") String content, @Param("reportUserIdx") int reportUserIdx);

	boolean exists(@Param("boardIdx") int boardIdx, @Param("reportUserIdx") int reportUserIdx);

	List<Report> summarize(int boardIdx);

	void deleteByBoard(int boardIdx);
}
