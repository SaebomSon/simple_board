package com.newsp.board;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.newsp.common.BoardType;
import com.newsp.common.NotFoundException;
import com.newsp.user.User;

import lombok.RequiredArgsConstructor;

/**
 * 게시판 등급/작성자 권한 확인.
 * 기존에는 화면(JS)에서만 확인해서 URL을 직접 호출하면 우회할 수 있었다.
 */
@Component
@RequiredArgsConstructor
public class BoardAccess {

	private final BoardMapper boardMapper;

	public void checkReadable(BoardType type, User me) {
		if (!me.canAccess(type)) {
			throw new AccessDeniedException("게시판에 접근할 수 없는 등급입니다.");
		}
	}

	/** 게시글을 읽을 수 있으면 반환 */
	public Board readable(int boardIdx, User me) {
		Board board = boardMapper.findByIdx(boardIdx)
				.orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));
		checkReadable(board.getBoardType(), me);
		return board;
	}

	/** 작성자만 수정할 수 있다 */
	public Board editable(int boardIdx, User me) {
		Board board = readable(boardIdx, me);
		if (board.getUserIdx() != me.getIdx()) {
			throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
		}
		return board;
	}

	/** 작성자 또는 관리자만 삭제할 수 있다 */
	public Board deletable(int boardIdx, User me) {
		Board board = readable(boardIdx, me);
		if (board.getUserIdx() != me.getIdx() && !me.isAdmin()) {
			throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
		}
		return board;
	}
}
