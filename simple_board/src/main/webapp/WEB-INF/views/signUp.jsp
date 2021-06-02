<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
.agree_form{
	font-size: 12px;
	display: block; 
	padding:10px; 
	border: solid 1px rgba(218,222,225,0.7);
	height: 150px;
	overflow: auto;
	font-family: nanum gothic !important;
	margin-bottom: 5px;
	width: 100%;
}
.btn-dark{
	color:#ffffff;
	background-color:#d091d0;
	border-color:#d091d0;
}
.btn-dark:hover{
	color:#d091d0;
	background-color:#ffffff;
	border: solid 2px #d091d0;
}
.btn-light{
	color:#d091d0;
	background-color:#ffffff;
	border: solid 2px #d091d0;
}
.btn-light:hover{
	color:#d091d0;
	background-color:#ffffff;
	border: solid 2px #d091d0;
}
input[type="checkbox"]:checked + label:after{
	background-color: #d091d0;
	border-radius: 4px;
}
	
</style>
<script>
$(function(){
	// 아이디 중복 실시간 check
	$("#id").on("change keyup paste", function(){
		const userId = $("#id").val();
		const idReg = /^[a-z0-9]{4,12}$/;
		if(userId == ''){
			$("#idCheck").text("");
			}
		else if(!idReg.test(userId)){
			$("#idCheck").text("아이디는 영문 소문자, 숫자 조합으로 4~12자리만 허용됩니다.");
			$("#idCheck").css('color', 'red');
			$("#idCheck").css('font-size', '12px');
			}
		else{
			$.ajax({
				type: "post",
				url: "ajax/signUp/idCheck",
				data: userId,
				dataType: "text",
			    contentType:"application/json;charset=UTF-8",
				success: function(message){
						if(message =="ok"){
							$("#idCheck").text("사용 가능한 아이디입니다.");
							$("#idCheck").css('color', 'green');
							$("#idCheck").css('font-size', '12px');
						}else{
							$("#idCheck").text("동일한 아이디가 존재합니다.");
							$("#idCheck").css('color', 'red');
							$("#idCheck").css('font-size', '12px');
						}
					},
				error: function(){
					console.log("응답 실패");
					}
				})	// ajax end
			}
		})	// function end

	// 닉네임 중복 실시간 check
	$("#nickname").on("change keyup paste", function(){
		const userNick = $("#nickname").val();
		if(userNick == ''){
			$("#nicknameCheck").text("");
			}
		else{
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
							$("#nicknameCheck").text("동일한 닉네임이 존재합니다.");
							$("#nicknameCheck").css('color', 'red');
							$("#nicknameCheck").css('font-size', '12px');
						}
				},
				error: function(){
					console.log("응답 실패");
				}
			})	// ajax end
		}
	})	// function end
	
	// 비밀번호 실시간 check
	$("#pw").on("change keyup paste", function(){
		const password = $("#pw").val();
		const pwReg = /(?=.*[a-zA-ZS])(?=.*?[#?!@$%^&*-]).{6,18}/;
		
		if(password == ''){
			$("#password").text("");
		}
		else if(!pwReg.test(password)){
			$("#password").text("비밀번호는 영문,특수문자 포함 6~18자리만 허용됩니다.");
			$("#password").css('color', 'red');
			$("#password").css('font-size', '12px');
			$("#password").focus()
		}
		else{
			$("#password").text("사용 가능한 비밀번호입니다.");
			$("#password").css('color', 'green');
			$("#password").css('font-size', '12px');
		}
	})	// function end
	// 비밀번호 재확인 실시간 check
	$("#inputPasswordCheck").on("change keyup paste", function(){
		const password = $("#pw").val();
		const passwordChk = $("#inputPasswordCheck").val();
		if(passwordChk == ''){
			$("#passwordCheck").text("");
			}
		else{
			if(password == ''){
				alert("비밀번호를 먼저 입력하세요.");
				$("#pw").focus();
				}
			else if(password == passwordChk){
				$("#passwordCheck").text("확인되었습니다.");
				$("#passwordCheck").css('color', 'green');
				$("#passwordCheck").css('font-size', '12px');
			}else{
				$("#passwordCheck").text("비밀번호가 다릅니다.");
				$("#passwordCheck").css('color', 'red');
				$("#passwordCheck").css('font-size', '12px');
			}
		}
	})	// function end

	// 이메일 중복 여부 실시간 check
	$("#email").on("change keyup paste", function(){
		const userEmail = $("#email").val();
		const emailReg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if(userEmail == ''){
			$("#emailCheck").text("");
		}
		else if(!emailReg.test(userEmail)){
			$("#emailCheck").text("잘못된 이메일 형식입니다.");
			$("#emailCheck").css('color', 'red');
			$("#emailCheck").css('font-size', '12px');
		}
		else{
			$.ajax({
				type: "post",
				url: "ajax/signUp/emailCheck",
				data: userEmail,
				dataType: "text",
			    contentType:"application/json;charset=UTF-8",
				success: function(message){
						if(message =="ok"){
							$("#emailCheck").text("사용 가능한 이메일입니다.");
							$("#emailCheck").css('color', 'green');
							$("#emailCheck").css('font-size', '12px');
						}else{
							$("#emailCheck").text("동일한 이메일이 존재합니다.");
							$("#emailCheck").css('color', 'red');
							$("#emailCheck").css('font-size', '12px');
						}
				},
				error: function(){
					console.log("응답 실패");
				}
			})	// ajax end
		}	// else end
	})	// function end	
	
	//form data ajax 전송
	$("#submitJoin").on("click", function(){
		const id = $("#id").val();
		const pw = $("#pw").val();
		const nickname = $("#nickname").val();
		const email = $("#email").val();
		
		const data = {"id":id, "pw":pw, "nickname":nickname, "email":email}

		$.ajax({
			type: "post",
			url: "ajax/signUp/sendEmail",
			data: JSON.stringify(data),
			dataType: 'json',
			contentType:"application/json;charset=UTF-8",
		    processData: false,
		    cache: false,
			success: function(result){
				console.log(result);
				alert("인증메일이 전송되었습니다.");
				window.location="${pageContext.request.contextPath}";
			},
			error: function(){
				console.log("회원가입 실패");
			}
		})	// ajax end
	})
})
</script>
</head>

