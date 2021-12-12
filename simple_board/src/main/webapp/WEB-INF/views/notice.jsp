<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Notice</title>
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
<jsp:include page="index.jsp" flush="false"></jsp:include>
<form action="insertNotice" method="post" id="insertNotice">
	<div class="container">
		<h2 class="board-title" style="margin-bottom: 100px;">Notice</h2>
		<table class="table table-borderless">
			<tr>
				<td style="padding-bottom: 2px;">
					<div class="input-group">
					<select name="type" class="custom-select col-sm-3" style="width:auto;">
					    <option value="A" selected>전체공지</option>
				    	<option value="L">Leaf</option>
				    	<option value="F">Flower</option>
				    	<option value="D">Diamond</option>
		 			</select>
		  			<input type="text" class="form-control" id="title" name="title" style="width:70%;" required="required"></div>
				</td>
			</tr>
			<tr>
				<td style="padding-top: 2px;">
					<div class="form-group">
			  			<textarea class="form-control" rows="15" id="content" name="content" required="required"></textarea>
					</div>
				</td>
			</tr>
		</table>
		<div class="btn_group" style="text-align: center;">
			<input class="btn btn-dark" type="submit" value="작성">
			<button class="btn btn-light" onclick="history.back();">
				<span class="inner_btn">취소</span>
			</button>
		</div>
	</div>
</form>

</body>
</html>