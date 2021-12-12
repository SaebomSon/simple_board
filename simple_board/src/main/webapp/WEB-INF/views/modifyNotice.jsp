<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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
</style>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<form action="modifyNoticeDone" method="post" onsubmit="alertMessage();">
	<div class="container">
		<h2 style="margin-bottom: 100px;" class="board-title">Notice</h2>
		<input type="hidden" name="idx" value="${info.idx }">
		<table class="table table-borderless">
			<tr>
				<td style="padding-bottom: 2px;">
					<div class="input-group">
					<select name="type" class="custom-select col-sm-3" style="width:auto;">
					    <option value="A" <c:if test="${info.type eq 'A' }">selected</c:if>>전체 공지</option>
				    	<option value="L" <c:if test="${info.type eq 'L'}">selected</c:if>>Leaf</option>
				    	<option value="F" <c:if test="${info.type eq 'F'}">selected</c:if>>Flower</option>
				    	<option value="D" <c:if test="${info.type eq 'D'}">selected</c:if>>Diamond</option>
		 			</select>
		  			<input type="text" class="form-control" id="title" name="title" style="width:80%;" required="required" value="${info.title }"></div>
				</td>
			</tr>
			<tr>
				<td style="padding-top: 2px;">
					<div class="form-group">
			  			<textarea class="form-control" rows="10" id="content" name="content" required="required">${info.content }</textarea>
					</div>
				</td>
			</tr>
		</table>
		<div style="text-align: center;">
			<input type="submit" class="btn btn-dark" id="modify" value="수정">
			<input type="button" class="btn btn-light" onclick="location.href='noditeDetail?idx=${info.idx }'" value="취소">
		</div>
	</div>
</form>
<script>
function alertMessage(){
	const msg = alert("공지를 수정했습니다.");
	document.location.href = "modifyNoticeDone";
}
</script>
</body>
</html>