package com.newsp.board;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachmentMapper {

	void insert(Attachment attachment);

	List<Attachment> findByBoard(int boardIdx);

	Optional<Attachment> findByIdx(int idx);

	int countByBoard(int boardIdx);

	void delete(int idx);

	void deleteByBoard(int boardIdx);
}
