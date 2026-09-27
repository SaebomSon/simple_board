package com.newsp.board;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.newsp.common.BoardType;
import com.newsp.notice.NoticeService;
import com.newsp.security.CurrentUser;
import com.newsp.user.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final NoticeService noticeService;

	/** 게시판 목록 + 검색 (기존 /boardType, /search 통합) */
	@GetMapping("/boards/{slug:[a-z]+}")
	public String list(@PathVariable String slug, @RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String option, @RequestParam(required = false) String keyword,
			@CurrentUser User me, Model model) {
		BoardType boardType = BoardType.fromSlug(slug);
		int type = boardType.getCode();
		BoardSearch search = BoardSearch.of(type, option, keyword);
		BoardService.Page result = boardService.list(search, page, me);

		model.addAttribute("boardType", boardType);
		model.addAttribute("search", search);
		model.addAttribute("boards", result.boards());
		model.addAttribute("pagination", result.pagination());
		model.addAttribute("notices", search.isSearching() ? List.of() : noticeService.forBoard(type));
		model.addAttribute("searchOptions", SearchOption.values());
		model.addAttribute("pageUrl", pageUrl(search));
		return "board/list";
	}

	/** 검색 조건을 유지한 페이지 이동 주소 ("...&page=" 까지) */
	private static String pageUrl(BoardSearch search) {
		return UriComponentsBuilder.fromPath("/boards/" + BoardType.of(search.type()).getSlug())
				.queryParamIfPresent("option", Optional.ofNullable(search.option()).map(SearchOption::getParam))
				.queryParamIfPresent("keyword", Optional.ofNullable(search.keyword()))
				.queryParam("page", "")
				.encode()
				.toUriString();
	}

	/** 예전 숫자 주소(/boards/1, /boards/1/write)는 새 주소로 영구 이동 */
	@GetMapping({"/boards/{code:\\d+}", "/boards/{code:\\d+}/write"})
	public void legacyBoardUrl(@PathVariable int code, HttpServletRequest request, HttpServletResponse response) {
		String path = request.getRequestURI().replaceFirst("/boards/\\d+", "/boards/" + BoardType.of(code).getSlug());
		String query = request.getQueryString();
		response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
		response.setHeader(HttpHeaders.LOCATION, query == null ? path : path + "?" + query);
	}

	@GetMapping("/boards/{slug:[a-z]+}/write")
	public String writeForm(@PathVariable String slug, Model model) {
		model.addAttribute("boardType", BoardType.fromSlug(slug));
		model.addAttribute("form", new BoardForm());
		model.addAttribute("attachments", List.of());
		return "board/form";
	}

	@PostMapping("/boards/{slug:[a-z]+}")
	public String write(@PathVariable String slug, @Valid @ModelAttribute("form") BoardForm form, BindingResult binding,
			@RequestParam(name = "files", required = false) List<MultipartFile> files,
			@CurrentUser User me, Model model) {
		BoardType boardType = BoardType.fromSlug(slug);
		if (binding.hasErrors()) {
			model.addAttribute("boardType", boardType);
			model.addAttribute("attachments", List.of());
			return "board/form";
		}
		int idx = boardService.write(boardType, form, files, me);
		return "redirect:/posts/" + idx;
	}

	@GetMapping("/posts/{idx}")
	public String detail(@PathVariable int idx, @RequestParam(defaultValue = "1") int page,
			@CurrentUser User me, Model model) {
		BoardService.Detail detail = boardService.view(idx, me);
		model.addAttribute("board", detail.board());
		model.addAttribute("boardType", detail.board().getBoardType());
		model.addAttribute("attachments", detail.attachments());
		model.addAttribute("reportedByMe", detail.reportedByMe());
		model.addAttribute("reportCategories", ReportCategory.values());
		model.addAttribute("page", page);
		return "board/detail";
	}

	@GetMapping("/posts/{idx}/edit")
	public String editForm(@PathVariable int idx, @CurrentUser User me, Model model) {
		Board board = boardService.getForEdit(idx, me);
		model.addAttribute("board", board);
		model.addAttribute("boardType", board.getBoardType());
		model.addAttribute("form", BoardForm.from(board));
		model.addAttribute("attachments", boardService.attachments(idx));
		return "board/form";
	}

	@PostMapping("/posts/{idx}/edit")
	public String edit(@PathVariable int idx, @Valid @ModelAttribute("form") BoardForm form, BindingResult binding,
			@RequestParam(name = "files", required = false) List<MultipartFile> files,
			@CurrentUser User me, Model model, RedirectAttributes redirect) {
		if (binding.hasErrors()) {
			Board board = boardService.getForEdit(idx, me);
			model.addAttribute("board", board);
			model.addAttribute("boardType", board.getBoardType());
			model.addAttribute("attachments", boardService.attachments(idx));
			return "board/form";
		}
		boardService.update(idx, form, files, me);
		redirect.addFlashAttribute("message", "게시물이 수정되었습니다.");
		return "redirect:/posts/" + idx;
	}

	@PostMapping("/posts/{idx}/delete")
	public String delete(@PathVariable int idx, @CurrentUser User me) {
		Board board = boardService.delete(idx, me);
		return "redirect:/boards/" + board.getBoardType().getSlug();
	}

	@PostMapping("/posts/{idx}/report")
	public String report(@PathVariable int idx, @RequestParam String category,
			@RequestParam(required = false) String etc, @CurrentUser User me,
			RedirectAttributes redirect) {
		boardService.report(idx, category, etc, me);
		redirect.addFlashAttribute("message", "신고가 접수되었습니다.");
		return "redirect:/posts/" + idx;
	}

	@GetMapping("/my/posts")
	public String myPosts(@CurrentUser User me, Model model) {
		model.addAttribute("boards", boardService.myBoards(me.getIdx()));
		return "my/posts";
	}

	@PostMapping("/my/posts/delete")
	public String deleteMyPosts(@RequestParam(name = "each", required = false) List<Integer> idxs,
			@CurrentUser User me) {
		if (idxs != null) {
			boardService.deleteAll(idxs, me);
		}
		return "redirect:/my/posts";
	}
}
