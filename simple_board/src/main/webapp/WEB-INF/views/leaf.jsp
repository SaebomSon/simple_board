<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Leaf Board</title>
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
  <h2 style="margin-bottom: 100px;">Leaf Board</h2>
         
  <table class="table table-hover">
  	<div class="radio container" style="text-align: end;">
  		<input type="radio" id="orderDate" name="orderby" checked="checked">
  		<label for="orderDate">최신순</label>
  		<input type="radio" id="orderHits" name="orderby">
  		<label for="orderHits">인기순</label>
  	</div>
	
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
      <tr>
        <td>John</td>
        <td>Doe</td>
        <td>john@example.com</td>
        <td>john@example.com</td>
        <td>john@example.com</td>
      </tr>
    </tbody>
  </table>
  <button class="btn btn-dark" id="newContent" onclick="location.href='newContent?type=1'">글쓰기</button>
</div>
</body>
</html>