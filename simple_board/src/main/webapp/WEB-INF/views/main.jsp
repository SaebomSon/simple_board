<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&family=Titillium+Web:ital,wght@1,600&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');

.main-title{
	font-family: 'Rampart One', cursive;
}
.title{
	font-family: 'Titillium Web', sans-serif;
}
*{
	font-family: 'Nanum Gothic', sans-serif;
}
a{
	text-decoration: none;
	color: black;
	cursor: pointer;
}
.panel{
	margin-bottom: 3rem;
	border: 1px solid transparent;
	
}
.main-block{
	margin-bottom: 3rem;
}
.list-group-item{
	position: relative;
	border: 1px solid #ddd;
	padding: 6px 10px;
}
.user-info{
	display: inline-block;
	float: right;
}
.nickname{
	display: inline-block;
	margin-right: 10px;
	font-size: 14px;
}
.hits{
	display: inline-block;
	font-size: 13px;
	color: gray;
	margin-right: 5px;
}
.board-info{
	font-size: 12px;
	color: gray;
	
}
.board-title{
	display: inline-block;
}
.nav__icon.board-icon{
	display: inline-block;
	font-size:11px;
}
.written-date{
	display: inline-block;
	float: right;
	font-size: 11px;
	color: gray;
	margin-right: 5px;
	
}
</style>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<h1 class="main-title">SIMPLE BOARD</h1>
<div class="container-fluid">
	<div class="row" style="margin-top: 3rem;">
		<div class="col-sm-6 panel">
			<h4><span class="title">Newest Contents</span></h4>
			<div class="main-block">
				<ul class="list-group">
					<c:forEach var="newestList" items="${newestList }">
						<li class="list-group-item">
							<div class="list-title-wrapper">
								<a onclick="loginAndLevelCheck(${newestList.type }, ${newestList.idx });">${newestList.title }</a>
								<div class="user-info">
									<a class="nickname">${newestList.nickname }</a>
									<div class="hits"><ion-icon name="eye-outline"></ion-icon>&nbsp;${newestList.hits }</div>
								</div>
							</div>
							<div class="board-info">
								<div class="board-title">
									<c:if test="${newestList.type eq 1}" >
										<ion-icon name="leaf-outline" class="nav__icon board-icon"></ion-icon><span>leaf board</span>
									</c:if>
									<c:if test="${newestList.type eq 2}" >
										<ion-icon name="flower-outline" class="nav__icon board-icon"></ion-icon></ion-icon><span>flower board</span>
									</c:if>
									<c:if test="${newestList.type eq 4}" >
										<ion-icon name="diamond-outline" class="nav__icon board-icon"></ion-icon></ion-icon><span>diamond board</span>
									</c:if>
								</div>
								<div class="written-date"><span>${newestList.written_date }</span></div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div><!-- main-block end -->
		</div><!-- col end -->
		<div class="col-sm-6 panel">
			<h4><span class="title">Hits Top</span></h4>
			<div class="main-block">
				<ul class="list-group">
					<c:forEach var="hitsList" items="${hitsList }">
						<li class="list-group-item">
							<div class="list-title-wrapper">
								<a onclick="loginAndLevelCheck(${hitsList.type }, ${hitsList.idx });"><span>${hitsList.title }</span></a>
								<div class="user-info">
									<a class="nickname">${hitsList.nickname }</a>
									<div class="hits"><ion-icon name="eye-outline"></ion-icon>&nbsp;${hitsList.hits }</div>
								</div>
							</div>
							<div class="board-info">
								<div class="board-title">
									<c:if test="${hitsList.type eq 1}" >
										<ion-icon name="leaf-outline" class="nav__icon board-icon"></ion-icon><span>leaf board</span>
									</c:if>
									<c:if test="${hitsList.type eq 2}" >
										<ion-icon name="flower-outline" class="nav__icon board-icon"></ion-icon></ion-icon><span>flower board</span>
									</c:if>
									<c:if test="${hitsList.type eq 4}" >
										<ion-icon name="diamond-outline" class="nav__icon board-icon"></ion-icon></ion-icon><span>diamond board</span>
									</c:if>
								</div>
								<div class="written-date"><span>${hitsList.written_date }</span></div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div><!-- main-block end -->
			<h4><span class="title">Reply Top</span></h4>
			<div class="main-block">
				<ul class="list-group">
					<c:forEach var="replyList" items="${replyList }">
						<li class="list-group-item">
							<div class="list-title-wrapper">
								<a onclick="loginAndLevelCheck(${replyList.type }, ${replyList.idx });">${replyList.title }</a>
								<div class="user-info">
									<a class="nickname">${replyList.nickname }</a>
									<div class="hits"><ion-icon name="chatbubbles-outline"></ion-icon>&nbsp;${replyList.reply_count }</div>
								</div>
							</div>
							<div class="board-info">
								<div class="board-title">
									<c:if test="${replyList.type eq 1}" >
										<ion-icon name="leaf-outline" class="nav__icon board-icon"></ion-icon><span>leaf board</span>
									</c:if>
									<c:if test="${replyList.type eq 2}" >
										<ion-icon name="flower-outline" class="nav__icon board-icon"></ion-icon></ion-icon><span>flower board</span>
									</c:if>
									<c:if test="${replyList.type eq 4}" >
										<ion-icon name="diamond-outline" class="nav__icon board-icon"></ion-icon></ion-icon><span>diamond board</span>
									</c:if>
								</div>
								<div class="written-date"><span>${replyList.written_date }</span></div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div><!-- main-block end -->
		</div><!-- col end -->
	</div><!-- row end -->
</div><!-- container end -->
</body>

<script>
function loginAndLevelCheck(type, idx){
	const status = "${status}";
	var level = "${level}";
	var boardType = type;
	if(status == ""){
		alert("로그인 후에 이용할 수 있습니다.");
		document.location.href='signIn';
	}else{
		console.log("type >> " + type);
		if(level >= type){
			document.location.href = 'detail?type=' + type + "&idx=" + idx;
			}
		else if(level < type){
			alert("게시판에 접근할 수 없는 등급입니다.");
			}
	}
}
</script>

</html>