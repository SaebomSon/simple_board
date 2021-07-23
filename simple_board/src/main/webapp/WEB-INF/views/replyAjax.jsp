<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>

</script>
</head>
<body>
<b>댓글&nbsp;&nbsp;&nbsp; <span class="reply_count">${info.reply_count }</span></b>

<div class="replyList">
	<c:forEach var="reply" items="${replyInfo }">
			<input type="hidden" id="replyIdx[${reply.idx }]" value="${reply.idx }"/>
			<table style="width:100%; margin: 1rem 0;">
				<tbody class="parent_reply_section">
					<tr>
						<td style="width:8rem;"><img id="level_image" src="resources/image/${reply.level_image }"> ${reply.nickname }</td>
						<td style="width:20rem; color: gray; font-size: 13px;">
							<c:choose>
								<c:when test="${reply.written_date eq reply.modify_date}">
									${reply.written_date }
								</c:when>
								<c:otherwise>
									${reply.modify_date }
								</c:otherwise>
							</c:choose>
						</td>
						<td></td>
						<c:choose>
							<c:when test="${userIdx eq reply.user_idx }">
								<td class="modi_del" id="modi_del[${reply.idx }]" rowspan="2" style="text-align: right; vertical-align: top;">
									<a class="replyBtns" onclick="modifyBtn(${reply.idx });"><span>수정</span></a>&nbsp;&nbsp;&nbsp;
									<a class="replyBtns" onclick="deleteReply(${reply.idx})"><span>삭제</span></a>
								</td>
								<td class="modi_can" id="modi_can[${reply.idx }]" style="text-align: right; display: none;">
									<a class="replyBtns" onclick="modifyReply(${reply.idx})"><span>수정</span></a>&nbsp;&nbsp;&nbsp;
									<a class="replyBtns" onclick="modifyCancelBtn(${reply.idx });"><span>취소</span></a>
								</td>
							</c:when>
							<c:otherwise><td colspan="2"></td></c:otherwise>
						</c:choose>
					</tr>
					<tr style="height: 2rem;"></tr>
					<tr class="original_reply" id="original_reply[${reply.idx }]">
						<td colspan="3">${reply.content }</td>
						<td style="text-align: right;">
							<a class="replyBtns" id="re_reply"><span>답글</span></a></td>
					</tr>
					<tr class="modify_reply" id="modify_reply[${reply.idx }]" style="display: none;">
						<td colspan="4">
							<textarea class="reply_area" rows="2" cols="60" id="reply_modify" style="width:100%;">${reply.content }</textarea>
						</td>
					</tr>
				</tbody>
				<!-- 대댓글 -->
				<tfoot class="added_reply_section">
					<tr><td>대댓글</td></tr>
				</tfoot>
			</table>
			<hr>
		</c:forEach>
</div>
</body>
</html>