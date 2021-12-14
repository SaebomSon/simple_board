<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<title>Question</title>
<script>
$(function(){
	getAnswerList();
});

function showAnswerSection(){
	const answerSection = document.getElementById("insertAnswer");
	answerSection.style.display = 'block';
}

function insertAnswer(qidx){
	const answerSection = document.getElementsByClassName("answer-section");
	const questionIdx = qidx;
	const content = document.getElementById('textbox').value;
	const data = {"questionIdx" : questionIdx, "content" : content};

	if (content == '') {
		alert("답변을 입력하세요.");
		document.getElementById('textbox').focus();
	}else {
		$.ajax({
			url : "ajax/insertAnswer",
			type : "post",
			data : JSON.stringify(data),
			dataType : "json",
			contentType : "application/json;charsest=UTF-8",
			success : function(result) {
				if (result.result == "insertAnswerOk") {
					getAnswerList();
					//document.getElementById('textbox').value = "";
					$(".answer-section").css('display', 'none');
					console.log("답변 작성 성공");
				}
			},
			error : function(e) {
				console.log("댓글 작성 실패");
			}
		})
	}

}
//answer 불러오는 ajax
function getAnswerList(idx) {
	var answerListSection = document.getElementsByClassName("inner_text_view");
	var formData = new FormData();

	$.ajax({
		url : "getAnswer?idx=${info.idx}",
		type : "get",
		data : formData,
		dataType : "text",
		contentType : false,
		processData : false,
		success : function(result) {
			var html = $('<div>').html(result);
			var answerContent = html.find("div.inner_text_view").html();

			$(".inner_text_view").html(answerContent);
		},
		error : function(e) {
			console.log("fail getting answer list");
		}
	})
}
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&family=Titillium+Web:ital,wght@1,600&display=swap');
*{
	font-family: 'Nanum Gothic', sans-serif;
}
.board-title{
	font-family: 'Titillium Web', sans-serif;
	margin-bottom: 5rem;
}
.mo-del-btn{
	display: inline-block;
	width: 100%;
	text-align: end;
	margin-top:10px;
	margin-bottom:50px;
}

