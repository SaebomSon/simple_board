<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Simple_board</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&family=Titillium+Web:ital,wght@1,600&display=swap');
.title{
	font-family: 'Titillium Web', sans-serif;
}
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
*{
	font-family: 'Nanum Gothic', sans-serif;
}

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
	height: 600px;
	position: fixed;
	top: 13%;
	left: 30%;
	padding: 10px;
	border: 1px solid rgba(0,0,0,.2);
    border-radius: 10px;
}
#modal .modal-header{
	border-bottom : 0px;
}
#modal .close-area {
	display: inline;
	float: right;
	padding-right: 10px;
	cursor: pointer;
	text-shadow: 1px 1px 2px gray;
}
.modal-content {
    position: relative;
    width: 100%;
    height: 500px;
    pointer-events: auto;
    padding: 4px 10px;
    border: 0px;
    background-color: #fff;
    background-clip: padding-box;
    outline: 0;
}
.title_report {
    display: block;
    padding: 0px 22px 10px;
    font-weight: 600;
    font-size: 16px;
    line-height: 19px;
}
ul.report_list{
	padding: 10px 18px;
}
.choice_wrap{
	margin: 3px 0px;
}
.lab_radio{
	margin-left: 2px;
}
.tf_etc{
	width: 100%;
	font-size: 0.9em;
}
.reason_check{
	position: relative;
    font-size: 15px;
    vertical-align: middle;
}
input[type=radio]{
	content:'';
	display: inline-block;
	appearance: none;
	background-color: #ffffff;
	width: 16px;
	height: 16px;
	margin: 3px;
	border: 1px solid rgb(216, 216, 216);
	border-radius:50%;
	vertical-align:middle;
	cursor:pointer;
}
input[type=radio]:checked{
	appearance: none;
	background: url('https://lostinyou4.github.io/leesm/study/images/radio_check.png') 0 0 no-repeat;
	background-size: contain;
	border:1px #fff;
}
.btn_group {
    overflow: hidden;
    padding: 15px;
    text-align: center;
}
.modal-content .btn_confirm .inner_btn {
    border-color: #353a59;
    background-color: #353a59;
    color: #fff;
}
.btn_report{
	width:100%;
	color:#ffffff;
	background-color:#12192c;
	border-color:#12192c;
}
.btn_report .inner_btn {
    display: block;
    height: 30px;
    margin: 0 5px;
    background-color: #12192c;
    font-size: 15px;
    line-height: 30px;
    color: #ffffff;
    text-align: center;
}
</style>
<script>
$(function(){
	var type = ${type};
	/* LINK ACTIVE */
	const linkColor = document.querySelectorAll('.nav__link')

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
function deleteBoard(idx, type){
	if(confirm("게시글을 삭제하시겠습니까?")){
		document.location.href='delete?type=' + type + '&idx=' + idx + '&user=' + ${info.user_idx };
		}
}


</script>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<div class="container">
	<h2 class="title" style="margin-bottom: 80px;">
			<c:if test="${type eq 1 }">
					<ion-icon name="leaf-outline"></ion-icon>Leaf Board					
			</c:if>
			<c:if test="${type eq 2 }">
				<ion-icon name="flower-outline"></ion-icon>Flower Board
			</c:if>
			<c:if test="${type eq 4 }">
				<ion-icon name="diamond-outline"></ion-icon>Diamond Board
			</c:if>
	</h2>
	<div style="margin-top:10px; margin-bottom:50px;">
		<c:if test="${page ne null }">
			<input type="button" class="btn btn-dark" value="목록" onclick="location.href='boardType?type=${type}&page=${page }'">
		</c:if>
		<c:if test="${page eq null }">
			<input type="button" class="btn btn-dark" value="목록" onclick="location.href='boardType?type=${type}&page=1'">
		</c:if>
		<input type="hidden" name="idx" value=${idx } />
		<input type="hidden" name="type" value=${type } />
		<!-- 내 user_idx== board의 user_idx가 같을 경우 -->
		<c:if test="${userIdx eq info.user_idx }">
			<input type="button" class="btn btn-light" value="삭제" onclick="deleteBoard(${idx }, ${type })" style="float:right;">
			<input type="button" class="btn btn-dark" value="수정" onclick="location.href='modifyPage?type=${type}&page=${page }&idx=${idx}'" style="float:right; margin-right: 5px;">
		</c:if>
	</div>
	<div class="title_section">
		<c:choose>
	        <c:when test="${info.subject eq null }">
	        	<td style="text-align: left; cursor:pointer;">${info.title }</td>
	        </c:when>
	        <c:otherwise>
	        	<td style="text-align: left; cursor:pointer;">[${info.subject }] ${info.title }</td>
	        </c:otherwise>
		</c:choose>
		<input type="hidden" id="reportMessage" value="${reportMessage }">
		<div style="float:right;">
			<span id="bookmarkBtn"><ion-icon name="bookmark-outline"></ion-icon>&nbsp;&nbsp;</span>
			<span id="reportBtn"><a onclick="openReportModal();"><ion-icon name="ellipsis-vertical-outline"></ion-icon></a></span>
		</div>
	</div>
	<hr>
	<div class="user_section">
		<span style="font-size: 15px;"><img src="${info.level_image}" style="width:15px;">${info.nickname }</span>&nbsp;&nbsp;
		<span style="font-size: 12px; color: gray;">${info.written_date }</span>&nbsp;&nbsp;
		<span style="font-size: 12px; color: gray;"><ion-icon name="eye-outline"></ion-icon>${info.hits }</span>&nbsp;&nbsp;
		<span style="font-size: 12px; color: gray;"><ion-icon name="chatbubbles-outline"></ion-icon><span class="reply_count">${info.reply_count }</span></span>&nbsp;&nbsp;
		<span style="font-size: 12px; color: gray;"><ion-icon name="sad-outline"></ion-icon>${info.report_count }</span>
	</div>
	<hr>
	<div class="detail_section">
		${info.content }<br>
		<c:if test="${images ne null }">
			<c:forEach items="${fn: split(images, '&') }" var="file_name">
				<img src="resources/image/${file_name }" style="width:60%; margin-bottom: 5px;">
			</c:forEach>
		</c:if>
	</div>
	<hr>
	<div class="reply_section">
		<jsp:include page="reply.jsp"></jsp:include>
	</div>
</div>
<!-- 게시글 신고 모달 -->
<form id="reportForm" name="reportForm" action="report?type=${type}&page=${type }&idx=${idx }" method="post">
<input type="hidden" name="userIdx" value=${userIdx } />
<div id="modal" class="modal-overlay" style="display:none;">
	<div class="modal-window">
		<div class="close-area"><ion-icon name='close-outline'></ion-icon></div>
		<div class="modal-header"><h2>게시글 신고하기</h2></div>
		<div class="modal-content">
			<strong class="title_report"><span>신고 사유 선택</span></strong>
			<input type="hidden" name="content" id="checkedReason" value="${checkedReason }" />
			<ul class="report_list">
				<li class="reason_check">
					<div class="choice_wrap">
						<input type="radio" id="C" class="report_check" name="category" onclick="checkReason('C');" value="C">
						<label for="C" class="lab_radio">
							<span id="C">욕설, 비방, 차별, 혐오</span>
						</label>
					</div>
				</li>
				<li class="reason_check">
					<div class="choice_wrap">
						<input type="radio" id="A" class="report_check" name="category" onclick="checkReason('A');" value="A">
						<label for="A" class="lab_radio">
							<span id="A">홍보, 영리 목적</span>
						</label>
					</div>
				</li>
				<li class="reason_check">
					<div class="choice_wrap">
						<input type="radio" id="U" class="report_check" name="category" onclick="checkReason('U');" value="U">
						<label for="U" class="lab_radio">
							<span id="U">불법 정보</span>
						</label>
					</div>
				</li>
				<li class="reason_check">
					<div class="choice_wrap">
						<input type="radio" id="O" class="report_check" name="category" onclick="checkReason('O');" value="O">
						<label for="O" class="lab_radio">
							<span id="O">음란, 청소년 유해</span>
						</label>
					</div>
				</li>
				<li class="reason_check">
					<div class="choice_wrap">
						<input type="radio" id="P" class="report_check" name="category" onclick="checkReason('P');" value="P">
						<label for="P" class="lab_radio">
							<span id="P">개인 정보 노출, 유포, 거래</span>
						</label>
					</div>
				</li>
				<li class="reason_check">
					<div class="choice_wrap">
						<input type="radio" id="E" class="report_check" name="category" onclick="checkReason('E');" value="E">
						<label for="E" class="lab_radio">
							<span id="E">도배, 스팸</span>
						</label>
					</div>
				</li>
				<li class="reason_check">
					<div class="choice_wrap">
						<input type="radio" id="G" class="report_check" name="category" onclick="checkReason('G');" value="G">
						<label for="G" class="lab_radio">
							<span>기타</span>
						</label>
					</div>
					<div class="wrap_additional" id="g_textarea" style="display: none;">
                    	<div class="tfetc_wrap">
                    		<textarea class="tf_etc reason" id="etcReason" placeholder="신고 사유 직접 입력 " name="etc" onkeyup="limit(this)"></textarea>
                    		<sup>(<span id="nowByte">0</span>/250 Byte)</sup>
                        </div>
                    </div>
				</li>
			</ul>
			<div class="btn_group">
				<button class="btn btn-dark btn_report">
					<span class="inner_btn">신고하기</span>
				</button>
			</div>
		</div>
	</div>
</div>
</form>
<script>
function openReportModal(){
	var userIdx = ${userIdx};
	var reportUserIdx = ${info.user_idx};
	var reportMessage = document.getElementById("reportMessage").value;

	if(userIdx == reportUserIdx){
		alert("작성자는 신고할 수 없습니다.");
	}else if(reportMessage == "done"){
		alert("이미 신고한 게시글입니다.");
	}else{
		const modal = document.getElementById("modal");
		const reportBtn = document.getElementById("reportBtn");
		reportBtn.addEventListener('click', () => {
			modal.style.display = 'block';
		});
	}
}
const closeBtn = modal.querySelector(".close-area");
closeBtn.addEventListener("click", e =>{
	modal.style.display = 'none';
	});


function limit(obj){
	const maxByte = 100; //최대 100바이트
    const text_val = obj.value; //입력한 문자
    const text_len = text_val.length; //입력한 문자수
    
    var totalByte=0;
    
    for(let i=0; i<text_len; i++){
    	const each_char = text_val.charAt(i);
        const uni_char = escape(each_char) //유니코드 형식으로 변환
        if(uni_char.length>4){
        	// 한글 : 2Byte
            totalByte += 2;
        }else{
        	// 영문,숫자,특수문자 : 1Byte
            totalByte += 1;
        }
    }
    if(totalByte > maxByte){
    	alert('최대 100Byte까지만 입력가능합니다.');
        	document.getElementById("nowByte").innerText = totalByte;
            document.getElementById("nowByte").style.color = "red";
        }else{
        	document.getElementById("nowByte").innerText = totalByte;
            document.getElementById("nowByte").style.color = "green";
        }
    return text_val;
}

function checkReason(type){
	const textarea = document.getElementById("g_textarea");
	var category = document.getElementById(type).value;
	var content = $("span[id='"+type+"']").text();
	reportForm.checkedReason.value = content;
	
	if(category == 'G'){
		textarea.style.display = '';
	}else{
		textarea.style.display = 'none';
	}
	
	
}
/*
function submit_report(){
	const boardIdx = ${idx };
	const userIdx = ${userIdx };
	
	var category = $("input[name='category']").attr('id');
	var content = $("input[name='category']:checked").val();
	
	reportForm.checkedReason.value = category;
	alert(reportForm.checkedReason.value);
	

	if(category == 'G'){
		content = document.getElementById("etcReason").value;
	}
	
	
}*/

</script>
</body>

</html>