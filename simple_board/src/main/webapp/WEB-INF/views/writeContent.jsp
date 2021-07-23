<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Write</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body style="padding-left: 15%; padding-right: 10%;">
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<form action="write" method="post" id="insertNewContent" enctype="multipart/form-data">
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
		<input type="hidden" id="user_idx" name="userIdx" value="${user_idx }">
		<input type="hidden" id="type" name="type" value="${type }">		
		<table class="table table-borderless">
			<tr>
				<td>
					<div class="input-group">
					<select name="subject" class="custom-select col-sm-6" style="width:auto;">
					    <option selected value="none">말머리</option>
					    <option value="conversation">사담</option>
					    <option value="question">질문</option>
					    <option value="information">정보</option>
		 			</select>
		  			<input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력하세요." style="width:80%;" required="required"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="form-group">
			  			<textarea class="form-control" rows="10" id="content" name="content" placeholder="내용을 입력하세요." required="required"></textarea>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<input type="file" name="multiparts" id="multiparts" multiple="multiple" />
				</td>
			</tr>
			<tr>
				<td>
					<div class="imgs_wrap" style="overflow:hidden; height: auto; width: 100%;"></div>
				</td>
			</tr>
			
			<tr>
				<td style="text-align: center;">
					<input type="submit" class="btn btn-dark" id="insert" value="작성">
					<button class="btn btn-light" onclick="history.back(-1)">취소</button>
				</td>
			</tr>
		</table>
	</div>
</form>
<script>			
$(document).ready(function(){
	$('.imgs_wrap').hide();
	//input images
	$("#multiparts").on("change", handleImgFileSelect);
	});
function fileimgAction(){
		$("#multiparts").trigger('click');
	}

function handleImgFileSelect(e) {
	//이미지 정보 초기화
	var sel_files = [];
	$(".imgs_wrap").empty();

	var files = e.target.files;
	var filesArr = Array.prototype.slice.call(files);

	if(files.length > 5){
          alert("이미지는 최대 5개까지 업로드 가능");
          $("input[type='file']").val("");
          return;
       }
	filesArr.forEach(function(f) {
				if (!f.type.match("image.*")) {
					alert("이미지 파일을 선택해주세요!");
					$("input[type='file']").val("");
					return;
				}
				
				sel_files.push(f);
				
				var reader = new FileReader();
				reader.onload = function(e) {
					$('.imgs_wrap').show();
					$('.imgs_wrap').css('height','100px;');
					var html = "<img src=\""+e.target.result+"\" style='width:100px; height:80px; margin:5px; float: left;'/>";
					$('.imgs_wrap').append(html);
				}
				reader.readAsDataURL(f);
			});
}
</script>

</body>
</html>