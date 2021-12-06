<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Question</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
$(function(){
	const url = document.location.href;
	const type = url.split("/");
	/* LINK ACTIVE */
	const linkColor = document.querySelectorAll('.nav__link')
	
	if(type[3] == 'question'){
		linkColor.forEach(l=> l.classList.remove('active'))
		linkColor[5].classList.add('active');
		}
	
});
</script>
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
<form action="insertQuestion" onsubmit="check()" method="post" enctype="multipart/form-data">
	<div class="container">
		<h2 class="board-title" style="margin-bottom: 100px;">Question</h2>
		<input type="hidden" id="idx" name="user_idx" value="${user_idx }">
		<table class="table table-borderless">
			<tr>
				<td style="padding-bottom: 2px;">
					<div class="input-group">
					<select name="subject" class="custom-select col-sm-6" style="width:auto;">
					    <option value="NONE" selected>말머리 없음</option>
				    	<option value="B">게시글 문의</option>
				    	<option value="L">등급 문의</option>
				    	<option value="R">신고글 문의</option>
				    	<option value="G">기타</option>
		 			</select>
		  			<input type="text" class="form-control" id="title" name="title" style="width:80%;" required="required"></div>
				</td>
			</tr>
			<tr>
				<td style="padding-top: 2px;">
					<div class="form-group">
			  			<textarea class="form-control" rows="10" id="content" name="content" required="required"></textarea>
					</div>
				</td>
			</tr>
		</table>
		<div style="text-align: center;">
			<input type="submit" class="btn btn-dark" id="question" value="입력">
			<input type="button" class="btn btn-light" onclick="location.href='/main'" value="취소">
		</div>
	</div>
</form>
<script>
function check(){
	const chk = confirm("문의글을 작성하시겠습니까?");
	if(!chk){
		alert("문의글 작성이 취소 되었습니다.");
		document.location.href = "main";
	}
}
</script>
</body>
</html>