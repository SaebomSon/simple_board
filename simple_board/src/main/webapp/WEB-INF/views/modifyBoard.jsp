<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 사이드 바 -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<form action="modify" method="post" enctype="multipart/form-data">
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
		<input type="hidden" id="idx" name="idx" value="${info.idx }">
		<table class="table table-borderless">
			<tr>
				<td>
					<div class="input-group">
					
					<select name="subject" class="custom-select col-sm-6" style="width:auto;">
					    <option value="none" <c:if test="${info.subject eq null }">selected</c:if>>말머리 없음</option>
				    	<option value="conversation" <c:if test="${info.subject eq '사담'}">selected</c:if>>사담</option>
				    	<option value="question" <c:if test="${info.subject eq '질문'}">selected</c:if>>질문</option>
				    	<option value="information" <c:if test="${info.subject eq '정보'}">selected</c:if>>정보</option>
		 			</select>
		  			<input type="text" class="form-control" id="title" name="title" style="width:80%;" required="required" value="${info.title }"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="form-group">
			  			<textarea class="form-control" rows="10" id="content" name="content" required="required">${info.content }</textarea>
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
		</table>
		<div style="text-align: center;">
			<input type="submit" class="btn btn-dark" id="modify" value="수정">
			<input type="button" class="btn btn-light" onclick="location.href='detail?type=${type}&page=${page }&idx=${info.idx }'" value="취소">
		</div>
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