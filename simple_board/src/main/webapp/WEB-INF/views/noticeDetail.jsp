<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<title>Notice</title>
<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&family=Titillium+Web:ital,wght@1,600&display=swap');
*{
	font-family: 'Nanum Gothic', sans-serif;
}
.board-title{
	font-family: 'Titillium Web', sans-serif;
	margin-bottom: 5rem;
}
.mo-del-btn{
	display: inline-block;
	width: 100%;
	text-align: end;
	margin-top:10px;
	margin-bottom:50px;
}
</style>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<div class="container">
	<h2 class="board-title" style="margin-bottom: 80px;">Notice</h2>
	<div class="mo-del-btn">
		<input type="button" class="btn btn-dark" value="수정" onclick="location.href='modifyNotice?idx=${info.idx}'" style="display:inline-block; margin-right: 5px;">
		<input type="button" class="btn btn-light" value="삭제" onclick="deleteNotice(${info.idx})" style="display:inline-block;">
	</div>
	<div class="title_section">
       	<span style="text-align: left; cursor:pointer;">
       		<c:if test="${info.type eq 'A' }">[전체 공지]</c:if>
	    	<c:if test="${info.type eq 'L'}">[Leaf]</c:if>
	    	<c:if test="${info.type eq 'F'}">[Flower]</c:if>
	    	<c:if test="${info.type eq 'D'}">[Diamond]</c:if>
       	${info.title }</span>
	</div>
	<hr>
	<div class="user_section">
		<span style="font-size: 15px;"><img src="${info.level_image}" style="width:15px;">${info.nickname }</span>&nbsp;&nbsp;
		<c:if test="${info.written_date eq info.modify_date }">
			<span style="font-size: 12px; color: gray;">${info.written_date }</span>&nbsp;&nbsp;
		</c:if>
		<c:if test="${info.written_date ne info.modify_date }">
			<span style="font-size: 12px; color: gray;">${info.modify_date }</span>&nbsp;&nbsp;
		</c:if>
	</div>
	<hr>
	<div class="detail_section">
		${info.content }<br>
	</div>
	<hr>
</div>
<script>
	function deleteNotice(idx){
		const chk = confirm("공지를 삭제하시겠습니까?");
		if(chk){
			alert("공지를 삭제했습니다.");
			document.location.href="deleteNotice?idx=" + idx;
			}
		}
</script>
</body>
</html>