<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reply List</title>
<style>
#level_image{
	width: 25px;
	border: 1px solid gray;
	border-radius: 50px;
	}
.replyBtns{
	cursor: pointer;
	color: black;
}
</style>
<script>
	$(function(){
		var original = document.querySelectorAll(".replyListSection .original_reply");
		var modify = document.querySelectorAll(".replyListSection .modify_reply");
		

		for (var i = 0; i < original.length; i++) {
			var item = original.item(i);
			item.style.display = '';
			}

		for (var j = 0; j < modify.length; j++) {
			var item = modify.item(j);
			item.style.display = 'none';
			}
		
		var modi_del = document.querySelectorAll(".replyListSection .modi_del");
		var modi_can = document.querySelectorAll(".replyListSection .modi_can");

		for (var i = 0; i < modi_del.length; i++) {
			var item = modi_del.item(i);
			item.style.display = '';
			}

		for (var j = 0; j < modi_can.length; j++) {
			var item = modi_can.item(j);
			item.style.display = 'none';
			}
		
		});


	// 댓글 등록 ajax
	function insertReply(){
			const boardIdx = ${info.idx};
			const userIdx = ${userIdx};
			const reply = document.getElementById('reply').value;
			const data = {"board_idx": boardIdx, "user_idx": userIdx, "content": reply};
			if(reply == ''){
				alert("댓글을 입력하세요.");
				document.getElementById('reply').focus();
				}
			else{
				$.ajax({
					url: "ajax/insertReply",
					type: "post",
					data: JSON.stringify(data),
					dataType: "json",
					contentType: "application/json;charsest=UTF-8",
					success: function(result){
							if(result.result == "ok"){
								document.getElementById('reply').value = "";
								getReplyList();
								console.log("댓글 작성 성공");
								}
						},
					error:function(e){
						console.log("댓글 작성 실패");
						}
					})
			}
		}
	
	// 댓글 불러오는 ajax
	function getReplyList(){
		var replyListSection = document.getElementsByClassName("replyListSection");
		var formData = new FormData();
		$.ajax({
			url: "getReply?idx=${info.idx}",
			type: "get",
			data: formData,
			dataType: "text",
			contentType: false,
			processData: false,
			success: function(result){
				console.log(result)
				var html = $('<div>').html(result);
				var replyContent = html.find("div.replyList").html();
				var replyCount = html.find("span.reply_count").html();
				
				$(".replyListSection").html(replyContent);
				$(".reply_count").html(replyCount);
				},
			error: function(e){
					console.log("fail getting replyList");
				}
			})
		}
	
	// 댓글 수정 영역 show
	function modifyBtn(idx){
		var original = document.getElementById("original_reply["+idx+"]");
		var modify = document.getElementById("modify_reply["+idx+"]");

		original.style.display = 'none';
		modify.style.display = '';
		
		var modi_del = document.getElementById("modi_del["+idx+"]");
		var modi_can = document.getElementById("modi_can["+idx+"]");

		modi_del.style.display = 'none';
		modi_can.style.display = '';
		}
	
	// 댓글 수정 취소 버튼
	function modifyCancelBtn(idx){
		var original = document.getElementById("original_reply["+idx+"]");
		var modify = document.getElementById("modify_reply["+idx+"]");

		original.style.display = '';
		modify.style.display = 'none';
		
		var modi_del = document.getElementById("modi_del["+idx+"]");
		var modi_can = document.getElementById("modi_can["+idx+"]");

		modi_del.style.display = '';
		modi_can.style.display = 'none';
		
		}
	
	// 댓글 수정하는 ajax
	function modifyReply(idx){
		const boardIdx = ${info.idx};
		const userIdx = ${userIdx};
		const modiReply = document.getElementById('reply_modify').value;
		const data = {"idx": idx, "board_idx": boardIdx, "user_idx": userIdx, "content": modiReply};

		$.ajax({
			url: "ajax/modifyReply",
			type: "post",
			data: JSON.stringify(data),
			dataType: "json",
			contentType: "application/json;charsest=UTF-8",
			success: function(result){
					if(result.result == "modifyOk"){
						getReplyList();
						console.log("댓글 수정 성공");
						}
				},
			error:function(e){
				console.log("댓글 수정 실패");
				}
			})
	}
		
	// 댓글 삭제하는 ajax
	function deleteReply(idx){
			const boardIdx = ${info.idx};
			const userIdx = ${userIdx};
			const data = {"idx": idx, "board_idx": boardIdx, "user_idx": userIdx};

			$.ajax({
				url: "ajax/deleteReply",
				type: "delete",
				data: JSON.stringify(data),
				dataType: "json",
				contentType: "application/json;charsest=UTF-8",
				success: function(result){
						if(result.result == "deleteOk"){
							getReplyList();
							console.log("댓글 삭제 성공");
							}
					},
				error:function(e){
					console.log("댓글 삭제 실패");
					}
				})
		}
