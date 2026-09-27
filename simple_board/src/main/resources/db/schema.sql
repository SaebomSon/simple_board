-- Simple Board 스키마 (MySQL / H2 MySQL 모드 공용)
-- 운영 DB는 이미 존재하므로 local 프로필과 테스트에서만 자동 실행된다.

CREATE TABLE IF NOT EXISTS user (
    idx          INT AUTO_INCREMENT PRIMARY KEY,
    id           VARCHAR(45)  NOT NULL UNIQUE,
    password     VARCHAR(100) NOT NULL,
    nickname     VARCHAR(45)  NOT NULL UNIQUE,
    email        VARCHAR(100) NOT NULL UNIQUE,
    level        INT          NOT NULL DEFAULT 1,
    level_image  VARCHAR(200),
    signup_date  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    auth_key     VARCHAR(45),
    auth_status  INT          NOT NULL DEFAULT 0,
    user_status  INT          NOT NULL DEFAULT 2,
    warning      INT          NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS board (
    idx                  INT AUTO_INCREMENT PRIMARY KEY,
    user_idx             INT           NOT NULL,
    type                 INT           NOT NULL,
    subject              VARCHAR(50),
    title                VARCHAR(100)  NOT NULL,
    content              VARCHAR(5000) NOT NULL,
    hits                 INT           NOT NULL DEFAULT 0,
    attachment_idx_list  VARCHAR(50),
    reply_count          INT           NOT NULL DEFAULT 0,
    report_count         INT           NOT NULL DEFAULT 0,
    written_date         DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_date          DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_idx) REFERENCES user (idx)
);

CREATE TABLE IF NOT EXISTS attachment (
    idx        INT AUTO_INCREMENT PRIMARY KEY,
    board_idx  INT          NOT NULL,
    file_name  VARCHAR(50)  NOT NULL,
    file_path  VARCHAR(200) NOT NULL,
    date_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (board_idx) REFERENCES board (idx)
);

CREATE TABLE IF NOT EXISTS reply (
    idx               INT AUTO_INCREMENT PRIMARY KEY,
    board_idx         INT          NOT NULL,
    user_idx          INT          NOT NULL,
    content           VARCHAR(200) NOT NULL,
    parent_reply_idx  INT          NOT NULL DEFAULT 0,
    reply_seq         INT          NOT NULL DEFAULT 0,
    reply_depth       INT          NOT NULL DEFAULT 0,
    written_date      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_date       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (board_idx) REFERENCES board (idx),
    FOREIGN KEY (user_idx) REFERENCES user (idx)
);

CREATE TABLE IF NOT EXISTS report (
    idx              INT AUTO_INCREMENT PRIMARY KEY,
    board_idx        INT          NOT NULL,
    category         VARCHAR(1)   NOT NULL,
    content          VARCHAR(250),
    report_user_idx  INT          NOT NULL,
    reported_date    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status           INT          NOT NULL DEFAULT 0,
    FOREIGN KEY (board_idx) REFERENCES board (idx)
);

CREATE TABLE IF NOT EXISTS notice (
    idx           INT AUTO_INCREMENT PRIMARY KEY,
    user_idx      INT           NOT NULL,
    type          INT           NOT NULL,
    title         VARCHAR(100)  NOT NULL,
    content       VARCHAR(5000) NOT NULL,
    hits          INT           NOT NULL DEFAULT 0,
    written_date  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_date   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_idx) REFERENCES user (idx)
);

CREATE TABLE IF NOT EXISTS grade (
    idx            INT AUTO_INCREMENT PRIMARY KEY,
    user_idx       INT      NOT NULL,
    type           INT      NOT NULL,
    update_level   INT      NOT NULL,
    written_date   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    approval_date  DATETIME,
    status         INT      NOT NULL DEFAULT 0,
    FOREIGN KEY (user_idx) REFERENCES user (idx)
);

CREATE TABLE IF NOT EXISTS question (
    idx           INT AUTO_INCREMENT PRIMARY KEY,
    user_idx      INT           NOT NULL,
    subject       VARCHAR(10)   NOT NULL,
    title         VARCHAR(100)  NOT NULL,
    content       VARCHAR(5000) NOT NULL,
    written_date  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status        INT           NOT NULL DEFAULT 0,
    FOREIGN KEY (user_idx) REFERENCES user (idx)
);

CREATE TABLE IF NOT EXISTS answer (
    idx            INT AUTO_INCREMENT PRIMARY KEY,
    user_idx       INT           NOT NULL,
    question_idx   INT           NOT NULL,
    content        VARCHAR(3000) NOT NULL,
    answered_date  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_idx) REFERENCES user (idx),
    FOREIGN KEY (question_idx) REFERENCES question (idx)
);
