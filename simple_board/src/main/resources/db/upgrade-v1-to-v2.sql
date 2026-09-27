-- 기존(v1, Spring 4) 운영 DB를 v2(Spring Boot 3)에서 사용하기 위한 변경 사항.
-- 배포 전에 한 번만 실행한다.

-- 1) 비밀번호를 BCrypt 해시("{bcrypt}$2a$10$..." 68자)로 저장하기 위해 컬럼 길이를 늘린다.
--    기존 평문 비밀번호는 그대로 로그인되며, 로그인에 성공하는 순간 해시로 자동 교체된다.
ALTER TABLE user MODIFY password VARCHAR(100) NOT NULL;

-- 2) 첨부파일은 이제 app.upload-dir(기본 ./uploads)에 UUID 파일명으로 저장된다.
--    기존 파일(webapp/resources/image/*)은 upload-dir로 복사해야 기존 게시글 이미지가 계속 보인다.
--    board.attachment_idx_list 컬럼은 더 이상 사용하지 않는다(attachment.board_idx로 조회).
