package com.newsp.reply;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReplyMapper {

	List<Reply> findPage(@Param("boardIdx") int boardIdx, @Param("offset") int offset, @Param("size") int size);

	int countByBoard(int boardIdx);

	Optional<Reply> findByIdx(int idx);

	List<Reply> findByUser(int userIdx);

	/** 댓글/대댓글 저장. 생성된 idx가 reply.idx에 채워진다. */
	void insert(Reply reply);

	/** 최상위 댓글은 parent_reply_idx를 자기 자신으로 설정 */
	void markAsRoot(int idx);

	void updateContent(@Param("idx") int idx, @Param("content") String content);

	void delete(int idx);

	void deleteByBoard(int boardIdx);
}
