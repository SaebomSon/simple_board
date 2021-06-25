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
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>

<div class="container">
	<h2 style="margin-bottom: 5rem;">
		<c:if test="${type eq 1 }">
				<ion-icon name="leaf-outline"></ion-icon>Leaf Board
		</c:if>
		<c:if test="${type eq 2 }">
			<ion-icon name="flower-outline"></ion-icon>Flower Board
		</c:if>
		<c:if test="${type eq 3 }">
			<ion-icon name="diamond-outline"></ion-icon>Diamond Board
		</c:if>
	</h2>
    <!-- 
	<div class="radio container" style="text-align: end;">
  		<input type="radio" id="orderDate" name="orderby" checked="checked">
  		<label for="orderDate">최신순</label>&nbsp;&nbsp;
  		<input type="radio" id="orderHits" name="orderby">
  		<label for="orderHits">인기순</label>
  	</div> -->
  	
  	<button class="btn btn-dark" id="newContent" style="margin-bottom: 1rem;" onclick="location.href='newContent?type=${type}'">글쓰기</button>
	<table class="table table-hover" style="text-align: center;">
	    <thead>
	      <tr>
	        <th>글번호</th>
	        <th>제목</th>
	        <th>작성자</th>
	        <th>작성일</th>
	        <th>조회수</th>
	      </tr>
	    </thead>
	    <tbody>
		  	<c:forEach var="list" items="${searchList }">
		      <tr onclick="location.href='detail?type=${type }&idx=${list.idx }'" style="cursor:pointer;">
		        <td>${list.idx }</td>
		        <c:choose>
			        <c:when test="${list.subject eq null }">
			        	<td style="text-align: left;">${list.title }</td>
			        </c:when>
			        <c:otherwise>
			        	<td style="text-align: left;">[${list.subject }] ${list.title }</td>
			        </c:otherwise>
		        </c:choose>
		        <td>${list.nickname }&nbsp;<img src="resources/image/${list.level_image }"></td>
		        <td>${list.written_date }</td>
		        <td>${list.hits }</td>
		      </tr>
		    </c:forEach>
	    </tbody>
	</table>
	<input type="button" class="btn btn-dark" value="전체 목록" onclick="location.href='boardType?type=${type}'">
	<form action="search" method="get">
		<div class="search col-sm-12" style="margin-bottom: 5rem;">
	  		<div class="form-group row float-right">
				<select class="form-control col-sm-3" name="option" style="margin-left:0.5rem; margin-right:0.3rem;">
					<option value="title">제목</option>
					<option value="content">내용</option>
					<option value="nickname">작성자</option>
				</select>
				<input type="hidden" name="type" value="${type }">
	  			<input class="form-control col-sm-6" type="text" name="keyword" placeholder="검색어를 입력하세요." autocomplete="off" style="margin-right:0.3rem;">
		  		<input type="submit" class="btn btn-dark col-sm-2" value="검색">
			</div>
	  	</div>
  	</form>
</div>

<!-- page bar -->
<div class="paginationBlock col-sm-12" style="margin-top:3rem; margin-bottom: 5rem; display: flex; justify-content: center;">
	<ul class="pagination">
	  <li class="page-item"><a class="page-link" href=""><span>«</span></a></li>
	  <c:forEach var="page" begin="1" end="${lastPage }">
		  <c:choose>
		  	<c:when test="${page eq 1 }">
		  		<li class="page-item active"><a class="page-link" href="">${page }</a></li>
		  	</c:when>
		  	<c:otherwise>
		  		<li class="page-item"><a class="page-link" href="">${page }</a></li>	  		
		  	</c:otherwise>
		  </c:choose>	  	
	  </c:forEach>
	  <li class="page-item"><a class="page-link" href=""><span>»</span></a></li>
	</ul>
</div>

</body>
<!-- JS -->
<script type="text/javascript" src="resources/js/main.js?ver=3"></script>
</html>