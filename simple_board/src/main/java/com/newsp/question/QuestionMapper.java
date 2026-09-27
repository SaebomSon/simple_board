package com.newsp.question;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestionMapper {

	void insert(Question question);

	/** 미처리 문의글 */
	List<Question> findOpen();

	Optional<Question> findByIdx(int idx);

	List<Question> findByUser(int userIdx);

	void markDone(int idx);

	void delete(int idx);

	Optional<Answer> findAnswer(int questionIdx);

	void insertAnswer(@Param("questionIdx") int questionIdx, @Param("userIdx") int userIdx,
			@Param("content") String content);

	void deleteAnswers(int questionIdx);
}