<body style="margin-right:15%; margin-left: 15%;">
<article class="container">
	<div class="page-header text-center" style="width:100%; margin-top: 5%; margin-bottom:5%;">
		<img class="img-fluid" id="signup_img" src="<c:url value="/resources/image/signup.png" />" style="width:30%; height: 100px;"/>
	</div>
	<div class="col-sm-6 col-md-offset-3" style="margin-left:25%">
	    <form id="signupForm" method="post" action="ajax/signUp/sendEmail">
	    	<div class="form-group">
	            <label for="inputId">아이디</label>
	            <input type="text" class="form-control" id="id" placeholder="아이디를 입력해 주세요." required>
	            <span id="idCheck"></span>
	        </div>
	        <div class="form-group">
	            <label for="inputNickname">닉네임</label>
	            <input type="text" class="form-control" id="nickname" placeholder="닉네임을 입력해 주세요." required>
	            <span id="nicknameCheck"></span>
	        </div>	        
	        <div class="form-group">
	            <label for="inputPassword">비밀번호</label>
	            <input type="password" class="form-control" id="pw" placeholder="비밀번호를 입력해주세요." required>
	        	<span id="password"></span>
	        </div>
	        <div class="form-group">
	            <label for="inputPasswordCheck">비밀번호 확인</label>
	            <input type="password" class="form-control" id="inputPasswordCheck" placeholder="비밀번호 확인을 위해 다시한번 입력 해 주세요." required>
	            <span id="passwordCheck"></span>
	        </div>
	        <div class="form-group">
	            <label for="inputEmail">이메일 주소</label>
	            <input type="email" class="form-control" id="email" placeholder="이메일 주소를 입력해주세요." required>
	            <span id="emailCheck"></span>
	        </div>
	        <div class="form-group">
		        <label>약관 동의</label>
		        <div class=agree_form>
		        	제 1 장 총칙<br>
					제 1 조 (목 적)<br>
					이 약관은 'SB' (이하 '회사'라 합니다.)가 제공하는 서비스의 이용조건 및 절차, 기타 필요한 사항을 규정함을 목적으로 합니다.<br>
					제 2 조 (약관의 효력 및 변경)<br> 1. 이 약관의 내용은 서비스 화면에 게시하거나 기타의 방법으로 회원에게 공지함으로써 효력이 발생합니다.<br>
					2. 회사는 합리적인 사유가 발생될 경우에는 이 약관을 변경할 수 있으며, 약관이 변경되는 경우에는 최소한 7일전에 1항과 같은 방법으로 공시합니다.<br>
					3. 본 사이트 안에 새로운 서비스가 개설될 경우 별도의 명시된 설명이 없는 한 이 약관에 따라 제공됩니다.<br>
					제 3 조 (약관 외 준칙)<br>
					이 약관에 명시되지 않은 사항은 전기통신기본법, 전기통신사업법 및 기타 관련법령의 규정에 따릅니다.<br>
					제 4 조 (정의)<br>
					1. 이 약관에서 사용하는 용어의 정의는 다음과 같습니다.<br>
					① 회 원 : 회사와 서비스 이용계약을 체결한 개인이나 법인 또는 법인에 준하는 단체<br>
					② 운영자 : 서비스의 전반적인 관리와 원활한 운영을 위하여 회사가 선정한 사람<br>
					③ 아이디(ID): 회원식별과 회원의 서비스 이용을 위하여 회원이 선정하고 회사가 승인하는 문자와 숫자의 조합<br>
					④ 비밀번호 : 회원의 정보 보호를 위해 회원 자신이 설정한 문자와 숫자의 조합<br>
					⑤ 서비스 중지: 정상이용 중 회사가 정한 일정한 요건에 따라 일정 기간 동안 서비스의 제공을 중지하는 것<br>
					⑥ 해지 : 회사 또는 회원이 서비스 개통 후 이용계약을 해약하는 것<br>
					2. 본 약관에서 사용하는 용어의 정의는 제1항에서 정하는 것을 제외하고는 관계법령 및 서비스별 안내에서 정하는 바에 의합니다.<br><br>
					제 2장 이용계약 체결<br>
					제 5 조 (서비스의 구분)<br>
					1. 회사가 회원에게 제공하는 서비스는 기본서비스와 부가서비스 등으로 구분합니다.<br>
					2. 서비스의 종류와 내용 등은 회사가 공지나 서비스 이용안내에서 별도로 정하는 바에 의합니다.<br>
					제 6 조 (이용계약의 성립)<br>
					아래 ' 위의 이용약관에 동의하십니까? ' 라는 물음에 회원이 '동의' 버튼을 누르면 이 약관에 동의하는 것으로 간주됩니다.<br>
					약관 변경 시에도 이와 동일하며, 변경된 약관에 동의하지 않을 경우 회원 등록 취소가 가능합니다.		        
		        </div>
		        <div class="custom-control custom-checkbox">
        			<input type="checkbox" id="agree" class="custom-control-input" required>
		        	<label class="custom-control-label" for="agree">이용약관에 동의합니다.</label>
		        </div>
		        </div>
	        <div class="form-group text-center">
	            <button type="button" id="submitJoin" class="btn btn-dark">회원가입<i class="fa fa-check spaceLeft"></i></button>
	            <button type="button" class="btn btn-light" onclick="history.back(-1)">가입취소<i class="fa fa-times spaceLeft"></i></button>
	        </div>
	    </form>
	</div>
</article>


</body>
</html>