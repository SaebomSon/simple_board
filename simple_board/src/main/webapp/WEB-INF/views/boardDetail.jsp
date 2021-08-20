<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Simple_board</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
$(function(){
	var type = ${type};
	/* LINK ACTIVE */
	const linkColor = document.querySelectorAll('.nav__link')
	console.log(linkColor[2].classList);

	if(type == 1){
		linkColor.forEach(l=> l.classList.remove('active'))
		linkColor[1].classList.add('active');
		}
	else if(type == 2){
		linkColor.forEach(l=> l.classList.remove('active'))
		linkColor[2].classList.add('active');
		}
	else if(type == 4){
		linkColor.forEach(l=> l.classList.remove('active'))
		linkColor[3].classList.add('active');
		}
	
});
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
*{
	font-family: 'Nanum Gothic', sans-serif;
}
</style>
<script>
function deleteBoard(idx, type){
	if(confirm("게시글을 삭제하시겠습니까?")){
		document.location.href='delete?type=' + type + '&idx=' + idx + '&user=' + ${info.user_idx };
		}
}

</script>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<div class="container">
	<h2 style="margin-bottom: 80px;">
			<c:if test="${type eq 1 }">
					<ion-icon name="leaf-outline"></ion-icon>Leaf Board					
			</c:if>
			<c:if test="${type eq 2 }">
				<ion-icon name="flower-outline"></ion-icon>Flower Board
			</c:if>
			<c:if test="${type eq 4 }">
				<ion-icon name="diamond-outline"></ion-icon>Diamond Board
			</c:if>
	</h2>
	<div style="margin-top:10px; margin-bottom:50px;">
		<c:if test="${page ne null }">
			<input type="button" class="btn btn-dark" value="목록" onclick="location.href='boardType?type=${type}&page=${page }'">
		</c:if>
		<c:if test="${page eq null }">
			<input type="button" class="btn btn-dark" value="목록" onclick="location.href='boardType?type=${type}&page=1'">
		</c:if>
		<input type="hidden" name="idx" value=${idx } />
		<input type="hidden" name="type" value=${type } />
		<!-- 내 user_idx== board의 user_idx가 같을 경우 -->
		<c:if test="${userIdx eq info.user_idx }">
			<input type="button" class="btn btn-light" value="삭제" onclick="deleteBoard(${idx }, ${type })" style="float:right;">
			<input type="button" class="btn btn-dark" value="수정" onclick="location.href='modifyPage?type=${type}&page=${page }&idx=${idx}'" style="float:right; margin-right: 5px;">
		</c:if>
	</div>
	<div class="title_section">
		<c:choose>
	        <c:when test="${info.subject eq null }">
	        	<td style="text-align: left; cursor:pointer;">${info.title }</td>
	        </c:when>
	        <c:otherwise>
	        	<td style="text-align: left; cursor:pointer;">[${info.subject }] ${info.title }</td>
	        </c:otherwise>
		</c:choose>
	</div>
	<hr>
	<div class="user_section">
		${info.nickname }&nbsp;&nbsp; | &nbsp;&nbsp;
		<ion-icon name="calendar-outline"></ion-icon>&nbsp;&nbsp;${info.written_date }&nbsp;&nbsp; | &nbsp;&nbsp;
		<ion-icon name="eye-outline"></ion-icon>&nbsp;&nbsp;${info.hits }
		<div style="float:right;">
			<ion-icon name="chatbubbles-outline"></ion-icon>&nbsp;&nbsp;
			<span class="reply_count">${info.reply_count }</span>
		</div>
	</div>
	<hr>
	<div class="detail_section">
		${info.content }<br>
		<c:if test="${images ne null }">
			<c:forEach items="${fn: split(images, '&') }" var="file_name">
				<img src="resources/image/${file_name }" style="width:60%; margin-bottom: 5px;">
			</c:forEach>
		</c:if>
	</div>
	<hr>
	<div class="reply_section">
		<jsp:include page="reply.jsp"></jsp:include>
	</div>
	
</div>

</body>
</html>