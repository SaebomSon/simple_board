<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Reply</title>
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
	/*float: right;*/
}
.nickname{
	display: inline-block;
	margin-right: 10px;
	font-size: 14px;
}
.reply_count {
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
	margin-left: 45px;
}
.reply-content{
	display: inline-block;
	margin-left : 45px;
	font-size: 14px;
}
.title{
	font-size: 13px;
	color: gray;
}
.nav__icon.board-icon{
	display: inline-block;
	font-size:11px;
}
.written-date{
	display: inline-block;
	/*float: right;*/
	font-size: 11px;
	color: gray;
	margin-right: 5px;
	
}
.checkSection{
	margin-bottom: 5px;
	padding: 6px 10px;
	display: flex;
  	justify-content: space-between;
}

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
	
	if(type[3] == 'myReply'){
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
	<h1 class="main-title">My Reply</h1>
	<form action="deleteMyReply" method="post">
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
					<c:forEach var="myList" items="${myList }">
						<li class="list-group-item">
							<div class="firstLine">
								<div class="list-title-wrapper">
									<div class="custom-control custom-checkbox">
										<input type="checkbox" name="each" id="checkbox[${myList.idx }]" class="custom-control-input" value="${myList.idx }">
										<label for="checkbox[${myList.idx }]" class="custom-control-label"></label>
										<div class="user-info">
											<img src="${myList.level_image }" width="15px;"><a class="nickname">${myList.nickname }</a>
										</div>
										<div class="written-date"><span>${myList.written_date }</span></div>
									</div>
								</div>
							</div>
							<div class="reply-content">
								<span>${myList.content }</span>
							</div>
							<div class="board-info">
								<div class="board-title">
									<input type="hidden" name="boardIdx" value="${myList.board_idx }" />
									<a class="title" onclick="openDetailPage(${myList.type }, ${myList.board_idx });">원문 제목 : ${myList.title }</a>
								</div>
								<div class="reply_count"><ion-icon name="chatbubbles-outline"></ion-icon>&nbsp;${myList.reply_count }</div>
								
								
							</div>
						</li>
					</c:forEach>
				</ul>
			</div><!-- main-block end -->
		</div><!-- col end -->
	</form>
</div>
<script>
function openDetailPage(type, idx){
	// idx : board_idx
	document.location.href = 'detail?type=' + type + "&idx=" + idx;
}

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