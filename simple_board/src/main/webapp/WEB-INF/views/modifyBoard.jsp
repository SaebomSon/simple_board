<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
#fileBtn{
	width: 6rem;
	height: 1.5rem;
	font-size: .90rem;
	vertical-align: text-top;
	padding: inherit;
}

.imageDel{
	display: block;
	font-size: 15px;
	color: gray;
	position: inherit;
	padding: 3px 10px;
	cursor: pointer;
}

.pImage{
	display:flex;
}
.pFilename{
	margin-bottom: -3px;
	font-size: 13px;
	cursor: pointer;
}
#modal .modal-overlay{
	width: 100%;
    height: 100%;
    left: 50%;
    top: 50%;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    backdrop-filter: blur(1.5px);
    -webkit-backdrop-filter: blur(1.5px);
    background: rgba(255, 255, 255, 0.25);
    border-radius: 10px;
}

#modal .modal-window{
	background: rgb(94 96 97 / 70%);
	backdrop-filter: blur( 13.5px );
	-webkit-backdrop-filter: blur( 13.5px );
	width: 600px;
	height: 600px;
	position: fixed;
	top: 10%;
	left: 30%;
	padding: 10px;
    border-radius: 10px;
}
#modal .close-area {
	display: inline;
	float: right;
	padding-right: 10px;
	cursor: pointer;
	text-shadow: 1px 1px 2px gray;
}
#modal .modal-content {
	width: 500px;
    margin: 9% 7%;
    padding: 4px 10px;
    text-shadow: 1px 1px 2px gray;
}
</style>
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
			<c:if test="${type eq 4 }">
				<ion-icon name="diamond-outline"></ion-icon>Diamond Board
			</c:if>
		</h2>
		<input type="hidden" id="idx" name="idx" value="${info.idx }">
		<table class="table table-borderless">
			<tr>
				<td style="padding-bottom: 2px;">
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
				<td style="padding-top: 2px; padding-bottom: 2px;">
					<input type="button" class="btn btn-light" id="fileBtn" value="파일 추가">
					<input type="file" name="multiparts" id="multiparts" multiple="multiple" style="display:none;"/>
					<span style="font-size:10px; color:gray;">⁕첨부파일은 최대 5개까지 등록할 수 있습니다.</span>
				</td>
			</tr>
			<tr>
				<td style="padding-top: 2px;">
					<div class="form-group">
			  			<textarea class="form-control" rows="10" id="content" name="content" required="required">${info.content }</textarea>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<p><b>첨부파일&nbsp;&nbsp;&nbsp;
					<span class="fileCount">${imgCount }</span></b></p>
					<div class="imgs_wrap" style="overflow:hidden; height: auto; width: 100%;">
						<c:if test="${images ne null }">
							<c:forEach items="${fn: split(images, '&') }" var="file_name" varStatus="status">
								<div class='pImage' id='pImage[${status.index }]'>
									<a onclick='openModal(${status.index}, "${file_name }")'>
										<input type="hidden" id="fileName[${status.index}]" name="org_fileName" value=${file_name } />
										<span class='pFilename' id='pFilename[${status.index}]'>${file_name }</span>
									</a>
									<a onclick='deleteAddedImage(${status.index }, "${file_name }")'><ion-icon name='close-outline' class='imageDel' id='delIcon'></ion-icon></a>
								</div>
							</c:forEach>
						</c:if>
					</div>
				</td>
			</tr>
		</table>
		<div style="text-align: center;">
			<input type="submit" class="btn btn-dark" id="modify" value="수정">
			<input type="button" class="btn btn-light" onclick="location.href='detail?type=${type}&page=${page }&idx=${info.idx }'" value="취소">
		</div>
	</div>
</form>
<div id="modal" class="modal-overlay" style="display:none;">
	<div class="modal-window">
		<div class="close-area"><ion-icon name='close-outline'></ion-icon></div>
		<div class="modal-content">
			<img id='modalImage'>
		</div>
	</div>
