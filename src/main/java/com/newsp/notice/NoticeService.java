package com.newsp.notice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsp.common.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

	private final NoticeMapper noticeMapper;

	public List<Notice> all() {
		return noticeMapper.findAll();
	}

	public List<Notice> forBoard(int type) {
		return noticeMapper.findForBoard(type);
	}

	public Notice get(int idx) {
		return noticeMapper.findByIdx(idx).orElseThrow(() -> new NotFoundException("존재하지 않는 공지입니다."));
	}

	@Transactional
	public Notice view(int idx) {
		Notice notice = get(idx);
		noticeMapper.increaseHits(idx);
		return notice;
	}

	@Transactional
	public void write(NoticeForm form, int adminIdx) {
		Notice notice = new Notice();
		notice.setUserIdx(adminIdx);
		apply(notice, form);
		noticeMapper.insert(notice);
	}

	@Transactional
	public void update(int idx, NoticeForm form) {
		Notice notice = get(idx);
		apply(notice, form);
		noticeMapper.update(notice);
	}

	@Transactional
	public void delete(int idx) {
		noticeMapper.delete(idx);
	}

	private static void apply(Notice notice, NoticeForm form) {
		notice.setType(Integer.parseInt(form.getType()));
		notice.setTitle(form.getTitle());
		notice.setContent(form.getContent());
	}
}
