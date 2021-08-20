<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정 완료</title>
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
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
*{
	font-family: 'Nanum Gothic', sans-serif;
}
</style>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<div class="container">
	<h2 style="margin-bottom: 80px;">
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
	<div class="container-sm p-3 my-3 border" style="height: 200px; text-align: center;">
		<div class="col-sm-12" style="margin: 30px 0; font-size: 25px;">
			<span>게시물이 수정되었습니다.</span>
		</div>
		<div class="col-sm-12" style="">
			<button class="btn btn-dark" onclick="location.href='detail?type=${type}&page=${type }&idx=${idx }'">작성한 글 확인</button>
			<button class="btn btn-dark" onclick="location.href='boardType?type=${type}&page=1'">목록 보기</button>
		</div>
	</div>
</div>

</body>
</html>