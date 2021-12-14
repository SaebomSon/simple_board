<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Answer Ajax</title>
<script>
</script>
</head>
<body>
<div class="inner_text_view">
	<c:if test="${answer ne null }">
		<strong>답변</strong>
		<ul class="list_comment">
			<li class="admin_answer">
				<div class="admin_answer_section">
					<div class="admin_info">
						<div class="admin_post">
							<div class="admin_user_info">
								<span style="font-size: 15px;"><img class="ico_role" width="15px;" src="${answer.level_image }">${answer.nickname }</span>&nbsp;&nbsp;
								<span class="txt_date" style="font-size: 12px; color: gray;">${answer.answered_date }</span>&nbsp;&nbsp;
							</div>
							<hr>
							<div class="box_post">
								<p class="answer_content_info">
									<span class="original_comment" tabindex="0">${answer.content }</span>
								</p>
							</div>
							<c:if test="${userIdx eq answer.user_idx }">
								<div class="admin_more_my_option" id="my_option[${answer.idx }]">
									<a class="menu_item" id="modify_answer" onclick="modifyBtn(${answer.idx});"><span class="modify_text" id="modifyText[${answer.idx }]">수정</span></a>
									<a class="menu_item" id="delete_answer" onclick="deleteReply(${answer.idx });"><span>삭제</span></a>
								</div>
							</c:if>
						</div><!-- admin_post end -->
					</div><!-- admin_info -->
				</div>
			</li>
		</ul>
	</c:if>
</div><!-- inner_text_view end -->
</body>
</html>