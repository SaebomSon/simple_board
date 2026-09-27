package com.newsp.config;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application.yml 의 app.* 설정.
 *
 * @param baseUrl     인증 메일 링크에 들어가는 외부 주소
 * @param uploadDir   첨부 이미지 저장 디렉터리
 * @param mailEnabled false면 메일을 보내지 않고 인증 링크를 로그로 남긴다(local 프로필)
 */
@ConfigurationProperties(prefix = "app")
public record AppProperties(String baseUrl, Path uploadDir, boolean mailEnabled) {
}
