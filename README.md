# Simple Board
![board_main](https://user-images.githubusercontent.com/46676608/143483994-692464c5-a289-43bf-8785-5faa82b7a407.jpg)

## 프로젝트 개요
개인으로 진행한 프로젝트로, 가장 간단하고 기본이 되는 게시판을 구현하였습니다.

## 기술 스택
- Java 17, Spring Boot 3.5 (Spring MVC, Spring Security 6, Validation, Mail)
- MyBatis 3 (mybatis-spring-boot-starter), MySQL
- Thymeleaf, Bootstrap 5, Bootstrap Icons, Pretendard 폰트, jQuery
- 테스트: JUnit 5, MockMvc, H2(MySQL 모드)

## 실행 방법
프로젝트는 `simple_board/` 폴더에 있습니다.

### MySQL 없이 바로 실행 (local 프로필)
인메모리 H2에 스키마와 샘플 데이터(`src/main/resources/db/data-local.sql`)를 올려 실행합니다.
인증 메일은 보내지 않고 링크를 로그로 출력합니다.
```bash
cd simple_board
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```
http://localhost:8080 — 샘플 계정은 `data-local.sql` 상단 주석을 참고하세요.

### MySQL로 실행
설정은 환경 변수로 주입합니다(기존 `config.properties` 대체).

| 환경 변수 | 설명 | 기본값 |
| --- | --- | --- |
| `DB_URL` | JDBC URL | `jdbc:mysql://localhost:3306/simple_board?...` |
| `DB_USERNAME` / `DB_PASSWORD` | DB 계정 | `root` / (없음) |
| `MAIL_USERNAME` / `MAIL_PASSWORD` | Gmail SMTP 계정(앱 비밀번호) | - |
| `APP_BASE_URL` | 인증 메일 링크의 서버 주소 | `http://localhost:8080` |
| `APP_UPLOAD_DIR` | 첨부 이미지 저장 폴더 | `./uploads` |

v1 DB를 그대로 쓰는 경우 먼저 `src/main/resources/db/upgrade-v1-to-v2.sql`을 실행하세요
(비밀번호 컬럼 길이 확장. 기존 평문 비밀번호는 로그인 시 BCrypt로 자동 전환됩니다).

### 테스트
```bash
cd simple_board
./mvnw test
```

## 프로젝트 구조
기능별 패키지로 나누었습니다. (`com.newsp`)
```
config/    설정(AppProperties, WebConfig)
security/  Spring Security 설정, 로그인 회원(@CurrentUser)
common/    페이지네이션, 게시판 종류, 예외 처리
user/      회원가입·로그인·회원정보
board/     게시판·게시글·첨부파일·신고
reply/     댓글·대댓글
notice/    공지
question/  문의·답변
admin/     관리자 화면·등업
```

## DataBase
- ERD
![erd_img](https://user-images.githubusercontent.com/46676608/143050907-2f85bdf3-6557-45fd-93c0-765eb4fcf46f.jpg)

- 테이블 명세<br>
 [보기](https://www.notion.so/b0b18d840f654013af9fa60d1998d106)

## 주요 기능
- User
    - 회원가입
    - 로그인
    - 로그아웃
    - 아이디 찾기 (가입 이메일로 발송)
    - 비밀번호 찾기 (메일로 받은 재설정 링크, 30분 1회용)
    - 회원정보 보기
    - 회원정보 수정
    - 회원 탈퇴
- Board
    - 등급별 게시판
    - 게시글 작성 / 수정 / 삭제
    - 게시글 찾기
    - 내가 쓴 게시글 모아보기
- Reply
    - 댓글, 대댓글 작성 / 수정 / 삭제
    - 내가 쓴 댓글 모아보기
- Report
    - 게시글 신고하기 
- Question
    - 문의글 작성하기
- ~~Admin~~(추가할 사항)
    - ~~회원 관리(등급, 탈퇴)~~
    - ~~게시글 신고 관리~~
    - ~~공지 관리~~
    - 
## API 명세
[API 명세 보기](https://www.notion.so/API-79818fbe60ba4466a18a0c97d9e4017f)
