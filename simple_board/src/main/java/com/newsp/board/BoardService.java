package com.newsp.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.newsp.common.BadRequestException;
import com.newsp.common.BoardType;
import com.newsp.common.NotFoundException;
import com.newsp.common.Pagination;
import com.newsp.reply.ReplyMapper;
import com.newsp.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	public static final int PAGE_SIZE = 15;
	public static final int PAGE_BLOCK_SIZE = 3;
	public static final int MAX_ATTACHMENTS = 5;
	/** 신고가 이 수 이상 쌓이면 관리자 화면에 노출 */
	public static final int REPORT_THRESHOLD = 10;

	private final BoardMapper boardMapper;
	private final AttachmentMapper attachmentMapper;
	private final ReportMapper reportMapper;
	private final ReplyMapper replyMapper;
	private final BoardAccess boardAccess;
	private final FileStorage fileStorage;

	public record Page(List<Board> boards, Pagination pagination) {
	}

	public record Detail(Board board, List<Attachment> attachments, boolean reportedByMe) {
	}

	public List<Board> latest() {
		return boardMapper.findLatest(12);
	}

	public List<Board> topByHits() {
		return boardMapper.findTopByHits(5);
	}

	public List<Board> topByReplyCount() {
		return boardMapper.findTopByReplyCount(5);
	}

	public Page list(BoardSearch search, int page, User me) {
		boardAccess.checkReadable(BoardType.of(search.type()), me);
		Pagination pagination = new Pagination(page, PAGE_SIZE, boardMapper.count(search), PAGE_BLOCK_SIZE);
		List<Board> boards = boardMapper.findPage(search, pagination.offset(), pagination.size());
		return new Page(boards, pagination);
	}

	/** 게시글 보기 (조회수 1 증가) */
	@Transactional
	public Detail view(int idx, User me) {
		boardAccess.readable(idx, me);
		boardMapper.increaseHits(idx);
		Board board = boardMapper.findByIdx(idx).orElseThrow();
		return new Detail(board, attachmentMapper.findByBoard(idx), reportMapper.exists(idx, me.getIdx()));
	}

	public Board getForEdit(int idx, User me) {
		return boardAccess.editable(idx, me);
	}

	public List<Attachment> attachments(int boardIdx) {
		return attachmentMapper.findByBoard(boardIdx);
	}

	@Transactional
	public int write(BoardType type, BoardForm form, List<MultipartFile> files, User me) {
		boardAccess.checkReadable(type, me);
		List<MultipartFile> images = validImages(0, files);
		Board board = new Board();
		board.setUserIdx(me.getIdx());
		board.setType(type.getCode());
		apply(board, form);
		boardMapper.insert(board);
		saveAttachments(board.getIdx(), images);
		return board.getIdx();
	}

	@Transactional
	public void update(int idx, BoardForm form, List<MultipartFile> files, User me) {
		Board board = boardAccess.editable(idx, me);
		List<MultipartFile> images = validImages(attachmentMapper.countByBoard(idx), files);
		apply(board, form);
		boardMapper.update(board);
		saveAttachments(idx, images);
	}

	/** 댓글·신고·첨부파일을 먼저 지운 뒤 게시글을 삭제한다. */
	@Transactional
	public Board delete(int idx, User me) {
		Board board = boardAccess.deletable(idx, me);
		replyMapper.deleteByBoard(idx);
		reportMapper.deleteByBoard(idx);
		List<Attachment> attachments = attachmentMapper.findByBoard(idx);
		attachmentMapper.deleteByBoard(idx);
		boardMapper.delete(idx);
		attachments.forEach(a -> fileStorage.delete(a.getFileName()));
		return board;
	}

	@Transactional
	public void deleteAll(List<Integer> idxs, User me) {
		idxs.forEach(idx -> delete(idx, me));
	}

	@Transactional
	public void deleteAttachment(int attachmentIdx, User me) {
		Attachment attachment = attachmentMapper.findByIdx(attachmentIdx)
				.orElseThrow(() -> new NotFoundException("존재하지 않는 첨부파일입니다."));
		boardAccess.editable(attachment.getBoardIdx(), me);
		attachmentMapper.delete(attachmentIdx);
		fileStorage.delete(attachment.getFileName());
	}

	@Transactional
	public void report(int idx, String categoryCode, String etc, User me) {
		Board board = boardAccess.readable(idx, me);
		if (board.getUserIdx() == me.getIdx()) {
			throw new BadRequestException("작성자는 신고할 수 없습니다.");
		}
		if (reportMapper.exists(idx, me.getIdx())) {
			throw new BadRequestException("이미 신고한 게시글입니다.");
		}
		ReportCategory category = ReportCategory.from(categoryCode);
		String content = category.getLabel();
		if (category == ReportCategory.G) {
			if (!StringUtils.hasText(etc)) {
				throw new BadRequestException("신고 사유를 입력하세요.");
			}
			content = etc.length() > 250 ? etc.substring(0, 250) : etc;
		}
		reportMapper.insert(idx, category.name(), content, me.getIdx());
		boardMapper.refreshReportCount(idx);
	}

	public List<Board> myBoards(int userIdx) {
		return boardMapper.findByUser(userIdx);
	}

	public List<Board> reported() {
		return boardMapper.findReported(REPORT_THRESHOLD);
	}

	public List<Report> reportSummary(int boardIdx) {
		return reportMapper.summarize(boardIdx);
	}

	private static void apply(Board board, BoardForm form) {
		board.setSubject(Subject.labelOf(form.getSubject()));
		board.setTitle(form.getTitle());
		board.setContent(form.getContent());
	}

	/** 첨부 개수와 파일 형식을 게시글 저장 전에 검사한다. */
	private List<MultipartFile> validImages(int existingCount, List<MultipartFile> files) {
		List<MultipartFile> images = files == null ? List.of()
				: files.stream().filter(f -> f != null && !f.isEmpty()).toList();
		if (existingCount + images.size() > MAX_ATTACHMENTS) {
			throw new BadRequestException("첨부파일은 최대 " + MAX_ATTACHMENTS + "개까지 등록할 수 있습니다.");
		}
		images.forEach(fileStorage::validate);
		return images;
	}

	private void saveAttachments(int boardIdx, List<MultipartFile> images) {
		for (MultipartFile file : images) {
			Attachment attachment = new Attachment();
			attachment.setBoardIdx(boardIdx);
			attachment.setFileName(fileStorage.store(file));
			attachment.setFilePath(FileStorage.PUBLIC_PATH);
			attachmentMapper.insert(attachment);
		}
	}
}
