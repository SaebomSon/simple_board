package com.newsp.reply;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsp.board.Board;
import com.newsp.board.BoardAccess;
import com.newsp.board.BoardMapper;
import com.newsp.common.BadRequestException;
import com.newsp.common.NotFoundException;
import com.newsp.common.Pagination;
import com.newsp.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

	public static final int PAGE_SIZE = 10;
	public static final int PAGE_BLOCK_SIZE = 5;
	public static final int MAX_LENGTH = 200;

	private final ReplyMapper replyMapper;
	private final BoardMapper boardMapper;
	private final BoardAccess boardAccess;

	public record Page(Board board, List<Reply> replies, Pagination pagination) {
	}

	/** page가 0 이하이면 마지막 페이지 */
	public Page page(int boardIdx, int page, User me) {
		Board board = boardAccess.readable(boardIdx, me);
		int total = replyMapper.countByBoard(boardIdx);
		int lastPage = new Pagination(1, PAGE_SIZE, total, PAGE_BLOCK_SIZE).lastPage();
		Pagination pagination = new Pagination(page <= 0 ? lastPage : Math.min(page, lastPage), PAGE_SIZE, total,
				PAGE_BLOCK_SIZE);
		List<Reply> replies = replyMapper.findPage(boardIdx, pagination.offset(), pagination.size());
		return new Page(board, replies, pagination);
	}

	/** @return 댓글이 추가된 마지막 페이지 번호 */
	@Transactional
	public int write(int boardIdx, String content, User me) {
		boardAccess.readable(boardIdx, me);
		Reply reply = newReply(boardIdx, content, me);
		replyMapper.insert(reply);
		replyMapper.markAsRoot(reply.getIdx());
		return refreshCount(boardIdx);
	}

	/** 대댓글: 같은 최상위 댓글 아래에 depth + 1로 저장 */
	@Transactional
	public void writeMention(int targetIdx, String content, User me) {
		Reply target = get(targetIdx);
		boardAccess.readable(target.getBoardIdx(), me);
		Reply reply = newReply(target.getBoardIdx(), content, me);
		reply.setParentReplyIdx(target.getParentReplyIdx());
		reply.setReplyDepth(target.getReplyDepth() + 1);
		replyMapper.insert(reply);
		refreshCount(target.getBoardIdx());
	}

	@Transactional
	public void modify(int idx, String content, User me) {
		Reply reply = owned(idx, me);
		replyMapper.updateContent(reply.getIdx(), validContent(content));
	}

	/** @return 삭제 후 마지막 페이지 번호 */
	@Transactional
	public int delete(int idx, User me) {
		Reply reply = owned(idx, me);
		replyMapper.delete(idx);
		return refreshCount(reply.getBoardIdx());
	}

	@Transactional
	public void deleteAll(List<Integer> idxs, User me) {
		idxs.forEach(idx -> delete(idx, me));
	}

	public List<Reply> myReplies(int userIdx) {
		return replyMapper.findByUser(userIdx);
	}

	private Reply get(int idx) {
		return replyMapper.findByIdx(idx).orElseThrow(() -> new NotFoundException("존재하지 않는 댓글입니다."));
	}

	private Reply owned(int idx, User me) {
		Reply reply = get(idx);
		if (reply.getUserIdx() != me.getIdx()) {
			throw new AccessDeniedException("작성자만 수정/삭제할 수 있습니다.");
		}
		return reply;
	}

	private static Reply newReply(int boardIdx, String content, User me) {
		Reply reply = new Reply();
		reply.setBoardIdx(boardIdx);
		reply.setUserIdx(me.getIdx());
		reply.setContent(validContent(content));
		return reply;
	}

	private static String validContent(String content) {
		if (content == null || content.isBlank()) {
			throw new BadRequestException("댓글을 입력하세요.");
		}
		if (content.length() > MAX_LENGTH) {
			throw new BadRequestException("댓글은 " + MAX_LENGTH + "자까지 입력할 수 있습니다.");
		}
		return content;
	}

	private int refreshCount(int boardIdx) {
		boardMapper.refreshReplyCount(boardIdx);
		return new Pagination(1, PAGE_SIZE, replyMapper.countByBoard(boardIdx), PAGE_BLOCK_SIZE).lastPage();
	}
}
