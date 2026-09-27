package com.newsp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.newsp.board.AttachmentMapper;
import com.newsp.board.BoardMapper;
import com.newsp.board.BoardSearch;
import com.newsp.board.FileStorage;
import com.newsp.reply.ReplyMapper;
import com.newsp.user.AccountRecoveryService;
import com.newsp.user.MailService;
import com.newsp.user.UserMapper;

/**
 * local 샘플 데이터(db/data-local.sql)를 올린 H2에서 전체 애플리케이션을 띄워 검증한다.
 * 회원: 1 admin, 2 leaf(등급1), 3 diamond(등급4), 4 legacy(평문 비밀번호), 5 pending(메일 미인증)
 * 게시글: 1 leaf의 광장 글, 2 diamond의 광장 글, 3 diamond의 다락방 글
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class SimpleBoardIntegrationTest {

	@Autowired
	MockMvc mvc;
	@Autowired
	UserMapper userMapper;
	@Autowired
	BoardMapper boardMapper;
	@Autowired
	ReplyMapper replyMapper;
	@Autowired
	AttachmentMapper attachmentMapper;
	@Autowired
	FileStorage fileStorage;
	@MockitoSpyBean
	MailService mailService;

	@Nested
	class 화면_렌더링 {

		@Test
		void 비회원_화면() throws Exception {
			for (String url : List.of("/", "/signIn", "/signUp", "/findId", "/findPassword", "/resetPassword?token=x")) {
				mvc.perform(get(url)).andExpect(status().isOk());
			}
		}

		@Test
		@WithUserDetails("diamond")
		void 회원_화면() throws Exception {
			for (String url : List.of("/", "/boards/plaza", "/boards/attic", "/boards/plaza?option=title&keyword=글",
					"/boards/plaza/write", "/posts/2", "/posts/2/edit", "/posts/1/replies", "/notices/1",
					"/my/posts", "/my/replies", "/my/questions", "/profile", "/questions/new")) {
				mvc.perform(get(url)).andExpect(status().isOk());
			}
		}

		@Test
		@WithUserDetails("admin")
		void 관리자_화면() throws Exception {
			for (String url : List.of("/admin", "/admin/notices/new", "/admin/notices/1/edit", "/questions/1")) {
				mvc.perform(get(url)).andExpect(status().isOk());
			}
		}
	}

	@Nested
	class 로그인 {

		@Test
		void 비회원은_로그인_페이지로() throws Exception {
			mvc.perform(get("/posts/1").accept(MediaType.TEXT_HTML)).andExpect(redirectedUrlPattern("**/signIn"));
		}

		@Test
		void 비회원의_ajax는_401() throws Exception {
			mvc.perform(delete("/api/replies/1").with(csrf())).andExpect(status().isUnauthorized());
		}

		@Test
		void 관리자는_관리자_화면으로() throws Exception {
			mvc.perform(formLogin("/login").userParameter("loginId").passwordParam("loginPw")
					.user("admin").password("test1234!"))
				.andExpect(redirectedUrl("/admin"));
		}

		@Test
		void 기존_평문_비밀번호로_로그인하면_BCrypt로_교체된다() throws Exception {
			mvc.perform(formLogin("/login").userParameter("loginId").passwordParam("loginPw")
					.user("legacy").password("test1234!"))
				.andExpect(redirectedUrl("/"));

			assertThat(userMapper.findById("legacy").orElseThrow().getPassword()).startsWith("{bcrypt}");
		}

		@Test
		void 메일_인증_전에는_로그인_불가() throws Exception {
			mvc.perform(formLogin("/login").userParameter("loginId").passwordParam("loginPw")
					.user("pending").password("test1234!"))
				.andExpect(redirectedUrl("/signIn?error=unverified"));
		}

		@Test
		void 잘못된_비밀번호() throws Exception {
			mvc.perform(formLogin("/login").userParameter("loginId").passwordParam("loginPw")
					.user("leaf").password("wrong"))
				.andExpect(redirectedUrl("/signIn?error=bad"));
		}
	}

	@Nested
	class 회원가입 {

		@Test
		void 가입하면_비밀번호는_해시로_저장되고_인증키가_생긴다() throws Exception {
			mvc.perform(post("/api/signup").with(csrf()).contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"id":"newbie","pw":"abc123!","nickname":"뉴비","email":"newbie@example.com"}"""))
				.andExpect(status().isOk());

			var user = userMapper.findById("newbie").orElseThrow();
			assertThat(user.getPassword()).startsWith("{bcrypt}");
			assertThat(user.getAuthKey()).hasSize(32);
			assertThat(user.getAuthStatus()).isZero();

			mvc.perform(get("/signUp/confirm").param("email", "newbie@example.com").param("authKey", user.getAuthKey()))
				.andExpect(redirectedUrl("/signIn"));
			assertThat(userMapper.findById("newbie").orElseThrow().getAuthStatus()).isEqualTo(1);
		}

		@Test
		void 서버에서도_입력값을_검증한다() throws Exception {
			mvc.perform(post("/api/signup").with(csrf()).contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"id":"newbie","pw":"short","nickname":"뉴비","email":"newbie@example.com"}"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("비밀번호는 영문,특수문자 포함 6~18자리만 허용됩니다."));

			mvc.perform(post("/api/signup").with(csrf()).contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"id":"leaf","pw":"abc123!","nickname":"다른닉","email":"x@example.com"}"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("동일한 아이디가 존재합니다."));
		}

		@Test
		void 중복_체크() throws Exception {
			mvc.perform(get("/api/signup/check/id").param("value", "leaf"))
				.andExpect(jsonPath("$.available").value(false));
			mvc.perform(get("/api/signup/check/nickname").param("value", "처음보는닉네임"))
				.andExpect(jsonPath("$.available").value(true));
		}
	}

	@Nested
	class 게시판_주소 {

		@Test
		@WithUserDetails("diamond")
		void 예전_숫자_주소는_새_주소로_영구_이동한다() throws Exception {
			mvc.perform(get("/boards/1"))
				.andExpect(status().isMovedPermanently())
				.andExpect(redirectedUrl("/boards/plaza"));
			mvc.perform(get("/boards/4?page=2&keyword={k}", "글"))
				.andExpect(status().isMovedPermanently())
				.andExpect(redirectedUrl("/boards/attic?page=2&keyword=%EA%B8%80"));
			mvc.perform(get("/boards/2/write"))
				.andExpect(redirectedUrl("/boards/lounge/write"));
		}

		@Test
		@WithUserDetails("diamond")
		void 없는_게시판은_404() throws Exception {
			mvc.perform(get("/boards/unknown")).andExpect(status().isNotFound());
			mvc.perform(get("/boards/3")).andExpect(status().isNotFound());
		}
	}

	@Nested
	class 게시판_권한 {

		@Test
		@WithUserDetails("leaf")
		void 등급이_낮으면_게시판과_글에_접근할_수_없다() throws Exception {
			mvc.perform(get("/boards/attic")).andExpect(status().isForbidden());
			mvc.perform(get("/posts/3")).andExpect(status().isForbidden());
			mvc.perform(get("/posts/3/replies")).andExpect(status().isForbidden());
		}

		@Test
		@WithUserDetails("leaf")
		void 요청_파라미터로_등급을_조작할_수_없다() throws Exception {
			mvc.perform(get("/posts/3").param("level", "5").param("userStatus", "1"))
				.andExpect(status().isForbidden());
		}

		@Test
		@WithUserDetails("leaf")
		void 다른_사람의_글은_수정_삭제할_수_없다() throws Exception {
			mvc.perform(get("/posts/2/edit")).andExpect(status().isForbidden());
			mvc.perform(post("/posts/2/edit").with(csrf()).param("title", "hack").param("content", "hack"))
				.andExpect(status().isForbidden());
			mvc.perform(post("/posts/2/delete").with(csrf())).andExpect(status().isForbidden());
			mvc.perform(post("/my/posts/delete").with(csrf()).param("each", "2")).andExpect(status().isForbidden());

			assertThat(boardMapper.findByIdx(2)).isPresent();
		}

		@Test
		@WithUserDetails("admin")
		void 관리자는_다른_사람의_글을_삭제할_수_있다() throws Exception {
			mvc.perform(post("/posts/2/delete").with(csrf())).andExpect(redirectedUrl("/boards/plaza"));

			assertThat(boardMapper.findByIdx(2)).isEmpty();
		}

		@Test
		@WithUserDetails("leaf")
		void 일반_회원은_관리자_기능을_쓸_수_없다() throws Exception {
			mvc.perform(get("/admin")).andExpect(status().isForbidden());
			mvc.perform(post("/admin/users/2/upgrade").with(csrf())).andExpect(status().isForbidden());
			mvc.perform(post("/admin/notices/1/delete").with(csrf())).andExpect(status().isForbidden());
			mvc.perform(get("/api/admin/posts/1/reports")).andExpect(status().isForbidden());
		}

		@Test
		@WithUserDetails("leaf")
		void CSRF_토큰_없는_요청은_거부() throws Exception {
			mvc.perform(post("/posts/1/delete")).andExpect(status().isForbidden());
			assertThat(boardMapper.findByIdx(1)).isPresent();
		}

		@Test
		@WithUserDetails("diamond")
		void 다른_사람의_문의글은_볼_수_없다() throws Exception {
			mvc.perform(get("/questions/1")).andExpect(status().isForbidden());
		}
	}

	@Nested
	class 검색 {

		@Test
		void 검색어는_SQL로_해석되지_않는다() {
			assertThat(boardMapper.count(BoardSearch.of(1, "title", "' OR '1'='1"))).isZero();
			assertThat(boardMapper.count(BoardSearch.of(1, "title", "첫 번째"))).isEqualTo(1);
		}

		@Test
		void 허용되지_않은_검색_조건은_제목_검색으로() {
			BoardSearch search = BoardSearch.of(1, "1=1 or title", "첫 번째");
			assertThat(boardMapper.count(search)).isEqualTo(1);
		}

		@Test
		void 작성자_검색() {
			assertThat(boardMapper.count(BoardSearch.of(1, "nickname", "다이아"))).isEqualTo(1);
		}
	}

	@Nested
	class 댓글 {

		@Test
		@WithUserDetails("leaf")
		void 작성자는_요청_본문이_아닌_로그인_정보로_정해진다() throws Exception {
			mvc.perform(post("/api/posts/1/replies").with(csrf()).contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"content":"안녕하세요","user_idx":3}"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lastPage").value(1));

			var replies = replyMapper.findPage(1, 0, 10);
			assertThat(replies).hasSize(1);
			assertThat(replies.get(0).getUserIdx()).isEqualTo(2);
			assertThat(replies.get(0).getParentReplyIdx()).isEqualTo(replies.get(0).getIdx());
			assertThat(boardMapper.findByIdx(1).orElseThrow().getReplyCount()).isEqualTo(1);
		}

		@Test
		@WithUserDetails("diamond")
		void 다른_사람의_댓글은_수정_삭제할_수_없다() throws Exception {
			var reply = new com.newsp.reply.Reply();
			reply.setBoardIdx(1);
			reply.setUserIdx(2);
			reply.setContent("leaf의 댓글");
			replyMapper.insert(reply);

			mvc.perform(delete("/api/replies/" + reply.getIdx()).with(csrf()))
				.andExpect(status().isForbidden());
			mvc.perform(post("/my/replies/delete").with(csrf()).param("each", String.valueOf(reply.getIdx())))
				.andExpect(status().isForbidden());

			assertThat(replyMapper.findByIdx(reply.getIdx())).isPresent();
		}
	}

	@Nested
	class 첨부파일 {

		private static final byte[] PNG = {(byte) 0x89, 'P', 'N', 'G', '\r', '\n', 0x1a, '\n'};

		@Test
		@WithUserDetails("leaf")
		void 이미지는_UUID_이름으로_저장된다() throws Exception {
			var file = new MockMultipartFile("files", "../../evil.png", "image/png", PNG);

			mvc.perform(multipart("/boards/plaza").file(file).with(csrf())
					.param("subject", "C").param("title", "사진").param("content", "본문"))
				.andExpect(status().is3xxRedirection());

			int boardIdx = boardMapper.findByUser(2).get(0).getIdx();
			var attachments = attachmentMapper.findByBoard(boardIdx);
			assertThat(attachments).hasSize(1);
			String stored = attachments.get(0).getFileName();
			assertThat(stored).matches("[0-9a-f-]{36}\\.png");
			assertThat(Files.exists(fileStorage.getRoot().resolve(stored))).isTrue();
			assertThat(boardMapper.findByIdx(boardIdx).orElseThrow().getSubject()).isEqualTo("사담");

			// 게시글을 지우면 파일도 지워진다
			mvc.perform(post("/posts/" + boardIdx + "/delete").with(csrf())).andExpect(status().is3xxRedirection());
			assertThat(Files.exists(fileStorage.getRoot().resolve(stored))).isFalse();
		}

		@Test
		@WithUserDetails("leaf")
		void 이미지가_아니면_거부() throws Exception {
			var file = new MockMultipartFile("files", "shell.jsp", "application/octet-stream", "<% %>".getBytes());

			mvc.perform(multipart("/boards/plaza").file(file).with(csrf()).param("title", "t").param("content", "c"))
				.andExpect(status().isBadRequest());

			assertThat(boardMapper.findByUser(2)).hasSize(1);
			try (var files = Files.list(fileStorage.getRoot())) {
				assertThat(files.map(Path::toString)).noneMatch(name -> name.endsWith(".jsp"));
			} catch (java.nio.file.NoSuchFileException ignored) {
				// 저장 디렉터리가 아직 없으면 저장된 파일도 없다
			}
		}
	}

	@Nested
	class 신고 {

		@Test
		@WithUserDetails("leaf")
		void 신고는_한_번만_가능하고_사유는_서버에서_정한다() throws Exception {
			mvc.perform(post("/posts/2/report").with(csrf()).param("category", "C").param("content", "조작된 사유"))
				.andExpect(redirectedUrl("/posts/2"));
			mvc.perform(post("/posts/2/report").with(csrf()).param("category", "C"))
				.andExpect(status().isBadRequest());

			assertThat(boardMapper.findByIdx(2).orElseThrow().getReportCount()).isEqualTo(1);
		}

		@Test
		@WithUserDetails("leaf")
		void 자기_글은_신고할_수_없다() throws Exception {
			mvc.perform(post("/posts/1/report").with(csrf()).param("category", "C"))
				.andExpect(status().isBadRequest());
		}
	}

	@Nested
	class 회원정보 {

		@Test
		@WithUserDetails("leaf")
		void 비밀번호_변경은_현재_비밀번호가_필요하다() throws Exception {
			mvc.perform(post("/profile/password").with(csrf())
					.param("currentPassword", "wrong").param("newPassword", "new123!"))
				.andExpect(redirectedUrl("/profile"));
			String before = userMapper.findById("leaf").orElseThrow().getPassword();

			mvc.perform(post("/profile/password").with(csrf())
					.param("currentPassword", "test1234!").param("newPassword", "new123!"))
				.andExpect(redirectedUrl("/profile"));

			String after = userMapper.findById("leaf").orElseThrow().getPassword();
			assertThat(after).isNotEqualTo(before).startsWith("{bcrypt}");
		}

		@Test
		@WithUserDetails("leaf")
		void 탈퇴는_본인_아이디를_입력해야_한다() throws Exception {
			mvc.perform(post("/quit").with(csrf()).param("confirmId", "other"))
				.andExpect(redirectedUrl("/"));
			assertThat(userMapper.findById("leaf")).isPresent();
		}
	}

	@Nested
	class 등업 {

		@Autowired
		com.newsp.admin.GradeService gradeService;

		@Test
		@WithUserDetails("admin")
		void 대기중인_요청이_있을_때만_승인된다() throws Exception {
			mvc.perform(post("/admin/users/2/upgrade").with(csrf())).andExpect(status().isBadRequest());
			assertThat(userMapper.findById("leaf").orElseThrow().getLevel()).isEqualTo(1);
		}

		@Test
		void 스케줄러는_조건을_만족하지_않는_회원을_추가하지_않는다() {
			gradeService.collectUpgradeCandidates();
			assertThat(gradeService.pending(com.newsp.admin.Grade.TYPE_UPGRADE)).isEmpty();
		}
	}

	@Nested
	class 아이디_비밀번호_찾기 {

		@Test
		void 아이디는_가입한_이메일로만_보낸다() throws Exception {
			mvc.perform(post("/findId").with(csrf()).param("email", "leaf@example.com"))
				.andExpect(redirectedUrl("/signIn"));
			verify(mailService).sendIdMail("leaf@example.com", "leaf");
		}

		@Test
		void 없는_이메일이나_미인증_회원도_같은_응답이고_메일은_보내지_않는다() throws Exception {
			mvc.perform(post("/findId").with(csrf()).param("email", "nobody@example.com"))
				.andExpect(redirectedUrl("/signIn"));
			mvc.perform(post("/findId").with(csrf()).param("email", "pending@example.com"))
				.andExpect(redirectedUrl("/signIn"));
			mvc.perform(post("/findPassword").with(csrf()).param("id", "leaf").param("email", "wrong@example.com"))
				.andExpect(redirectedUrl("/signIn"));

			verify(mailService, never()).sendIdMail(anyString(), anyString());
			verify(mailService, never()).sendPasswordResetMail(anyString(), anyString(), anyInt());
			assertThat(userMapper.findById("leaf").orElseThrow().getResetToken()).isNull();
		}

		@Test
		void 재설정_링크로_비밀번호를_바꾸고_링크는_한_번만_쓸_수_있다() throws Exception {
			mvc.perform(post("/findPassword").with(csrf()).param("id", "leaf").param("email", "LEAF@example.com"))
				.andExpect(redirectedUrl("/signIn"));

			ArgumentCaptor<String> token = ArgumentCaptor.forClass(String.class);
			verify(mailService).sendPasswordResetMail(eq("leaf@example.com"), token.capture(), eq(30));
			// DB에는 원문이 아닌 해시가 저장된다
			assertThat(userMapper.findById("leaf").orElseThrow().getResetToken())
				.isEqualTo(AccountRecoveryService.hash(token.getValue()))
				.isNotEqualTo(token.getValue());

			mvc.perform(get("/resetPassword").param("token", token.getValue()))
				.andExpect(model().attribute("valid", true));

			// 규칙에 맞지 않는 비밀번호는 거부되고 링크는 그대로 유효
			mvc.perform(post("/resetPassword").with(csrf()).param("token", token.getValue()).param("newPassword", "short"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("error"))
				.andExpect(model().attribute("valid", true));

			mvc.perform(post("/resetPassword").with(csrf()).param("token", token.getValue()).param("newPassword", "brandnew1!"))
				.andExpect(redirectedUrl("/signIn"));

			mvc.perform(formLogin("/login").userParameter("loginId").passwordParam("loginPw")
					.user("leaf").password("brandnew1!"))
				.andExpect(redirectedUrl("/"));

			mvc.perform(get("/resetPassword").param("token", token.getValue()))
				.andExpect(model().attribute("valid", false));
			mvc.perform(post("/resetPassword").with(csrf()).param("token", token.getValue()).param("newPassword", "again123!"))
				.andExpect(model().attribute("valid", false));
		}

		@Test
		void 만료된_링크는_쓸_수_없다() throws Exception {
			userMapper.updateResetToken(2, AccountRecoveryService.hash("expired-token"), LocalDateTime.now().minusMinutes(1));

			mvc.perform(get("/resetPassword").param("token", "expired-token"))
				.andExpect(model().attribute("valid", false));
			mvc.perform(post("/resetPassword").with(csrf()).param("token", "expired-token").param("newPassword", "brandnew1!"))
				.andExpect(model().attributeExists("error"));
		}
	}
}
