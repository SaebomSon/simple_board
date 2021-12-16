<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Question</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&family=Titillium+Web:ital,wght@1,600&display=swap');

.main-title{
	font-family: 'Titillium Web', sans-serif;
	margin-bottom: 5rem;
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
.hits, .reply_count {
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
	margin-left: 28px;
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
.content{
	display: inline-block;
	margin-left : 28px;
	font-size: 14px;
	color: gray;
}
.checkSection{
	margin-bottom: 5px;
	padding: 6px 10px;
	display: flex;
  	justify-content: space-between;
}
/*
#allCheckBtn{
	margin-right: 15px;
	margin-top: 11px;
}
#deleteCheck{
	float: left;
    margin-top: 5px;
    margin-right: 15px;
}*/
input[type="checkbox"]:checked + label:after{
	background-color: #12192c;
	border-radius: 4px;
}
</style>
<script>
$(function(){
	const url = document.location.href;
	const type = url.split("/");
	/* LINK ACTIVE */
	const linkColor = document.querySelectorAll('.nav__link')
	
	if(type[3] == 'myBoard'){
		linkColor.forEach(l=> l.classList.remove('active'))
		linkColor[4].classList.add('active');
		}
});
</script>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<div class="container">
	<h1 class="main-title">My Question</h1>
	<form action="deleteMyQuestion" method="post">
	<div class="col-sm-12 panel">
		<div class="checkSection">
			<div class="custom-control custom-checkbox allCheck">
				<input type="checkbox" id="allCheckBtn" class="custom-control-input"/>
				<label for="allCheckBtn" class="custom-control-label" style="font-size: 13px;"><span>전체 선택</span></label>
			</div>
			<div class="allDelete">
				<button type="submit" class="btn btn-dark" style="height: 30px; font-size: 13px;">삭제</button>
			</div>
		</div>
		<div class="main-block">
			<ul class="list-group">
				<c:forEach var="myQuestionList" items="${myQuestionList }">
					<li class="list-group-item">
						<div class="firstLine">
							<div class="list-title-wrapper">
								<div class="custom-control custom-checkbox">
									<input type="checkbox" name="each" id="checkbox[${myQuestionList.idx }]" class="custom-control-input" value="${myQuestionList.idx }">
									<label for="checkbox[${myQuestionList.idx }]" class="custom-control-label"></label>
									<a onclick="openDetailPage(${myQuestionList.idx });">${myQuestionList.title }</a>
									<c:if test="${myQuestionList.status eq 1 }">
										<img src="https://img.shields.io/badge/-%EC%99%84%EB%A3%8C-ff80aa" style="margin-left: 5px; width:30px; height: 20px;">
									</c:if>
									<div class="user-info">
										<img src="${myQuestionList.level_image }" width="15px;"><a class="nickname">${myQuestionList.nickname }</a>
									</div>
								</div>
							</div>
						</div>
						<div class="content"><span>${myQuestionList.content }</span></div>
						<div class="written-date"><span>${myQuestionList.written_date }</span></div>
							
					</li>
				</c:forEach>
			</ul>
		</div><!-- main-block end -->
	</div><!-- col end -->
	</form>
</div>
<script>
function openDetailPage(idx){

	document.location.href = 'questionDetail?&idx=' + idx;
}

// 체크박스 전체 선택 or 전체 해제
/*
$(".allCheck").on("click", "#allCheckBtn", function(){
	var checked = $(this).is(":checked");
	if(checked){
		$(".custom-checkbox").find("input").prop("checked", true);
	}else{
		$(".custom-checkbox").find("input").prop("checked", false);
	}
});*/

// 전체선택 or 전체 해제
$(".allCheck").on("click", "#allCheckBtn", function () {
    $(".custom-checkbox").find('input').prop("checked", $(this).is(":checked"));
});

// 체크박스 개별 선택
$(".custom-checkbox").on("click", ".custom-control-input", function() {
    var checkboxLength = $("input:checkbox[name='each']").length;
    var checkedLength = $("input:checkbox[name='each']:checked").length;

	// 만약 개별선택 == 전체선택일시 전체선택에 check
    if(checkboxLength == checkedLength){
    	$("#allCheckBtn").prop("checked", true);
    }else{
    	$("#allCheckBtn").prop("checked", false);
        }
});
</script>
</body>
</html>