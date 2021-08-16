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
a{
	text-decoration: none;
	color: black;
	cursor: pointer;
}
.panel{
	margin-bottom: 20px;
	border: 1px solid transparent;
}
.list-group-item{
	position: relative;
	border: 1px solid #ddd;
	padding: 6px 10px;
}
.user-info{
	display: inline-block;
	float:right;
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
</style>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<h1>SIMPLE BOARD</h1>
<div class="container-fluid">
	<div class="row" style="margin-top: 3rem;">
		<div class="col-sm-6 panel">
			<h4><span>BEST HITS</span></h4>
			<div class="main-block">
				<ul class="list-group">
					<c:forEach var="hitsList" items="${hitsList }">
						<li class="list-group-item">
							<div class="list-title-wrapper">
								<a onclick="loginAndLevelCheck(${hitsList.type }, ${hitsList.idx });"><span>${hitsList.title }</span></a>
								<div class="user-info">
									<a class="nickname">${hitsList.nickname }</a>
									<div class="hits"><ion-icon name="glasses-outline"></ion-icon>&nbsp;${hitsList.hits }</div>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div><!-- main-block end -->
		</div><!-- col end -->
		<div class="col-sm-6">
			<h4><span>LEAF NEWEST</span></h4>
			<div class="main-block">
				<ul class="list-group">
					<c:forEach var="typeOne" items="${typeOneList }">
						<li class="list-group-item">
							<div class="list-title-wrapper">
								<a  onclick="loginAndLevelCheck(${typeOne.type }, ${typeOne.idx });">${typeOne.title }</a>
								<div class="user-info">
									<a class="nickname">${typeOne.nickname }</a>
									<div class="hits"><ion-icon name="glasses-outline"></ion-icon>&nbsp;${typeOne.hits }</div>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div><!-- main-block end -->
		</div><!-- col end -->
	</div><!-- row end -->
	<div class="row" style="margin-top: 3rem;">
		<div class="col-sm-6">
			<h4><span>FLOWER NEWEST</span></h4>
			<div class="main-block">
				<ul class="list-group">
					<c:forEach var="typeTwo" items="${typeTwoList }">
						<li class="list-group-item">
							<div class="list-title-wrapper">
								<a onclick="loginAndLevelCheck(${typeTwo.type }, ${typeTwo.idx });">${typeTwo.title }</a>
								<div class="user-info">
									<a class="nickname">${typeTwo.nickname }</a>
									<div class="hits"><ion-icon name="glasses-outline"></ion-icon>&nbsp;${typeTwo.hits }</div>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div><!-- main-block end -->
		</div><!-- col end -->
		<div class="col-sm-6">
			<h4><span>DIAMOND NEWEST</span></h4>
			<div class="main-block">
				<ul class="list-group">
					<c:forEach var="typeThree" items="${typeThreeList }">
						<li class="list-group-item">
							<div class="list-title-wrapper">
								<a onclick="loginAndLevelCheck(${typeThree.type }, ${typeThree.idx });">${typeThree.title }</a>
								<div class="user-info">
									<a class="nickname">${typeThree.nickname }</a>
									<div class="hits"><ion-icon name="glasses-outline"></ion-icon>&nbsp;${typeThree.hits }</div>
								</div>
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