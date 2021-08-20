<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign In</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
*{
	font-family: 'Nanum Gothic', sans-serif;
}
.btn-dark{
	color:#ffffff;
	background-color:#12192c;
	border-color:#12192c;
}
.btn-dark:hover{
	color:#12192c;
	background-color:#ffffff;
	border: solid 1px #12192c;
}
</style>
<script>
$(function(){
	// user data 전송
	$("#submitLogin").on("click", function(){
		const id = $("#id").val();
		const pw = $("#pw").val();
		
		const data = {"id":id, "pw":pw};
		const JsonData = JSON.stringify(data);
		
		$.ajax({
			type: "post",
			url: "ajax/signIn/login",
			data: JsonData,
			dataType: 'json',
			contentType: "application/json;charset=UTF-8",
		    cache: false,
			success: function(message){
				if(message.result == "ok"){
					console.log("로그인 성공");
					window.location = 'main';
				}else if(message.result == "empty"){
					alert("존재하지 않거나 잘못된 정보입니다.");
					$("#id").val("");
					$("#pw").val("");
					$("#id").focus();
				}else if(message.result == "fail"){
					alert("인증되지 않은 계정입니다. 가입시 작성한 메일 계정을 확인해주세요.");
					$("#id").val("");
					$("#pw").val("");
					$("#id").focus();
				}
			},
			error: function(){
				console.log("로그인 실패");
			}
		})	// ajax end
	})

})
</script>
</head>
<body>
<article class="container">
	<div class="page-header text-center" style="width:100%; margin-top: 5%; margin-bottom:5%;">
		<img class="img-fluid" id="signin_img" src="<c:url value="/resources/image/signin_new.png" />" style="width:30%; height: 100px;"/>
	</div>
	<div class="col-sm-6 col-md-offset-3" style="margin-left:25%">
	    <form id="loginForm" method="post" action="ajax/signIn/login">
	    	<div class="form-group">
	            <label for="inputId">아이디</label>
	            <input type="text" class="form-control" id="id" placeholder="아이디" required>
	        </div>       
	        <div class="form-group">
	            <label for="inputPassword">비밀번호</label>
	            <input type="password" class="form-control" id="pw" placeholder="비밀번호" required>
	        </div>	        
	        <div class="form-group text-center">
	            <button type="button" id="submitLogin" class="btn btn-dark">로그인<i class="fa fa-check spaceLeft"></i></button>
	        </div>
	    </form>
	</div>
</article>


</body>
</html>