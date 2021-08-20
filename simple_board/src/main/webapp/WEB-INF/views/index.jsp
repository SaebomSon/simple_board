<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<!-- IONICONS -->
<script type="module" src="https://unpkg.com/ionicons@5.5.1/dist/ionicons/ionicons.esm.js"></script>
<script nomodule="" src="https://unpkg.com/ionicons@5.5.1/dist/ionicons/ionicons.js"></script>
<!-- CSS -->
<link rel="stylesheet" href="resources/css/sidebar.css">
<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
*{
	font-family: 'Nanum Gothic', sans-serif;
}
</style>
</head>
<body id="body-pd">
<input type="hidden" id="status" value="${status }">
<input type="hidden" id="level" value="${level }" >
	<div class="l-navbar" id="navbar">
		<nav class="nav">
			<div>
                <div class="nav__brand">
                    <ion-icon name="menu-outline" class="nav__toggle" id="nav-toggle"></ion-icon>
                    <a class="nav__logo" onclick="loginAlert(0);">Simple Board</a>
                </div>
                <div class="nav__list">
					<a class="nav__link active" id="0" onclick="loginAlert(0);">
						<ion-icon name="home-outline" class="nav__icon"></ion-icon>
						<span class="nav_name">Home</span>
					</a>
					<a class="nav__link" id="1" onclick="loginAlert(1);">
						<ion-icon name="leaf-outline" class="nav__icon"></ion-icon>
						<span class="nav_name">Leaf Board</span>
					</a>
					<a class="nav__link" id="2" onclick="loginAlert(2);">
						<ion-icon name="flower-outline" class="nav__icon"></ion-icon>
						<span class="nav_name">Flower Board</span>
					</a>
					<a class="nav__link" id="4" onclick="loginAlert(4);">
						<ion-icon name="diamond-outline" class="nav__icon"></ion-icon>
						<span class="nav_name">Diamond Board</span>
					</a>
                    <c:choose>
               			<c:when test="${status eq 'success' }">
               				<div class="nav__link" id="collapse">
			                        <ion-icon name="person-outline" class="nav__icon"></ion-icon>
			                        <span class="nav_name">${nickname }</span>
			                        <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
			                        <ul class="collapse__menu">
			                            <a href="#" class="collapse__sublink">Profile</a>
			                            <a href="#" class="collapse__sublink">MyPage</a>
			                            <a href="#" class="collapse__sublink">Members</a>
			                        </ul>
			                </div>
			                <a href="${pageContext.request.contextPath }/" class="nav__link">
			                    <ion-icon name="log-out-outline" class="nav__icon"></ion-icon>
			                    <span class="nav_name">Log Out</span>
			                </a>
			            </c:when>
			            <c:otherwise>
			                <a href="${pageContext.request.contextPath }/signIn" class="nav__link">
			                    <ion-icon name="enter-outline" class="nav__icon"></ion-icon>
			                    <span class="nav_name">Log In</span>
			                </a>
		               		<a href="${pageContext.request.contextPath }/signUp" class="nav__link">
			                    <ion-icon name="create-outline" class="nav__icon"></ion-icon>
			                    <span class="nav_name">Sign Up</span>
	               			</a>
			            </c:otherwise>
	                </c:choose>
                </div>
            </div>
        </nav>
    </div>
</body>

<script>
/* MENU CLICK ALERT */
const status = document.getElementById('status').value
const level = document.getElementById('level').value
function loginAlert(type){
	// 비로그인
	if(status == ''){
		// home or simple_board click
		if(type == 0){
			document.location.href='/'
		}
		// 게시판 click
		else{
			alert("로그인 후에 이용할 수 있습니다.")
			document.location.href='signIn'
			}
	}
	// 로그인
	else{
		// home or simple_board click
		if(type == 0){
			document.location.href='main';
		}
		// 게시판 click
		else{
			if(level < type){
				alert("게시판에 접근할 수 없는 등급입니다.");
				}
			else{
				document.location.href='boardType?type=' + type + "&page=1";
				}
			}
		}
}

</script>
<!-- JS -->
<script type="text/javascript" src="resources/js/main.js"></script>
</html>