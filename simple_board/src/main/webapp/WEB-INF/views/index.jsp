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
</head>
<body id="body-pd">
<input type="hidden" id="status" value="${status }">
	<div class="l-navbar" id="navbar">
		<nav class="nav">
			<div>
                <div class="nav__brand">
                    <ion-icon name="menu-outline" class="nav__toggle" id="nav-toggle"></ion-icon>
                    <a class="nav__logo">Simple Board</a>
                </div>
                <div class="nav__list">
                    <a  class="nav__link active"  id="home">
                        <ion-icon name="home-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Home</span>
                    </a>
                    <a class="nav__link"  id="leaf">
                        <ion-icon name="leaf-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Leaf Board</span>
                    </a>
                    <a class="nav__link" id="flower">
                        <ion-icon name="flower-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Flower Board</span>
                    </a>
                    <a class="nav__link" id="diamond">
                        <ion-icon name="diamond-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Diamond Board</span>
                    </a>
	                <c:choose>
	                	<c:when test="${status eq null }">
			                <a href="${pageContext.request.contextPath }/signIn" class="nav__link">
			                    <ion-icon name="enter-outline" class="nav__icon"></ion-icon>
			                    <span class="nav_name">Log In</span>
			                </a>
	                		<a href="${pageContext.request.contextPath }/signUp" class="nav__link">
			                    <ion-icon name="create-outline" class="nav__icon"></ion-icon>
			                    <span class="nav_name">Sign Up</span>
	                		</a>
			            </c:when>
			            <c:otherwise>
			                <div class="nav__link" id="collapse">
			                        <ion-icon name="person-outline" class="nav__icon"></ion-icon>
			                        <span class="nav_name">${nickname }</span>
			                        <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
			                        <ul class="collapse__menu">
			                            <a href="#" class="collapse__sublink">Profile</a>
			                            <a href="#" class="collapse__sublink">blaasa</a>
			                            <a href="#" class="collapse__sublink">Members</a>
			                        </ul>
			                </div>
			                <a href="${pageContext.request.contextPath }" class="nav__link">
			                    <ion-icon name="log-out-outline" class="nav__icon"></ion-icon>
			                    <span class="nav_name">Log Out</span>
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
document.addEventListener('DOMContentLoaded', function(){

	/* MENU CLICK ALERT */
	const status = document.getElementById('status').value
	console.log(status)
	const home = document.getElementById('home')
	home.addEventListener('click', function(){
		if(status == ''){
			document.location.href = ''
		}else{
			document.location.href='main'
			}
	});
	const leaf = document.getElementById('leaf')
	leaf.addEventListener('click', function(){
		if($(this).hasClass('active')){
			$(".nav__list a.active").removeClass("active")
			$(this).addClass("active")
			}
		if(status == ''){
			alert("로그인 후에 이용할 수 있습니다.")
			document.location.href='signIn'
			}
		else{
			document.location.href='boardType?type=1'
			}
	
	});	// leaf end
	
	const flower = document.getElementById('flower')
	flower.addEventListener('click', function(){
		if(status == ''){
			alert("로그인 후에 이용할 수 있습니다.")
			document.location.href='signIn'
			}
		else{
			document.location.href='boardType?type=2'
			}
	});	// flower end
	
	const diamond = document.getElementById('diamond')
	diamond.addEventListener('click', function(){
		if(status == ''){
			alert("로그인 후에 이용할 수 있습니다.")
			document.location.href='signIn'
			}
		else{
			document.location.href='boardType?type=3'
			}
	});	// diamond end
	
});	//DOMContentLoaded end


</script>
<!-- JS -->
<script type="text/javascript" src="resources/js/main.js?ver=3"></script>
</html>