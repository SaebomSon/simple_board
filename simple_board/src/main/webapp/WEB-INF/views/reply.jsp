<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reply List</title>
<!-- CSS -->
<link rel="stylesheet" href="resources/css/reply.css">
<script>
	$(function() {
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
	function insertReply() {
		const boardIdx = ${info.idx};
		const userIdx = ${userIdx};
		const reply = document.getElementById('textbox').value;
		const data = {"board_idx" : boardIdx, "user_idx" : userIdx, "content" : reply};
		if (reply == '') {
			alert("댓글을 입력하세요.");
			document.getElementById('textbox').focus();
		} else {
			$.ajax({
				url : "ajax/insertReply",
				type : "post",
				data : JSON.stringify(data),
				dataType : "json",
				contentType : "application/json;charsest=UTF-8",
				success : function(result) {
					if (result.result == "ok") {
						document.getElementById('textbox').value = "";
						getReplyList();
						console.log("댓글 작성 성공");
					}
				},
				error : function(e) {
					console.log("댓글 작성 실패");
				}
			})
		}
	}

	// 댓글 불러오는 ajax
	function getReplyList() {
		var replyListSection = document.getElementsByClassName("cont_comment");
		var formData = new FormData();
		$.ajax({
			url : "getReply?idx=${info.idx}",
			type : "get",
			data : formData,
			dataType : "text",
			contentType : false,
			processData : false,
			success : function(result) {
				var html = $('<div>').html(result);
				var replyContent = html.find("div.cont_comment").html();
				var replyCount = html.find("span.reply_count").html();

				$(".cont_comment").html(replyContent);
				$(".reply_count").html(replyCount);
			},
			error : function(e) {
				console.log("fail getting replyList");
			}
		})
	}

	// 댓글 수정 영역 show
	function modifyBtn(idx) {
		var modifySection = document.getElementById("mention_modify[" + idx + "]");
		var modifyText = document.getElementById("my_option[" + idx + "]");

		modifySection.style.display = '';
		modifyText.style.display = 'none';
	}

	// 댓글 수정하는 ajax
	function modifyComment(idx) {
		const boardIdx = ${info.idx};
		const userIdx = ${userIdx};
		var modifyComment = document.getElementById("textbox_modify[" + idx + "]").value;
		console.log(modifyComment);
		const data = {"idx" : idx, "board_idx" : boardIdx, "user_idx" : userIdx, "content" : modifyComment};

		$.ajax({
			url : "ajax/modifyReply",
			type : "post",
			data : JSON.stringify(data),
			dataType : "json",
			contentType : "application/json;charsest=UTF-8",
			success : function(result) {
				if (result.result == "modifyOk") {
					getReplyList();
					console.log("댓글 수정 성공");
				}
			},
			error : function(e) {
				console.log("댓글 수정 실패");
			}
		})
	}

	// 댓글 삭제하는 ajax
	function deleteComment(idx) {
		const boardIdx = ${info.idx};
		const userIdx = ${userIdx};
		const data = {"idx" : idx, "board_idx" : boardIdx, "user_idx" : userIdx};

		$.ajax({
			url : "ajax/deleteReply",
			type : "delete",
			data : JSON.stringify(data),
			dataType : "json",
			contentType : "application/json;charsest=UTF-8",
			success : function(result) {
				if (result.result == "deleteOk") {
					getReplyList();
					console.log("댓글 삭제 성공");
				}
			},
			error : function(e) {
				console.log("댓글 삭제 실패");
			}
		})
	}

	// 답글 버튼 click시
	function insertMention(idx) {
		var mention = document.getElementById("mention[" + idx + "]");
		var mentionInsert = document.getElementById("mention_insert");
		
		if(mentionInsert.innerHTML == '답글'){
			mention.style.display = '';
			mentionInsert.innerHTML = '답글 접기';
		}else{
			mention.style.display = 'none';
			mentionInsert.innerHTML = '답글';

			}
	}

</script>
</head>
<body>
	<b>댓글&nbsp;&nbsp;&nbsp; <span class="reply_count">${info.reply_count }</span></b>
	<hr>
	<div id="comment-list" class="cont_comment">
		<div class="comment_view" id="comment_view">
			<ul class="list_comment">
			<c:forEach var="reply" items="${replyInfo }" varStatus="i">
			<input type="hidden" id="replyIdx" value="${reply.idx }"/>
				<li id="_cmt-1390093-1" class="" data-seq="1" data-parseq="0"
					data-is-hidden="false" data-nickname="${reply.nickname }" data-image-url=""
					data-image-size="0" data-is-emoticon="false">
					<div class="comment_section">
						<div class="profile_thumb">
							<img src="//img1.daumcdn.net/thumb/C72x72/?fname=https://t1.daumcdn.net/cafe_image/img_profile/profile_cob.png"
								alt="" width="36" height="36" class="img_thumb">
						</div>
						<div class="comment_info">
							<div class="comment_post">
								<div class="profile_info">
									<div class="opt_more_g">
										<!-- 닉네임 클릭시 open_layer 추가 -->
										<img class="ico_role"
											src="//t1.daumcdn.net/cafe_image/cf_img2/bbs2/roleicon/2/a_level_25.gif"
											alt=""> <span data-grpid="1D7bO"
											data-rolecode="25"
											data-nickname="${reply.nickname }"
											data-is-mine="false" data-enc-userid="ekS3VEoMSs90"
											class="txt_name">${reply.nickname }</span>
									</div>
									<c:if test="${i.index eq 0}">
										<span class="tag_comment" tabindex="0">첫댓글</span>
									</c:if>
									<c:choose>
										<c:when test="${reply.written_date eq reply.modify_date}">
											<span class="txt_date" tabindex="0">${reply.written_date }</span>
										</c:when>
										<c:otherwise>
											<span class="txt_date" tabindex="0">${reply.modify_date }</span>
										</c:otherwise>
									</c:choose>
									<!-- 오늘 작성한 댓글일 경우 -->
									<span class="ico_bbs ico_new" tabindex="0">새글</span>
								</div>
								<div class="box_post">
									<p class="desc_info">
										<span class="original_comment" tabindex="0">${reply.content }</span>
									</p>
									
								</div>
								<div id="_cmt_button-1390093-1" class="comment_more">
									<a onclick="insertMention(${reply.idx });" class="link_write" 
									id="mention_insert" style="cursor: pointer;">답글</a>
								</div>
								<c:if test="${userIdx eq reply.user_idx }">
									<div class="comment_more_my_option" id="my_option[${reply.idx }]">
										<a class="menu_item" id="modify_comment" onclick="modifyBtn(${reply.idx});"><span>수정</span></a>&nbsp;&nbsp;&nbsp;
										<a class="menu_item" id="delete_comment" onclick="deleteComment(${reply.idx });"><span>삭제</span></a>
									</div>
								</c:if>
							</div><!-- comment_post end -->
							<!--  댓글 수정 눌렀을 경우 -->
							<div class="text_write_g comment_write_modify" id="mention_modify[${reply.idx }]" style="display:none;">
								<div class="inner_text_write">
									<div class="box_textarea">
										<textarea id="textbox_modify[${reply.idx }]" maxlength="600" style="height: 86px;">${reply.content }</textarea>
									</div>
									<div class="wrap_menu">
										<div class="area_r">
											<div class="btn_group">
												<button class="btn_g full_type1 modify_button" onclick="modifyComment(${reply.idx});" style="font-size: 13px;">등록</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- 답글 -->
							<div class="text_write_g comment_write" id="mention[${reply.idx }]" style="display:none;">
								<div class="inner_text_write">
									<span class="mention_nickname" style="font-size: 12px;">@${reply.nickname }</span>
									<div class="box_textarea">
										<textarea placeholder="댓글을 작성해주세요." id="textbox_mention" maxlength="600" style="height: 86px;"></textarea>
									</div>
									<div class="wrap_menu">
										<div class="area_r">
											<div class="btn_group">
												<button class="btn_g full_type1 confirm_button" onclick="" style="font-size: 13px;">등록</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div><!-- comment_info -->
					</div>
				</li>
				</c:forEach>

			</ul>
		</div>
		<div id="comment-paging"
			class="simple_paging light-theme simple-pagination"
			style="display: block;">
			<ul>
				<li class="disabled"><span class="current prev"><span
						class="ico_prev"><span class="sr_only">이전 목록으로 이동</span></span></span></li>
				<li class="active"><span class="current">1</span></li>
				<li class="disabled"><span class="current next"><span
						class="ico_next"><span class="sr_only">다음 목록으로 이동</span></span></span></li>
			</ul>
		</div>
		<div class="text_write_g comment_write">
			<div class="inner_text_write">
				<div class="box_textarea">
					<textarea placeholder="댓글을 작성해주세요." id="textbox" maxlength="600" style="height: 86px;"></textarea>
				</div>
				<div class="wrap_menu">
					<div class="area_r">
						<div class="btn_group">
							<button class="btn_g full_type1 confirm_button" onclick="insertReply();" style="font-size: 13px;">등록</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>