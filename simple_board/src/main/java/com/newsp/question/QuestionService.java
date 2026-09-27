package com.newsp.question;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsp.common.BadRequestException;
import com.newsp.common.NotFoundException;
import com.newsp.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

	private final QuestionMapper questionMapper;

	@Transactional
	public void ask(QuestionForm form, User me) {
		Question question = new Question();
		question.setUserIdx(me.getIdx());
		question.setSubject(QuestionSubject.isValid(form.getSubject()) ? form.getSubject() : QuestionSubject.NONE.name());
		question.setTitle(form.getTitle());
		question.setContent(form.getContent());
		questionMapper.insert(question);
	}

	/** 작성자 본인과 관리자만 볼 수 있다 */
	public Question get(int idx, User me) {
		Question question = questionMapper.findByIdx(idx)
				.orElseThrow(() -> new NotFoundException("존재하지 않는 문의글입니다."));
		if (question.getUserIdx() != me.getIdx() && !me.isAdmin()) {
			throw new AccessDeniedException("작성자만 볼 수 있습니다.");
		}
		return question;
	}

	public Optional<Answer> answerOf(int questionIdx) {
		return questionMapper.findAnswer(questionIdx);
	}

	public List<Question> mine(int userIdx) {
		return questionMapper.findByUser(userIdx);
	}

	public List<Question> open() {
		return questionMapper.findOpen();
	}

	@Transactional
	public void deleteMine(List<Integer> idxs, User me) {
		for (int idx : idxs) {
			Question question = get(idx, me);
			if (question.getUserIdx() != me.getIdx()) {
				throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
			}
			questionMapper.deleteAnswers(idx);
			questionMapper.delete(idx);
		}
	}

	@Transactional
	public void answer(int questionIdx, String content, User admin) {
		get(questionIdx, admin);
		if (content == null || content.isBlank()) {
			throw new BadRequestException("답변을 입력하세요.");
		}
		if (content.length() > 3000) {
			throw new BadRequestException("답변은 3000자까지 입력할 수 있습니다.");
		}
		if (questionMapper.findAnswer(questionIdx).isPresent()) {
			throw new BadRequestException("이미 답변한 문의글입니다.");
		}
		questionMapper.insertAnswer(questionIdx, admin.getIdx(), content);
	}

	@Transactional
	public void markDone(int idx) {
		questionMapper.markDone(idx);
	}
}
