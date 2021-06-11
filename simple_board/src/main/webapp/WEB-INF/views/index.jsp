<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- CSS -->
<link rel="stylesheet" href="resources/css/sidebar.css">
</head>
<body id="body-pd">
    <div class="l-navbar" id="navbar">
        <nav class="nav">
            <div>
                <div class="nav__brand">
                    <ion-icon name="menu-outline" class="nav__toggle" id="nav-toggle"></ion-icon>
                    <a href="#" class="nav__logo">Simple Board</a>
                </div>
                <div class="nav__list">
                    <a href="#" class="nav__link active">
                        <ion-icon name="home-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Home</span>
                    </a>
                    <a href="#" class="nav__link">
                        <ion-icon name="leaf-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Leaf Board</span>
                    </a>
                    <a href="#" class="nav__link">
                        <ion-icon name="flower-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Flower Board</span>
                    </a>
                    <a href="#" class="nav__link">
                        <ion-icon name="diamond-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">Diamond Board</span>
                    </a>
	                <div href="#" class="nav__link" id="collapse">
	                        <ion-icon name="person-outline" class="nav__icon"></ion-icon>
	                        <span class="nav_name">My Page</span>
	                        <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
	                        <ul class="collapse__menu">
	                            <a href="#" class="collapse__sublink">Information</a>
	                            <a href="#" class="collapse__sublink">blaasa</a>
	                            <a href="#" class="collapse__sublink">Members</a>
	                        </ul>
	                </div>
	                <a href="#" class="nav__link">
	                    <ion-icon name="log-out-outline" class="nav__icon"></ion-icon>
	                    <span class="nav_name">Log out</span>
	                </a>
                </div>
            </div>
        </nav>
    </div>

    <h1>게시판</h1>
    
</body>
<!-- JS -->
<script src="resources/js/main.js"></script>
</html>