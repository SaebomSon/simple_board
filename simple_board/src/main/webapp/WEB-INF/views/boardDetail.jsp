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
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<div class="container">
	<h2 style="margin-bottom: 100px;">
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
	<div class="title_section">
		<c:choose>
	        <c:when test="${info.subject eq null }">
	        	<td style="text-align: left; cursor:pointer;">${info.title }</td>
	        </c:when>
	        <c:otherwise>
	        	<td style="text-align: left; cursor:pointer;">[${info.subject }] ${info.title }</td>
	        </c:otherwise>
		</c:choose>
	</div>
	<hr>
	<div class="user_section">
		${info.nickname }&nbsp;&nbsp;<img src="resources/image/${info.level_image }">&nbsp; | &nbsp;&nbsp;
		<ion-icon name="calendar-outline"></ion-icon>&nbsp;&nbsp;${info.written_date }&nbsp;&nbsp; | &nbsp;&nbsp;
		<ion-icon name="eye-outline"></ion-icon>&nbsp;&nbsp;${info.hits }
	</div>
	<hr>
	<div class="detail_section">
		${info.content }<br>
		<c:if test="${images ne null }">
			<c:forEach items="${fn: split(images, '&') }" var="file_name">
				<img src="resources/image/${file_name }" style="width:100%;"><br>
			</c:forEach>
		</c:if>
	</div>
	<div style="margin-top: 20px;">
		<input type="button" class="btn btn-dark" value="목록" onclick="location.href='boardType?type=${type}'">
	</div>
</div>
</body>
</html>