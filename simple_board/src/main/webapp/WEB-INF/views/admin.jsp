<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자페이지</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
$(function(){
	var type = ${type};
	/* LINK ACTIVE */
	const linkColor = document.querySelectorAll('.nav__link')
	console.log(linkColor[2].classList);

	if(type == 1){
		linkColor.forEach(l=> l.classList.remove('active'))
		linkColor[1].classList.add('active');
		}
	else if(type == 2){
		linkColor.forEach(l=> l.classList.remove('active'))
		linkColor[2].classList.add('active');
		}
	else if(type == 4){
		linkColor.forEach(l=> l.classList.remove('active'))
		linkColor[3].classList.add('active');
		}
	
});
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&family=Titillium+Web:ital,wght@1,600&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
body{
	background-color: #deebff;
}
.main-title{
	font-family: 'Rampart One', cursive;
}
.title{
	display: inline-block;
	font-family: 'Titillium Web', sans-serif;
}
.panel{
	margin-bottom: 3rem;
	border: 1px solid transparent;
}
.title-area{
	margin-bottom: 0.5rem;
}
.title-header{
	display: inline-block;
}
.write-btn{
	display: inline-block;
	float: right;
	width:45px;
	margin-top: 4px;
	font-size: 12px;
	padding: 3px;
}
.main-block{
	margin-bottom: 3rem;
	font-size: 13px;
}
.list-group-item{
	position: relative;
	border: 1px solid #ddd;
	padding: 6px 10px;
}
.title-wrapper, .notice-subject, .notice-title,
.report-subject, .report-title, .question-title, .question-nickname,
.grade-nickname, .grade-date {
	display: inline-block;
}
.title-wrapper{
	width: 100%;
	margin-top: 3px;
}
.delete-btn, .answer-btn, .approval-btn{
	display: inline-block;
	float: right;
	width:35px;
	font-size: 10px;
	padding: 3px;
}
.grade-nickname{
	width: 50%;
}
.grade-date{
	width: 35%;
	text-align: right;
}
.notice
</style>
</head>
<body>
<jsp:include page="index.jsp" flush="false"></jsp:include>
<h1 class="main-title">SIMPLE BOARD :: ADMIN</h1>
<div class="container-fluid">
	<div style="margin-top: 3rem;">
		<div class="row">
			<div class="col-sm-4 panel">
				<div class="title-area">
					<div class="title-header"><h4><span class="title">Notice</span></h4></div>
					<button class="write-btn btn btn-dark">작성</button>
				</div>
				<div class="main-block">
					<ul class="list-group">
						<li class="list-group-item">
							<div class="title-wrapper">
								<div class="notice-subject">[전체공지]</div>
								<div class="notice-title">제목</div>
								<button class="delete-btn btn btn-danger">삭제</button>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="col-sm-4 panel">
				<div class="title-area">
					<div class="title-header"><h4><span class="title">Report</span></h4></div>
				</div>
				<div class="main-block">
					<ul class="list-group">
						<li class="list-group-item">
							<div class="title-wrapper">
								<div class="report-subject">[게시판]</div>
								<div class="report-title">제목</div>
								<button class="delete-btn btn btn-danger">삭제</button>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="col-sm-4 panel">
				<div class="title-area">
					<div class="title-header"><h4><span class="title">Question</span></h4></div>
				</div>
				<div class="main-block">
					<ul class="list-group">
						<li class="list-group-item">
							<div class="title-wrapper">
								<div class="question-title">제목</div>
								<div class="question-nickname">닉네임</div>
								<button class="answer-btn btn btn-success">답변</button>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="col-sm-12 panel">
				<h4><span class="title">Grade</span></h4>
				<div class="main-block">
					<div class="row">
						<div class="col-sm-4">
							<ul class="list-group">
								<li class="list-group-item">
									<img src="https://img.shields.io/badge/-%EB%93%B1%EC%97%85-red" style="width:35px; margin-bottom: 4px;">
									<div class="title-wrapper">
										<div class="grade-nickname">user nickname</div>
										<div class="grade-date">2021-11-29</div>
										<button class="approval-btn btn btn-primary">승인</button>
									</div>
								</li>
							</ul>
						</div>
						<div class="col-sm-4">
							<ul class="list-group">
								<li class="list-group-item">
									<img src="https://img.shields.io/badge/-%EA%B0%95%EB%93%B1-orange" style="width:35px; margin-bottom: 4px;">
									<div class="title-wrapper">
										<div class="grade-nickname">user nickname</div>
										<div class="grade-date">2021-11-29</div>
										<button class="approval-btn btn btn-primary">승인</button>
									</div>
								</li>
							</ul>
						</div>
						<div class="col-sm-4">
							<ul class="list-group">
								<li class="list-group-item">
									<img src="https://img.shields.io/badge/-%EC%A0%95%EC%A7%80-lightgrey" style="width:35px; margin-bottom: 4px;">
									<div class="title-wrapper">
										<div class="grade-nickname">user nickname</div>
										<div class="grade-date">2021-11-29</div>
										<button class="approval-btn btn btn-primary">승인</button>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div><!-- row -->
	</div>
	
</div>

</body>
</html>