<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Board</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- IONICONS -->
<script type="module" src="https://unpkg.com/ionicons@5.5.1/dist/ionicons/ionicons.esm.js"></script>
<script nomodule="" src="https://unpkg.com/ionicons@5.5.1/dist/ionicons/ionicons.js"></script>
<!-- BootStrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&family=Titillium+Web:ital,wght@1,600&display=swap');
.title{
	font-family: 'Titillium Web', sans-serif;
}
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
*{
	font-family: 'Nanum Gothic', sans-serif;
}
a:link, a:visited{
	text-decoration: none;
}
.board_title{
	color: black;
}
.board_title:hover{
	text-decoration: underline;
}
.reply_count{
	display: inline-block;
	min-width: 17px;
	height: 19px;
	padding: 1px 6px 0px 5px;
	border: 1px solid gray;
	border-radius: 50px;
	box-sizing: border-box;
	text-align: center;
	vertical-align: middle;
	line-height: 16px;
	cursor: pointer;
	color: red;
}
.reply_count:hover{
	text-decoration: none;
}
.ico_bbs {
    display: inline-block;
    vertical-align: middle;
    text-indent: -9999px;
    margin-top: -12px;
    margin-left: 1px;
}
.ico_bbs.ico_new {
	width: 10px;
    height: 10px;
    background: url(//t1.daumcdn.net/cafe_image/cf_img4/skin/W01/10_new.svg);
    margin: 5px 0 5px 2px;
}
</style>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>

<div class="container">
	<h2 class="title" style="margin-bottom: 5rem;">
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
  	<button class="btn btn-dark" id="newContent" style="margin-bottom: 1rem;" onclick="location.href='newContent?type=${type}'">글쓰기</button>
	<table class="table table-hover" style="text-align: center;">
	    <thead>
	      <tr>
	        <th colspan="2" style="width: 20rem;">제목</th>
	        <th style="text-align: left;">작성자</th>
	        <th>작성일</th>
	        <th>조회수</th>
	      </tr>
	    </thead>
	    <tbody>
		  	<c:forEach var="list" items="${searchList }">
		      <tr>
		        <td>${list.idx }</td>
	        	<td style="text-align: left;">
			        <c:choose>
				        <c:when test="${list.subject eq null }">
			        		<a class="board_title" onclick="location.href='searchDetail?type=${type }&page=${page }&idx=${list.idx }'" style="cursor:pointer;">${list.title }</a>&nbsp;
			        		<a class="reply_count"><span class="count_num">${list.reply_count }</span></a>
				        </c:when>
				        <c:otherwise>
			        		<a class="board_title" onclick="location.href='searchDetail?type=${type }&page=${page }&idx=${list.idx }'" style="cursor:pointer;">[${list.subject }] ${list.title }</a>&nbsp;
			        		<a class="reply_count"><span class="count_num">${list.reply_count }</span></a>			        		
				        </c:otherwise>
			        </c:choose>
			        <c:if test="${today < list.written_date }">
						<span class="ico_bbs ico_new" tabindex="0">새글</span>
					</c:if>
	        	</td>
		        <td style="text-align: left;">${list.nickname }</td>
		        <td style="text-align: left;">${list.written_date }</td>
		        <td>${list.hits }</td>
		      </tr>
		    </c:forEach>
	    </tbody>
	</table>
	<input type="button" class="btn btn-dark" value="전체 목록" onclick="location.href='boardType?type=${type}&page=1'">
	
	<form action="search" method="get">
		<div class="search col-sm-12" style="margin-bottom: 5rem;">
	  		<div class="form-group row float-right">
				<select class="form-control col-sm-3" name="option" style="margin-left:0.5rem; margin-right:0.3rem;">
					<option value="title">제목</option>
					<option value="content">내용</option>
					<option value="nickname">작성자</option>
				</select>
				<input type="hidden" name="type" value="${type }">
				<input type="hidden" name="page" value="1">
	  			<input class="form-control col-sm-6" type="text" name="keyword" placeholder="검색어를 입력하세요." autocomplete="off" style="margin-right:0.3rem;">
		  		<input type="submit" class="btn btn-dark col-sm-2" value="검색">
			</div>
	  	</div>
  	</form>
</div>

<!-- page bar -->
<div class="paginationBlock col-sm-12" style="margin-top:3rem; margin-bottom: 5rem; display: flex; justify-content: center;">
	<ul class="pagination">
	<c:if test="${blockStart > 1 }">
  	<li class="page-item"><a class="page-link" href="boardType?type=${type }&page=${blockStart - 1 }"><span>«</span></a></li></c:if>
	  <c:set var="active" value="${page }" />
	  <c:forEach var="num" begin="${blockStart }" end="${blockEnd }">
	  	<c:if test="${num <= lastPage }">
		  <c:choose>
		  	<c:when test="${num == active }">
		  		<li class="page-item active"><a class="page-link">${num }</a></li>
		  	</c:when>
		  	<c:otherwise>
		  		<li class="page-item"><a class="page-link" href="boardType?type=${type }&page=${num }">${num }</a></li>
		  	</c:otherwise>
		  </c:choose>
		  </c:if>
	  </c:forEach>
	  <c:if test="${blockEnd < lastPage }">
	  <li class="page-item"><a class="page-link" href="boardType?type=${type }&page=${blockEnd + 1 }"><span>»</span></a></li></c:if>
	</ul>
</div>
</body>

</html>