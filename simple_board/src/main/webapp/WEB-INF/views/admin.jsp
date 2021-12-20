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
<style>
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&family=Titillium+Web:ital,wght@1,600&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');

body{
	background-color: #f5f9ff;
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
.list-group{
	background-color: #fff;
	border: 1px solid #ddd;
	padding: 6px 10px;
}
.list-group-item{
	position: relative;
	border: 0px solid #ddd;
	padding: 3px 3px;
}
.list-group-header{
	position: relative;
}
.title-wrapper, #notice-subject, #notice-title,
#report-index, #report-board-title, #reported-nickname, 
#question-index, #question-title, #question-nickname,
#grade-nickname, #grade-date, #upgrade-level,
.delete-btn, .answer-btn, .approval-btn {
	display: inline-block;
}
.title-wrapper{
	width: 90%;
	margin-top: 3px;
}
.delete-btn, .answer-btn, .approval-btn, .report-btn{
	display: inline-block;
	float: right;
	width:35px;
	font-size: 10px;
	padding: 3px;
}
#notice-title{
	width: 70%;
}
#report-board-title, #question-title{
	width: 60%;
}
#grade-nickname{
	width: 40%;
}
#grade-date{
	width: 30%;
	text-align: right;
	float: right;
	color: gray;
	margin-right: 3px;
	font-size: 10px;
}
.a-title{
	text-decoration : none;
	cursor: pointer;
	color: black;
}

