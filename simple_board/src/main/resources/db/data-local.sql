-- local 프로필 전용 샘플 데이터 (운영 DB에는 사용하지 않는다)
-- 모든 계정의 비밀번호: test1234!
--   admin   : 관리자
--   leaf    : 준회원1 (광장만 접근)
--   diamond : 우수회원 (모든 게시판 접근)
--   legacy  : v1처럼 비밀번호가 평문으로 저장된 회원. 로그인하면 BCrypt로 자동 교체된다.
--   pending : 이메일 인증 전 회원 (로그인 불가)
INSERT INTO user (id, password, nickname, email, level, auth_status, user_status) VALUES
    ('admin',   '{bcrypt}$2a$10$xgC4mNgzmJHZZIbuJAFXvefxL6gwOD2XHzjCiO5PJehhXHnzG4uuC', '관리자',  'admin@example.com',   5, 1, 1),
    ('leaf',    '{bcrypt}$2a$10$xgC4mNgzmJHZZIbuJAFXvefxL6gwOD2XHzjCiO5PJehhXHnzG4uuC', '새싹',    'leaf@example.com',    1, 1, 2),
    ('diamond', '{bcrypt}$2a$10$xgC4mNgzmJHZZIbuJAFXvefxL6gwOD2XHzjCiO5PJehhXHnzG4uuC', '다이아',  'diamond@example.com', 4, 1, 2),
    ('legacy',  'test1234!',                                                              '옛회원',  'legacy@example.com',  2, 1, 2),
    ('pending', '{bcrypt}$2a$10$xgC4mNgzmJHZZIbuJAFXvefxL6gwOD2XHzjCiO5PJehhXHnzG4uuC', '대기중',  'pending@example.com', 1, 0, 2);

INSERT INTO board (user_idx, type, subject, title, content, hits) VALUES
    (2, 1, '사담', '첫 번째 글입니다', '안녕하세요.
줄바꿈도 그대로 보여야 합니다.', 3),
    (3, 1, NULL, '<script>alert(1)</script> 제목도 이스케이프', '<b>굵게</b> 가 아니라 글자 그대로 보여야 합니다.', 10),
    (3, 4, '정보', '다이아 게시판 글', '등급 4 이상만 볼 수 있습니다.', 1);

INSERT INTO notice (user_idx, type, title, content) VALUES
    (1, 0, '전체 공지입니다', 'Simple Board에 오신 것을 환영합니다.');

INSERT INTO question (user_idx, subject, title, content) VALUES
    (2, 'L', '등업은 언제 되나요?', '조건이 궁금합니다.');
