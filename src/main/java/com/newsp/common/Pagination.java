package com.newsp.common;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 페이지 번호와 페이지 블럭(« 1 2 3 ») 계산.
 * 기존에 컨트롤러마다 복사돼 있던 계산식을 한 곳으로 모은 것.
 */
public record Pagination(int page, int size, int total, int blockSize) {

	public Pagination {
		if (size < 1 || blockSize < 1) {
			throw new IllegalArgumentException("size and blockSize must be positive");
		}
		page = Math.max(1, page);
	}

	public int offset() {
		return (page - 1) * size;
	}

	public int lastPage() {
		return Math.max(1, (int) Math.ceil((double) total / size));
	}

	public int blockStart() {
		return ((page - 1) / blockSize) * blockSize + 1;
	}

	public int blockEnd() {
		return Math.min(blockStart() + blockSize - 1, lastPage());
	}

	public boolean hasPrevBlock() {
		return blockStart() > 1;
	}

	public boolean hasNextBlock() {
		return blockEnd() < lastPage();
	}

	public List<Integer> pages() {
		return IntStream.rangeClosed(blockStart(), blockEnd()).boxed().toList();
	}
}
