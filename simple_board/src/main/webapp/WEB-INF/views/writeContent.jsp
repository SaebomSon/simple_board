<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rampart+One&family=Titillium+Web:ital,wght@1,600&display=swap');
*{
	font-family: 'Nanum Gothic', sans-serif;
}
.title{
	font-family: 'Titillium Web', sans-serif;
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
<!-- ????????? ??? -->
<jsp:include page="index.jsp" flush="false"></jsp:include>
<form action="write" method="post" id="insertNewContent" enctype="multipart/form-data">
	<sec:csrfInput/>
	<div class="container">
		<h2 class="title" style="margin-bottom: 100px;">
			<c:if test="${type eq 1 }">Leaf Board</c:if>
			<c:if test="${type eq 2 }">Flower Board</c:if>
			<c:if test="${type eq 4 }">Diamond Board</c:if>
		</h2>
		<table class="table table-borderless">
			<tr>
				<td style="padding-bottom: 2px;">
					<div class="input-group">
					<select name="subject" class="custom-select col-sm-6" style="width:auto;">
					    <option selected value="NONE">?????????</option>
					    <option value="C">??????</option>
					    <option value="Q">??????</option>
					    <option value="I">??????</option>
		 			</select>
		  			<input type="text" class="form-control" id="title" name="title" placeholder="????????? ???????????????." style="width:80%;" required="required"></div>
				</td>
			</tr>
			<tr>
				<td style="padding-top: 2px; padding-bottom: 2px;">
					<input type="button" class="btn btn-light" id="fileBtn" value="?????? ??????">
					<input type="file" name="multiparts" id="multiparts" multiple="multiple" style="display:none;"/>
					<span style="font-size:10px; color:gray;">?????????????????? ?????? 5????????? ????????? ??? ????????????.</span>
				</td>
			</tr>
			<tr>
				<td style="padding-top: 2px;">
					<div class="form-group">
			  			<textarea class="form-control" rows="10" id="content" name="content" placeholder="????????? ???????????????." required="required"></textarea>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<p><b>????????????&nbsp;&nbsp;&nbsp;
					<span class="fileCount"></span></b></p>
					<div class="imgs_wrap" style="position: relative; overflow:hidden; height: auto; width: 100%;">
					</div>
				</td>
			</tr>
		</table>
		<div style="text-align: center;">
			<input type="submit" class="btn btn-dark" id="insert" value="??????">
			<button class="btn btn-light" onclick="history.back(-1)">??????</button>
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
	$(".imgs_wrap").empty();
	$('.imgs_wrap').hide();
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
	
// ?????? ?????? ?????? ??????
var totalCount = 5;
// ?????? ????????? ????????? ?????? - totalCount??? ??????
var fileCount = 0;
// ????????? index
var fileIndex = 0;
// ???????????? ?????? ?????????
var sel_files = new Array();

function handleImgFileSelect(e) {
	var files = e.target.files;
	var filesArr = Array.prototype.slice.call(files);
	
	if(fileCount + files.length > totalCount){
          alert("???????????? ?????? "+totalCount+"????????? ????????? ???????????????.");
          $("input[type='file']").val("");
          return;
       }
	else{
		fileCount += files.length;
		$('.fileCount').text(fileCount);
		}

	// ????????? ?????? ????????? ??????
	filesArr.forEach(function(f) {
		if (!f.type.match("image.*")) {
			fileCount -= 1;
			alert("????????? ????????? ??????????????????!");
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
	//????????? ?????? ?????????
	//$(".imgs_wrap").empty();
}

const modal = document.getElementById("modal");
const modalImage = document.getElementById("modalImage");
function openModal(idx, src){
	const btnModal = document.getElementById("pFilename["+idx+"]");
	btnModal.addEventListener("click", e => {
		modal.style.display = "flex";
		modalImage.setAttribute("src", src);
		$(".modal-content").append(modalImage);
		});
}

const closeBtn = modal.querySelector(".close-area");
closeBtn.addEventListener("click", e =>{
	$(".modal-content").empty();
	modal.style.display = "none";
	});

function deleteAddedImage(idx){
	console.log(idx);
	var imageDiv = document.getElementById("pImage["+idx+"]");
	console.log(imageDiv);
	imageDiv.remove();
	fileCount--;
	$('.fileCount').text(fileCount);
}

</script>

</body>
</html>