/*모달*/
#modal .modal-overlay{
	width: 70%;
    height: 100%;
    left: 50%;
    top: 50%;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    backdrop-filter: blur(1.5px);
    background: #fff;
    border-radius: 10px;
}
#modal .modal-window{
	background: #fff;
	backdrop-filter: blur( 13.5px );
	width: 520px;
	height: 450px;
	position: fixed;
	top: 13%;
	left: 30%;
	padding: 10px;
	border: 1px solid rgba(0,0,0,.2);
    border-radius: 10px;
}
#modal .modal-header{
	display: initial;
	border-bottom : 0px;
}
#modal .close-area {
	display: inline;
	float: right;
	padding-right: 10px;
	cursor: pointer;
	text-shadow: 1px 1px 2px gray;
}
.title_report{
	padding: 15px 10px;
	text-align: center;
}
.modal-content {
    position: relative;
    width: 100%;
    height: 300px;
    pointer-events: auto;
    padding: 4px 10px;
    border: 0px;
    background-color: #fff;
    background-clip: padding-box;
    outline: 0;
}
.empty-message{
	text-align: center;
	font-size: 12px;
	color: gray;
}
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
					<button class="write-btn btn btn-dark" id="noticeBtn" onclick="location.href='notice'">작성</button>
				</div>
				<div class="main-block">
					<ul class="list-group">
						<c:if test="${empty notice }">
							<span class="empty-message">공지가 존재하지 않습니다.</span>
						</c:if>
						<c:forEach var="notice" items="${notice }">
							<li class="list-group-item">
								<div class="title-wrapper">
									<div id="notice-subject">
										<c:if test="${notice.type eq 0 }">[전체 공지]</c:if>
								    	<c:if test="${notice.type eq 1}">[Leaf]</c:if>
								    	<c:if test="${notice.type eq 2}">[Flower]</c:if>
								    	<c:if test="${notice.type eq 4}">[Diamond]</c:if>
									</div>
									<div id="notice-title"><a class="a-title" href="noticeDetail?idx=${notice.idx }">${notice.title }</a></div>
								</div>
								<button class="delete-btn btn btn-danger" onclick="deleteNotice(${notice.idx})">삭제</button>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="col-sm-4 panel">
				<div class="title-area">
					<div class="title-header"><h4><span class="title">Report</span></h4></div>
				</div>
				<div class="main-block">
					<ul class="list-group">
						<c:if test="${empty reportedBoard }">
							<span class="empty-message">신고글이 존재하지 않습니다.</span>
						</c:if>
						<c:forEach var="report" items="${reportedBoard }" varStatus="i">
							<input type="hidden" id="boardIdx" value=${report.idx } >
							<li class="list-group-item">
								<div class="title-wrapper">
									<div id="report-index" style="width:5%; text-align: center;">${i.index + 1 }</div>
									<div id="report-board-title"><a class="a-title" onclick="location.href='detail?type=${report.type }&idx=${report.idx }'">
										<c:if test="${report.type eq 1 }">[Leaf]</c:if>
										<c:if test="${report.type eq 2 }">[Flower]</c:if>
										<c:if test="${report.type eq 4 }">[Diamond]</c:if>
										${report.title }</a></div>
									<div id="reported-nickname">${report.nickname }</div>
								</div>
								<button id="report-view-btn[${report.idx }]" class="report-btn btn btn-warning" onclick="openReportModal(${report.idx })">보기</button>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="col-sm-4 panel">
				<div class="title-area">
					<div class="title-header"><h4><span class="title">Question</span></h4></div>
				</div>
				<div class="main-block">
					<ul class="list-group">
						<c:if test="${empty question }">
							<span class="empty-message">문의글이 존재하지 않습니다.</span>
						</c:if>
						<c:forEach var="question" items="${question }" varStatus="i">
							<li class="list-group-item">
								<div class="title-wrapper">
									<div id="question-index" style="width:5%; text-align: center;">${i.index + 1 }</div>
									<div id="question-title"><a class="a-title" onclick="location.href='questionDetail?idx=${question.idx}'">${question.title }</a></div>
									<div id="question-nickname" style="width: 30%; text-align: center;">${question.nickname }</div>
								</div>
								<button class="answer-btn btn btn-success" onclick="updateQuestionStatus(${question.idx})">완료</button>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="col-sm-12 panel">
				<h4><span class="title">Grade</span></h4>
				<div class="main-block">
					<div class="row">
						<div class="col-sm-4">
							<ul class="list-group">
								<li class="list-group-header">
									<img src="https://img.shields.io/badge/-%EB%93%B1%EC%97%85-red" style="width:35px; margin-bottom: 4px;">
								</li>
								<c:if test="${empty gradeTypeOne }">
									<span class="empty-message">등업 대상이 존재하지 않습니다.</span>
								</c:if>
								<c:forEach var="gradeOne" items="${gradeTypeOne }">
									<li class="list-group-item">
										<div class="title-wrapper">
											<div id="grade-nickname"><a class="a-title" href="">${gradeOne.nickname }</a></div>
											<div id="upgrade-level">
												<c:if test="${gradeOne.update_level eq 2 }">
													<ion-icon name="arrow-up-circle-outline"></ion-icon>준회원2</c:if>
												<c:if test="${gradeOne.update_level eq 3 }">
													<ion-icon name="arrow-up-circle-outline"></ion-icon>정회원</c:if>
												<c:if test="${gradeOne.update_level eq 4 }">
													<ion-icon name="arrow-up-circle-outline"></ion-icon>우수회원</c:if>
												<c:if test="${gradeOne.update_level eq 5 }">
													<ion-icon name="arrow-up-circle-outline"></ion-icon>특별회원</c:if>
											</div>
											<div id="grade-date">${gradeOne.written_date }</div>
										</div>
										<button class="approval-btn btn btn-primary" onclick="location.href='upgradeLevel?idx=${gradeOne.user_idx}'">승인</button>
									</li>
								</c:forEach>
							</ul>
						</div>
						<div class="col-sm-4">
							<ul class="list-group">
								<li class="list-group-header">
									<img src="https://img.shields.io/badge/-%EA%B0%95%EB%93%B1-orange" style="width:35px; margin-bottom: 4px;">
								</li>
								<c:if test="${empty gradeTypeTwo }">
									<span class="empty-message">강등 대상이 존재하지 않습니다.</span>
								</c:if>
								<c:forEach var="gradeTwo" items="${gradeTypeTwo }">
									<li class="list-group-item">
										<div class="title-wrapper">
											<div id="grade-nickname"><a class="a-title" href="">${gradeTwo.nickname }</a></div>
											<div id="grade-date">${gradeTwo.written_date }</div>
										</div>
										<button class="approval-btn btn btn-primary">승인</button>
									</li>
								</c:forEach>
							</ul>
						</div>
						<div class="col-sm-4">
							<ul class="list-group">
								<li class="list-group-header">
									<img src="https://img.shields.io/badge/-%EC%A0%95%EC%A7%80-lightgrey" style="width:35px; margin-bottom: 4px;">
								</li>
								<c:if test="${empty gradeTypeThree }">
									<span class="empty-message">정지 대상이 존재하지 않습니다.</span>
								</c:if>
								<c:forEach var="gradeThree" items="${gradeTypeThree }">
									<li class="list-group-item">
										<div class="title-wrapper">
											<div id="grade-nickname"><a class="a-title" href="">${gradeThree.nickname }</a></div>
											<div id="grade-date">${gradeThree.written_date }</div>
										</div>
										<button class="approval-btn btn btn-primary">승인</button>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div><!-- row -->
				</div><!-- main-block : Grade -->
			</div><!-- col-sm-12 panel : Grade -->
		</div>
	</div>
	
