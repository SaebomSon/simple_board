<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- IONICONS -->
<script type="module" src="https://unpkg.com/ionicons@5.5.1/dist/ionicons/ionicons.esm.js"></script>
<script nomodule="" src="https://unpkg.com/ionicons@5.5.1/dist/ionicons/ionicons.js"></script>
<!-- CSS -->
<link rel="stylesheet" href="resources/css/sidebar.css">
</head>
<body id="body-pd">
	<div class="l-navbar" id="navbar">
		<nav class="nav">
			<div>
                <div class="nav__brand">
                    <ion-icon name="menu-outline" class="nav__toggle" id="nav-toggle"></ion-icon>
                    <a href="${pageContext.request.contextPath }/main" class="nav__logo">Simple Board</a>
                </div>
                
                <div class="nav__list">
                    <a href="${pageContext.request.contextPath }/main" class="nav__link active">
                        <ion-icon name="home-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Home</span>
                    </a>
                    <a href="${pageContext.request.contextPath }/leaf" class="nav__link">
                        <ion-icon name="leaf-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Leaf Board</span>
                    </a>
                    <a href="${pageContext.request.contextPath }/flower" class="nav__link">
                        <ion-icon name="flower-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Flower Board</span>
                    </a>
                    <a href="${pageContext.request.contextPath }/diamond" class="nav__link">
                        <ion-icon name="diamond-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Diamond Board</span>
                    </a>
	                <c:choose>
	                	<c:when test="${status eq null }">
	                		<a href="${pageContext.request.contextPath }/signUp" class="nav__link">
			                    <ion-icon name="create-outline" class="nav__icon"></ion-icon>
			                    <span class="nav_name">Sign Up</span>
	                		</a>
			                <a href="${pageContext.request.contextPath }/signIn" class="nav__link">
			                    <ion-icon name="enter-outline" class="nav__icon"></ion-icon>
			                    <span class="nav_name">Log In</span>
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
			                <a href="#" class="nav__link">
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
<!-- JS -->
<script src="resources/js/main.js"></script>
</html>