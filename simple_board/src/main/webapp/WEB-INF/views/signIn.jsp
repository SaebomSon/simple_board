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
	const url = document.location.href;
	const type = url.split("/");
	/* LINK ACTIVE */
	const linkColor = document.querySelectorAll('.nav__link')
	
	if(type[3] == 'signIn'){
		linkColor.forEach(l=> l.classList.remove('active'))
		linkColor[4].classList.add('active');
		}
	
	
	// user data 전송
	/*$("#submitLogin").on("click", function(){
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
		    beforeSend: function(xhr){
				var token = $("#token");
				xhr.setRequestHeader(token.data("token-name"), token.val());

			    },
			success: function(result){
				if(result.message == "ok"){
					console.log("로그인 성공");
					window.location = 'main';
				}else if(result.message == "admin"){
					console.log("관리자 계정");
					window.location = 'admin';
				}else if(result.message == "stop"){
					console.log("정지 회원");
					alert("정지된 회원입니다. 자세한 사항은 관리자에게 문의하세요.");
					$("#id").val("");
					$("#pw").val("");
					$("#id").focus();
				}else if(result.message == "empty"){
					alert("존재하지 않거나 잘못된 정보입니다.");
					$("#id").val("");
					$("#pw").val("");
					$("#id").focus();
				}else if(result.message == "fail"){
					alert("인증되지 않은 계정입니다. 가입시 작성한 메일 계정을 확인해주세요.");
					$("#id").val("");
					$("#pw").val("");
					$("#id").focus();
				}

				if(result.message == "error"){
					console.log("에러 메시지 : " + result.message)
					}
			},
			error: function(e){
				console.log(e);
				console.log("로그인 실패");
			}
		})	// ajax end
	})*/

}) // function end
</script>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<article class="container">
	<div class="page-header text-center" style="width:100%; margin-top: 5%; margin-bottom:5%;">
		<img class="img-fluid" id="signin_img" src="<c:url value="/resources/image/signin_new.png" />" style="width:30%; height: 100px;"/>
	</div>
	<div class="col-sm-6 col-md-offset-3" style="margin-left:25%">
	    <form id="loginForm" method="post" action="/login">
	    	<div class="form-group">
	            <label for="inputId">아이디</label>
	            <input type="text" class="form-control" id="id" name="loginId" placeholder="아이디" required>
	        </div>       
	        <div class="form-group">
	            <label for="inputPassword">비밀번호</label>
	            <input type="password" class="form-control" id="pw" name="loginPw" placeholder="비밀번호" required>
	        </div>	        
	        <div class="form-group text-center">
	            <button type="submit" id="submitLogin" class="btn btn-dark">로그인<i class="fa fa-check spaceLeft"></i></button>
	        </div>
	        <input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}">
	    </form>
	</div>
	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
    <font color="red">
        <p>${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
        <c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
    </font>
</c:if>

	<div class="col-sm-6 col-md-offset-3" style="margin-left:25%; color:gray; font-size: 13px;">
		<span>아직 회원이 아니신가요?</span>
		<span style="margin-left: 20px;"><a href="/signUp">회원가입</a></span>
		
	</div>
</article>


</body>
</html>