</div><!-- container-fluid -->
<!-- 신고사유 보기 modal -->
<div id="modal" class="modal-overlay" style="display:none;">
	<div class="modal-window">
		<div class="close-area"><ion-icon name='close-outline'></ion-icon></div>
		<div class="modal-header">
			<h2 class="title_report"><strong>게시글 신고 사유</strong></h2>
		</div>
		<div class="modal-content">
			<table class="table table-hover" style="text-align: center;">
			    <thead>
			      <tr>
			        <th>신고 사유</th>
			        <th>신고 건수</th>
			      </tr>
			    </thead>
			    <tbody class="detail"></tbody>
			</table>
		</div>
	</div>
</div>
<script>
function deleteNotice(idx){
	const isNoticeChk = confirm("공지를 삭제하시겠습니까?");
	if(isNoticeChk){
		alert("공지를 삭제했습니다.");
		document.location.href="deleteNotice?idx=" + idx;
		}
	}

function openReportModal(bidx){
	const modal = document.getElementById("modal");
	const btn = document.getElementById("report-view-btn["+bidx+"]");
	var html = '';
	btn.addEventListener('click', () => {
		modal.style.display = 'block';
		$.ajax({
			url : "ajax/getReason",
			type : "post",
			data : JSON.stringify(bidx),
			dataType : "json",
			contentType : "application/json;charsest=UTF-8",
			async : true,
			success : function(detail) {
					var details = detail.reportDetail;
					for(var i=0; i<details.length; i++){
						html = "<tr><td>";
						if(details[i].category == 'G'){
							html += "기타 : &nbsp;&nbsp;" + details[i].content + "</td>"
						}else{
							html += details[i].content + "</td>"
								}
						html += "<td>" + details[i].count + "</td></tr>";
						$(".detail").append(html);					
					}
			},
			error : function(e) {
				console.log("댓글 작성 실패");
			}
		})
			
	});
}
const closeBtn = modal.querySelector(".close-area");
closeBtn.addEventListener("click", e =>{
	modal.style.display = 'none';
	$(".detail").empty();
	
	setTimeout(function(){
		location.reload();
		},50); // 3000밀리초 = 3초
	});

function updateQuestionStatus(idx){
	const isQuestionChk = confirm("답변을 완료하시겠습니까?");
	if(isQuestionChk){
		document.location.href="updateQuestionStatus?idx=" + idx;
		}
	}

</script>
</body>
</html>