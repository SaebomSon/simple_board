package com.newsp.board;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.newsp.common.BadRequestException;
import com.newsp.config.AppProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * 첨부 이미지 저장소.
 * 기존에는 사용자가 올린 파일명을 그대로 webapp 폴더에 저장해 덮어쓰기/경로 조작이 가능했다.
 * 이제 이미지 확장자만 허용하고 UUID 이름으로 upload-dir에 저장한다.
 */
@Slf4j
@Component
public class FileStorage {

	/** 저장된 파일을 서빙하는 경로 (WebConfig) */
	public static final String PUBLIC_PATH = "/uploads";

	private static final Set<String> IMAGE_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "bmp", "webp");

	private final Path root;

	public FileStorage(AppProperties properties) {
		this.root = properties.uploadDir().toAbsolutePath().normalize();
	}

	public Path getRoot() {
		return root;
	}

	/** 이미지 파일이 아니면 예외. 저장 전에 한꺼번에 검사하기 위해 분리했다. */
	public void validate(MultipartFile file) {
		String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		String contentType = file.getContentType();
		if (extension == null || !IMAGE_EXTENSIONS.contains(extension.toLowerCase(Locale.ROOT))
				|| contentType == null || !contentType.startsWith("image/")) {
			throw new BadRequestException("이미지 파일만 첨부할 수 있습니다.");
		}
	}

	/** @return 저장된 파일 이름 */
	public String store(MultipartFile file) {
		validate(file);
		String extension = StringUtils.getFilenameExtension(file.getOriginalFilename()).toLowerCase(Locale.ROOT);
		String storedName = UUID.randomUUID() + "." + extension;
		try (InputStream in = file.getInputStream()) {
			Files.createDirectories(root);
			Files.copy(in, resolve(storedName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new UncheckedIOException("파일 저장 실패", e);
		}
		return storedName;
	}

	public void delete(String fileName) {
		try {
			Files.deleteIfExists(resolve(fileName));
		} catch (IOException e) {
			log.warn("파일 삭제 실패: {}", fileName, e);
		}
	}

	private Path resolve(String fileName) {
		Path path = root.resolve(fileName).normalize();
		if (!path.getParent().equals(root)) {
			throw new BadRequestException("잘못된 파일 이름입니다.");
		}
		return path;
	}
}