</script>
</head>
<body>
<div class="reply_container" style="margin-top: 3rem; margin-bottom: 4rem;">
<b>댓글&nbsp;&nbsp;&nbsp; <span class="reply_count">${info.reply_count }</span></b>
<hr>
	<div class="inputReplySection" style="width:100%; height: 8rem;">
		<textarea class="reply_area" rows="2" cols="60" id="reply" placeholder="댓글을 작성하세요." style="width:100%;"></textarea>
		<button class="insert_reply btn btn-dark row float-right" onclick="insertReply();" style="margin-right: 5px;">댓글 작성</button>
	</div>
	<div class="replyListSection">
		<c:forEach var="reply" items="${replyInfo }">
			<input type="hidden" id="replyIdx[${reply.idx }]" value="${reply.idx }"/>
			<table style="width:100%; margin: 1rem 0;">
				<tbody class="parent_reply_section">
					<tr>
						<td style="width:8rem;"><img id="level_image" src="resources/image/${reply.level_image }"> ${reply.nickname }</td>
						<td style="width:20rem; color: gray; font-size: 13px;">
							<c:choose>
								<c:when test="${reply.written_date eq reply.modify_date}">
									${reply.written_date }
								</c:when>
								<c:otherwise>
									${reply.modify_date }
								</c:otherwise>
							</c:choose>
						</td>
						<td></td>
						<c:choose>
							<c:when test="${userIdx eq reply.user_idx }">
								<td class="modi_del" id="modi_del[${reply.idx }]" rowspan="2" style="text-align: right; vertical-align: top;">
									<a class="replyBtns" onclick="modifyBtn(${reply.idx });"><span>수정</span></a>&nbsp;&nbsp;&nbsp;
									<a class="replyBtns" onclick="deleteReply(${reply.idx})"><span>삭제</span></a>
								</td>
								<td class="modi_can" id="modi_can[${reply.idx }]" style="text-align: right; display: none;">
									<a class="replyBtns" onclick="modifyReply(${reply.idx})"><span>수정</span></a>&nbsp;&nbsp;&nbsp;
									<a class="replyBtns" onclick="modifyCancelBtn(${reply.idx });"><span>취소</span></a>
								</td>
							</c:when>
							<c:otherwise><td colspan="2"></td></c:otherwise>
						</c:choose>
					</tr>
					<tr style="height: 2rem;"></tr>
					<tr class="original_reply" id="original_reply[${reply.idx }]">
						<td colspan="3">${reply.content }</td>
						<td style="text-align: right;">
							<a class="replyBtns" id="re_reply"><span>답글</span></a></td>
					</tr>
					<tr class="modify_reply" id="modify_reply[${reply.idx }]" style="display: none;">
						<td colspan="4">
							<textarea class="reply_area" rows="2" cols="60" id="reply_modify" style="width:100%;">${reply.content }</textarea>
						</td>
					</tr>
				</tbody>
				<!-- 대댓글 -->
				<tfoot class="added_reply_section">
					<tr><td>대댓글</td></tr>
				</tfoot>
			</table>
			<hr>
		</c:forEach>
	</div>
	
</div>
</body>
</html>