</div>

<script>			
$(document).ready(function(){
	//$(".imgs_wrap").empty();
	//$('.imgs_wrap').hide();
	//input images
	$("#multiparts").on("change", handleImgFileSelect);
	
});

$(function(){
	$("#fileBtn").click(function(e){
		e.preventDefault();
		$("#multiparts").click();
		});
});

function fileimgAction(){
		$("#fileBtn").trigger('click');
	}
	
// 전체 파일 개수 제한
var totalCount = 5;
// 현재 선택된 파일의 개수 - totalCount와 비교
var fileCount = ${imgCount };
console.log(fileCount)
// 파일의 index
var fileIndex = 0;
// 첨부파일 배열 초기화
var sel_files = new Array();

function handleImgFileSelect(e) {
	var files = e.target.files;
	var filesArr = Array.prototype.slice.call(files);
	
	if(fileCount + files.length > totalCount){
          alert("이미지는 최대 "+totalCount+"개까지 업로드 가능합니다.");
          $("input[type='file']").val("");
          return;
       }
	else{
		fileCount += files.length;
		$('.fileCount').text(fileCount);
		}

	// 각각의 파일 배열에 담기
	filesArr.forEach(function(f) {
		if (!f.type.match("image.*")) {
			fileCount -= 1;
			alert("이미지 파일을 선택해주세요!");
			//$("input[type='file']").val("");
			if(fileCount == 0){
				$('.fileCount').empty();
				}
			else{
				$('.fileCount').text(fileCount);
				}
			return;
		}
				
		var reader = new FileReader();
		reader.onload = function(e) {
			$('.imgs_wrap').show();
			sel_files.push(f);
			var src = e.target.result;
			var html = "<div class='pImage' id='pImage["+fileIndex+"]'>"
			+ "<a onclick='openModal("+fileIndex+", \""+ src+ "\")'><p class='pFilename' id='pFilename["+fileIndex+"]'>"+f.name+"</p></a>"
			+ "<a onclick='deleteAddedImage("+fileIndex+")'><ion-icon name='close-outline' class='imageDel' id='delIcon'></ion-icon></a>"
			+ "</div>";

			$('.imgs_wrap').append(html);
			fileIndex++;
		}
		reader.readAsDataURL(f);
	});
	//이미지 정보 초기화
	//$(".imgs_wrap").empty();
}

const modal = document.getElementById("modal");
const modalImage = document.getElementById("modalImage");
function openModal(idx, filename){
	console.log(idx)
	console.log(filename)
	var btnModal = document.getElementById("pFilename["+idx+"]");
	btnModal.addEventListener("click", e => {
		modal.style.display = "flex";
		var src = "resources/image/" + filename
		modalImage.setAttribute("src", src);
		$(".modal-content").append(modalImage);
		});
}

const closeBtn = modal.querySelector(".close-area");
closeBtn.addEventListener("click", e =>{
	$(".modal-content").empty();
	modal.style.display = "none";
	});

function deleteAddedImage(idx, fileName){
	var imageDiv = document.getElementById("pImage["+idx+"]");
	var boardIdx = ${info.idx};
	var data = {"idx": boardIdx, "fileName": fileName};
	console.log("idx > " + boardIdx);
	console.log("filename > " + fileName);
	
	if(confirm("첨부파일을 삭제하시겠습니까?")){
		$.ajax({
			url : "ajax/delAttach",
			type : "post",
			data : JSON.stringify(data),
			dataType : "json",
			contentType : "application/json;charsest=UTF-8",
			success : function(result) {
				if (result.result == "deleteAttachmentOk") {
					console.log("첨부파일 삭제 성공");
					imageDiv.remove();
					fileCount--;
					$('.fileCount').text(fileCount);
				}
			},
			error : function(e) {
				console.log("첨부파일 삭제 실패");
			}
		})
	}

	
}
</script>

</body>
</html>