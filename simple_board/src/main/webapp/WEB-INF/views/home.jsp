<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<style>
	.btn-outline-info{
		color:#12192c;
		border: solid 1px #12192c;
	}
	.btn-outline-info:hover{
		color:#ffffff;
		background-color:#12192c;
		border-color:#12192c;
	}
	.btn-outline-info:active{
		color:#ffffff;
		background-color:#12192c;
		border-color:#12192c;
	}
</style>

<body style="margin-right:15%; margin-left: 15%;">
<div class="logo_image text-center" style="width:100%; margin-top: 10%; margin-bottom: 5%;">
	<img class="img-fluid" id="logo" src="<c:url value="/resources/image/logo_new.png" />" style="width:50%; height: 400px;"/>
</div>
<div class="d-grid gap-2 col-6 mx-auto">
	<button class="btn btn-outline-info btn-block" onclick="location='signUp'">회원가입</button>
	<button class="btn btn-outline-info btn-block" onclick="location='signIn'">로그인</button>
</div>
</body>
</html>
