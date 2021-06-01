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
</head>
<body style="margin-right:15%; margin-left: 15%;">
<article class="container">
	<div class="page-header text-center" style="width:100%; margin-top: 5%; margin-bottom:5%;">
		<img class="img-fluid" id="signin_img" src="<c:url value="/resources/image/signin.png" />" style="width:30%; height: 100px;"/>
	</div>
	<div class="col-sm-6 col-md-offset-3" style="margin-left:25%">
	    <form role="form" method="post" action="${pageContext.request.contextPath }/successLogin">
	    	<div class="form-group">
	            <label for="inputId">아이디</label>
	            <input type="text" class="form-control" id="inputId" placeholder="아이디" required>
	            <label for="inputId" id="idCheck"></label>
	        </div>       
	        <div class="form-group">
	            <label for="inputPassword">비밀번호</label>
	            <input type="password" class="form-control" id="inputPassword" placeholder="비밀번호" required>
	        </div>	        
	        <div class="form-group text-center">
	            <button id="join-submit" class="btn btn-dark">로그인<i class="fa fa-check spaceLeft"></i></button>
	        </div>
	    </form>
	</div>
</article>


</body>
</html>