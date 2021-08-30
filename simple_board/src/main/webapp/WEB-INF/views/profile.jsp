<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&family=Titillium+Web:ital,wght@1,600&display=swap');

.title{
	font-family: 'Titillium Web', sans-serif;
}
*{
	font-family: 'Nanum Gothic', sans-serif;
}
img {
    vertical-align: middle;
    border-style: none;
}
.profile_thumb {
    display: inline-block;
    width: 100px;
    height: 100px;
    padding-right: 8px;
    margin-bottom: 2rem;
}

.profile_thumb:after {
    position: absolute;
    width: 100px;
    height: 100px;
    display: block;
    border: 1px solid rgba(0,0,0,.05);
    border-radius: 50%;
}
.profile_thumb .img_thumb {
    /*position: absolute;*/
    /*width: 50%;
    height: 200%;*/
    border-radius: 50%;
}

.user_info_detail .my_info{
	margin-bottom: 5px;
}
</style>
<script>
$(function(){
	const url = document.location.href;
	const type = url.split("/");
	/* LINK ACTIVE */
	const linkColor = document.querySelectorAll('.nav__link')
	
	if(type[3] == 'profile'){
		linkColor.forEach(l=> l.classList.remove('active'))
		linkColor[4].classList.add('active');
		}

	//닉네임 중복 실시간 check
	/*
	$("#user-nickname").on("change keyup paste", function(){
		const originNick = $("#original-user-nickname").val();
		const userNick = $("#user-nickname").val();
		$.ajax({
			type: "post",
			url: "ajax/signUp/nickCheck",
			data: userNick,
			dataType: "text",
		    contentType:"application/json;charset=UTF-8",
			success: function(message){
					if(message =="ok"){
						$("#nicknameCheck").text("사용 가능한 닉네임입니다.");
						$("#nicknameCheck").css('color', 'green');
						$("#nicknameCheck").css('font-size', '12px');
					}else{
						if(originNick == userNick){
							$("#nicknameCheck").text("");
						}else{
							$("#nicknameCheck").text("동일한 닉네임이 존재합니다.");
							$("#nicknameCheck").css('color', 'red');
							$("#nicknameCheck").css('font-size', '12px');
							}
					}
			},
			error: function(){
				console.log("응답 실패");
			}
		})	// ajax end
	})*/
	
	// 비밀번호 실시간 check
	$("#new-password").on("change keyup paste", function(){
		const password = $("#new-password").val();
		const pwReg = /(?=.*[a-zA-ZS])(?=.*?[#?!@$%^&*-]).{6,18}/;
		
		if(password == ''){
			$("#newPassword").text("");
		}
		else if(!pwReg.test(password)){
			$("#newPassword").text("비밀번호는 영문,특수문자 포함 6~18자리만 허용됩니다.");
			$("#newPassword").css('color', 'red');
			$("#newPassword").css('font-size', '12px');
			$("#newPassword").focus()
		}
		else{
			$("#newPassword").text("사용 가능한 비밀번호입니다.");
			$("#newPassword").css('color', 'green');
			$("#newPassword").css('font-size', '12px');
		}
	})	// function end
	// 비밀번호 재확인 실시간 check
	$("#new-password-check").on("change keyup paste", function(){
		const password = $("#new-password").val();
		const passwordChk = $("#new-password-check").val();
		if(passwordChk == ''){
			$("#newPasswordCheck").text("");
			}
		else{
			if(password == ''){
				alert("비밀번호를 먼저 입력하세요.");
				$("#pw").focus();
				}
			else if(password == passwordChk){
				$("#newPasswordCheck").text("확인되었습니다.");
				$("#newPasswordCheck").css('color', 'green');
				$("#newPasswordCheck").css('font-size', '12px');
				document.getElementById("change-password-btn").disabled = false;
				
			}else{
				$("#newPasswordCheck").text("비밀번호가 다릅니다.");
				$("#newPasswordCheck").css('color', 'red');
				$("#newPasswordCheck").css('font-size', '12px');
			}
		}
	})	// function end

});

// 닉네임 수정
function changeNickname(idx){
	const userNick = document.getElementById("user-nickname").value;
	const data = {"idx" : idx, "nickname" : userNick};

	$.ajax({
		type: "post",
		url: "ajax/modifyNickname",
		data: JSON.stringify(data),
		dataType : "json",
	    contentType:"application/json;charset=UTF-8",
		success: function(result){
				if(result.result == "ok"){
					alert("닉네임이 변경 되었습니다.")
				}else{
					alert("동일한 닉네임이 존재합니다.")
					document.getElementById("user-nickname").value = document.getElementById("original-user-nickname").value;
				}
		},
		error: function(){
			console.log("응답 실패");
		}
	})	// ajax end
	
}
//비밀번호 수정 section 열기
function openChangePassword(){
	var newSection = document.getElementById("new-password-section");
	newSection.style.display = '';
}

</script>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<div class="container">
	<div class="col-sm-12" style="text-align: center;">
		<div class="profile_thumb">
			<img src="//img1.daumcdn.net/thumb/C72x72/?fname=https://t1.daumcdn.net/cafe_image/img_profile/profile_cob.png"
			width="100" height="100" class="img_thumb">
		</div>
	</div>
	<div class="col-sm-12" style="margin-left: 25%;">
	    <div class="user-info-detail" >
	    	<form>
		    	<c:forEach var="user" items="${userList }">
		    		<input type="hidden" name="idx" value="${user.idx }" />
			    	<div class="form-group">
			            <label for="id">아이디[등급]</label>
			            <input type="text" class="form-control my_info" id="user-id" style="width: 50%;" value="${user.id }[${user.level_name }]" disabled/>
			        </div>
			        <div class="form-group">
			            <label for="nickname">닉네임</label>
				            <div>
				            	<input type="hidden" id="original-user-nickname" value="${user.nickname }" />
					            <input type="text" class="form-control my_info" id="user-nickname" name="nickname" style="float: left; width: 43%; margin-right:3px;" value="${user.nickname }" />
					            <button type="button" onclick="changeNickname(${user.idx});" id="change-nickname" style="width: 7%;" class="btn btn-dark">변경</button>
				            </div>
			        </div>	        
			        <div class="form-group">
			            <label for="email">이메일 주소</label>
		            	<input type="text" class="form-control my_info" id="user-email" style="width: 50%;" value="${user.email }" disabled/>
			        </div>
			        <div class="form-group">
			            <label for="password">현재 비밀번호</label>
			            <div>
				            <input type="text" class="form-control my_info" id="user-password" style="float: left; width: 43%; margin-right:3px;" value="${user.password }" disabled/>
				            <button type="button" onclick="openChangePassword();" id="change-password" style="width: 7%;" class="btn btn-dark">변경</button>
			            </div>
			        </div>
			        <div id="new-password-section" style="display: none;">
				        <div class="form-group">
				            <label for="newPassword">새 비밀번호</label>
				            <input type="password" class="form-control my_info" id="new-password" style="width: 50%;" value="" />
				            <span id="newPassword"></span>
				        </div>
				        <div class="form-group">
				            <label for="newPasswordCheck">새 비밀번호 확인</label>
				            <input type="password" class="form-control my_info" name="password" id="new-password-check" style="width: 50%;"value="" />
				            <span id="newPasswordCheck"></span>
				        </div>
				        <button type="submit" id="change-password-btn" formaction="modifyPassword" formmethod="post" style="width: 15%;" class="btn btn-dark" disabled>비밀번호 변경</button>
			        </div>
		        </c:forEach>
	        </form>
        </div>
	</div>
</div>
</body>
</html>