/*답변 작성 영역*/
.answer-section {
	width: 100%;
    margin-top: 10px;
    display: inline-block;
}
.answer-btn{
	float: left;
}
.inner_text_write {
    border-color: #efefef;
    background-color: #fff;
}
.text_write_g .inner_text_write {
    border: 1px solid #e4e4e4;
    padding: 12px 10px 10px 20px;
    background-color: #fff;
    border-radius: 2px;
    margin-left: 50px;
}
.text_write_g .box_textarea textarea {
    width: 100%;
    height: 81px;
    margin: -5px -5px 0;
    padding: 5px 5px 0;
    border: 0 none;
    font-size: 13px;
    background-color: transparent;
    resize: none;
    vertical-align: top;
}
/*댓글 작성 버튼*/
.text_write_g .wrap_menu {
    margin-top: 3px;
}
.text_write_g .wrap_menu:after {
    display: block;
    clear: both;
    content: "";
}
.area_r {
    float: right;
}
.text_write_g .wrap_menu .area_r {
    font-size: 0;
}
.text_write_g .wrap_menu .btn_group {
    display: inline-block;
    margin-left: 15px;
}
.full_type1 {
	width: 50px;
	height: 30px;
    background-color: #ff5656;
    color: #fff;
    border: 0 none;
    border-radius: 4px;
}
.list_comment>li {
    position: relative;
}
.admin_answer_section {
    display: table;
    width: 100%;
    position: relative;
    padding: 19px 0 16px;
    table-layout: fixed;
    box-sizing: border-box;
}
.admin_user_info {
    margin-top: -4px;
    margin-bottom: 2px;
}
.admin_more_my_option {
    position: absolute;
    top: 18px;
    right: 0;
}
.admin_more_my_option .menu_item {
    color: #666;
    float: left;
    margin-right: 5px;
    vertical-align: middle;
    font-size: 13px;
}
.menu_item .modify_text {
	margin-right: 10px;
}
</style>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<div class="container">
	<h2 class="board-title" style="margin-bottom: 80px;">Question</h2>
	<!-- 
	<div class="mo-del-btn">
		<c:if test="${userIdx eq 1 }">
			<input type="button" class="btn btn-dark" value="수정" onclick="location.href='modifyNotice?idx=${info.idx}'" style="display:inline-block; margin-right: 5px;">
			<input type="button" class="btn btn-light" value="삭제" onclick="deleteNotice(${info.idx})" style="display:inline-block;">
		</c:if>
	</div> -->
	<div class="title_section">
       	<span style="text-align: left; cursor:pointer;">
       		<c:if test="${info.subject eq 'NONE' }"></c:if>
       		<c:if test="${info.subject eq 'B' }">[게시글 문의]</c:if>
       		<c:if test="${info.subject eq 'L' }">[등급 문의]</c:if>
	    	<c:if test="${info.subject eq 'R' }">[신고글 문의]</c:if>
	    	<c:if test="${info.subject eq 'G' }">[기타]</c:if>
       	${info.title }</span>
	</div>
	<hr>
	<div class="user_info_section">
		<span style="font-size: 15px;"><img src="${info.level_image}" style="width:15px;">${info.nickname }</span>&nbsp;&nbsp;
		<span style="font-size: 12px; color: gray;">${info.written_date }</span>&nbsp;&nbsp;
	</div>
	<hr>
	<div class="detail_section">
		${info.content }<br>
	</div>
	<hr>
	<div class="text_write_g answer-section">
		<c:if test="${userIdx eq 1 and answer eq null}">
			<div class="answer-btn">
				<span style="width: 30%;">
				<a onclick="showAnswerSection();"><ion-icon size="large" name="return-down-forward-outline"></ion-icon></a></span>
			</div>
		</c:if>
		<div class="inner_text_write" id="insertAnswer" style="display:none;">
			<div class="box_textarea">
				<textarea placeholder="답변을 작성해주세요." id="textbox" maxlength="3000" style="height: 100px;"></textarea>
			</div>
			<div class="wrap_menu">
				<div class="area_r">
					<div class="btn_group">
						<button class="btn_g full_type1 confirm_button" onclick="insertAnswer(${info.idx})" style="font-size: 13px;">등록</button>
					</div>
				</div>
			</div>
		</div><!-- inner_text_write end -->
	</div>
	<div class="inner_text_view">
		<c:if test="${answer ne null }">
			<strong>답변</strong>
			<ul class="list_comment">
				<li class="admin_answer">
					<div class="admin_answer_section">
						<div class="admin_info">
							<div class="admin_post">
								<div class="admin_user_info">
									<span style="font-size: 15px;"><img class="ico_role" width="15px;" src="${answer.level_image }">${answer.nickname }</span>&nbsp;&nbsp;
									<span class="txt_date" style="font-size: 12px; color: gray;">${answer.answered_date }</span>&nbsp;&nbsp;
								</div>
								<hr>
								<div class="box_post">
									<p class="answer_content_info">
										<span class="original_comment" tabindex="0">${answer.content }</span>
									</p>
								</div>
								<c:if test="${userIdx eq answer.user_idx }">
									<div class="admin_more_my_option" id="my_option[${answer.idx }]">
										<a class="menu_item" id="modify_answer" onclick="modifyBtn(${answer.idx});"><span class="modify_text" id="modifyText[${answer.idx }]">수정</span></a>
										<a class="menu_item" id="delete_answer" onclick="deleteReply(${answer.idx });"><span>삭제</span></a>
									</div>
								</c:if>
							</div><!-- admin_post end -->
						</div><!-- admin_info -->
					</div>
				</li>
			</ul>
		</c:if>
	</div><!-- inner_text_view end -->
</div><!-- container end -->
<script>


</script>
</body>
</html>