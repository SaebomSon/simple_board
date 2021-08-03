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
		var modifyText = document.getElementById("modifyText[" + idx + "]");

		if(modifyText.innerHTML == '수정'){
			modifySection.style.display = '';
			modifyText.innerHTML = '취소';
			}
		else{
			modifySection.style.display = 'none';
			modifyText.innerHTML = '수정';
			}
	}

	// 댓글 수정하는 ajax
	function modifyComment(idx) {
		const boardIdx = ${info.idx};
		const userIdx = ${userIdx};
		var modifyComment = document.getElementById("textbox_modify[" + idx + "]").value;
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
		if(confirm("댓글을 삭제하시겠습니까?")){
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
		}	//if
	}

	// 답글 버튼 click시
	function clickInsertMention(idx) {
		var mention = document.getElementById("mention[" + idx + "]");
		var mentionInsert = document.getElementById("mention_insert");
		
		if(mentionInsert.innerHTML == '답글'){
			mention.style.display = '';
			mentionInsert.innerHTML = '답글 접기';
			//document.getElementById("textbox_mention["+idx+"]").focus();
		}else{
			mention.style.display = 'none';
			mentionInsert.innerHTML = '답글';
			}
	}

	// 대댓글 작성하기
	function insertMention(idx){
		var mention = document.getElementById("textbox_mention["+idx+"]").value;
		const userIdx = ${userIdx};
		console.log(mention);
		// idx : 모댓글의 idx
		var data = {"idx": idx, "user_idx": userIdx, "content": mention};
		if (mention == '') {
			alert("댓글을 입력하세요.");
			document.getElementById("textbox_mention["+idx+"]").focus();
		} else {
			$.ajax({
				url : "ajax/insertMention",
				type : "post",
				data : JSON.stringify(data),
				dateType: "json",
				contentType : "application/json;charsest=UTF-8",
				success : function(result) {
					if (result.result == "insertOk") {
						getReplyList();
						console.log("대댓글 작성 성공");
					}
				},
				error : function(e) {
					console.log("대댓글 작성 실패");
				}
			})
		}
	}

	/*
	// 대댓글 수정하는 ajax
	function modifyMention(idx) {
		const boardIdx = ${info.idx};
		const userIdx = ${userIdx};
		var modifyComment = document.getElementById("textbox_modify[" + idx + "]").value;
		console.log(modifyComment);
		const data = {"idx" : idx, "board_idx" : boardIdx, "user_idx" : userIdx, "content" : modifyComment};

		$.ajax({
			url : "ajax/modifyMention",
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

	// 대댓글 삭제하는 ajax
	function deleteMention(idx) {
		const boardIdx = ${info.idx};
		const userIdx = ${userIdx};
		const data = {"idx" : idx, "board_idx" : boardIdx, "user_idx" : userIdx};

		$.ajax({
			url : "ajax/deleteMention",
			type : "delete",
			data : JSON.stringify(data),
			dataType : "json",
			contentType : "application/json;charsest=UTF-8",
			success : function(result) {
				if(result.result == "deleteOk") {
					getReplyList();
					console.log("댓글 삭제 성공");
				}
			},
			error : function(e) {
				console.log("댓글 삭제 실패");
			}
		})
	}*/

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
					<c:if test="${reply.reply_depth eq 0 }">
					<!-- 모댓글 -->
						<li class="parent_reply" data-depth="0" data-parent="${reply.parent_reply_idx }" data-is-hidden="false" data-nickname="${reply.nickname }">
							<div class="comment_section">
								<!-- 프로필 추후 수정 -->
								<div class="profile_thumb">
									<img src="//img1.daumcdn.net/thumb/C72x72/?fname=https://t1.daumcdn.net/cafe_image/img_profile/profile_cob.png"
									width="36" height="36" class="img_thumb">
								</div>
								<div class="comment_info">
									<div class="comment_post">
										<div class="profile_info">
											<div class="opt_more_g">
												<!-- 닉네임 클릭시 open_layer 추가 -->
												<img class="ico_role" src="${reply.level_image }">
												<span data-grpid="1D7bO" data-nickname="${reply.nickname }" class="txt_name">${reply.nickname }</span>
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
											<c:if test="${today < reply.written_date }">
												<span class="ico_bbs ico_new" tabindex="0">새글</span>
											</c:if>
										</div>
										<div class="box_post">
											<p class="desc_info">
												<span class="original_comment" tabindex="0">${reply.content }</span>
											</p>
										</div>
										<div class="comment_more">
											<a onclick="clickInsertMention(${reply.idx });" class="link_write" 
											id="mention_insert" style="cursor: pointer;">답글</a>
										</div>
										<c:if test="${userIdx eq reply.user_idx }">
											<div class="comment_more_my_option" id="my_option[${reply.idx }]">
												<a class="menu_item" id="modify_comment" onclick="modifyBtn(${reply.idx});"><span id="modifyText[${reply.idx }]">수정</span></a>&nbsp;&nbsp;&nbsp;
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
											<!-- <span class="mention_nickname" style="font-size: 12px;">@${reply.nickname }</span> -->
											<div class="box_textarea">
												<textarea placeholder="댓글을 작성해주세요." id="textbox_mention[${reply.idx }]" maxlength="600" style="height: 86px;"></textarea>
											</div>
											<div class="wrap_menu">
												<div class="area_r">
													<div class="btn_group">
														<button class="btn_g full_type1 confirm_button" onclick="insertMention(${reply.idx})" style="font-size: 13px;">등록</button>
													</div>
											</div>
											</div>
										</div>
									</div>
								</div><!-- comment_info -->
							</div>
						</li>
						</c:if>
						<!-- 답글 -->
						<c:if test="${reply.reply_depth eq 1 }">
							<li class="reply_section" data-depth="1" data-parent="${reply.parent_reply_idx }" data-is-hidden="false" data-nickname="${reply.nickname }">
								<div class="comment_section">
									<!-- 프로필 추후 수정 -->
									<div class="profile_thumb">
										<img src="//img1.daumcdn.net/thumb/C72x72/?fname=https://t1.daumcdn.net/cafe_image/img_profile/profile_cob.png"
										width="36" height="36" class="img_thumb">
									</div>
									<div class="comment_info">
										<div class="comment_post">
											<div class="profile_info">
												<div class="opt_more_g">
													<!-- 닉네임 클릭시 open_layer 추가 -->
													<img class="ico_role" src="${reply.level_image }">
													<span data-grpid="1D7bO" data-nickname="${reply.nickname }" class="txt_name">${reply.nickname }</span>
												</div>
												<c:choose>
													<c:when test="${reply.written_date eq reply.modify_date}">
														<span class="txt_date" tabindex="0">${reply.written_date }</span>
													</c:when>
													<c:otherwise>
														<span class="txt_date" tabindex="0">${reply.modify_date }</span>
													</c:otherwise>
												</c:choose>
												<!-- 오늘 작성한 댓글일 경우 -->
												<c:if test="${today < reply.written_date }">
													<span class="ico_bbs ico_new" tabindex="0">새글</span>
												</c:if>
											</div>
											<div class="box_post">
												<p class="desc_info">
													<span class="original_comment" tabindex="0">${reply.content }</span>
												</p>
												
											</div>
											<div class="comment_more">
												<a onclick="clickInsertMention(${reply.idx });" class="link_write" 
												id="mention_insert" style="cursor: pointer;">답글</a>
											</div>
											<c:if test="${userIdx eq reply.user_idx }">
												<div class="comment_more_my_option" id="my_option[${reply.idx }]">
													<a class="menu_item" id="modify_comment" onclick="modifyBtn(${reply.idx});"><span id="modifyText[${reply.idx }]">수정</span></a>&nbsp;&nbsp;&nbsp;
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
													<textarea placeholder="댓글을 작성해주세요." id="textbox_mention[${reply.idx }]" maxlength="600" style="height: 86px;"></textarea>
												</div>
												<div class="wrap_menu">
													<div class="area_r">
														<div class="btn_group">
															<button class="btn_g full_type1 confirm_button" onclick="insertMention(${reply.idx})" style="font-size: 13px;">등록</button>
														</div>
												</div>
												</div>
											</div>
										</div>
									</div><!-- comment_info -->
								</div>
							</li>
						</c:if>
						<!-- 대댓글의 답글 -->
						<c:if test="${reply.reply_depth > 1 }">
							<li class="reply_section" data-parent="${reply.parent_reply_idx }" data-is-hidden="false" data-nickname="${reply.nickname }">
								<div class="comment_section">
									<!-- 프로필 추후 수정 -->
									<div class="profile_thumb">
										<img src="//img1.daumcdn.net/thumb/C72x72/?fname=https://t1.daumcdn.net/cafe_image/img_profile/profile_cob.png"
										width="36" height="36" class="img_thumb">
									</div>
									<div class="comment_info">
										<div class="comment_post">
											<div class="profile_info">
												<div class="opt_more_g">
													<!-- 닉네임 클릭시 open_layer 추가 -->
													<img class="ico_role" src="${reply.level_image }">
													<span data-grpid="1D7bO" data-nickname="${reply.nickname }" class="txt_name">${reply.nickname }</span>
												</div>
												<c:choose>
													<c:when test="${reply.written_date eq reply.modify_date}">
														<span class="txt_date" tabindex="0">${reply.written_date }</span>
													</c:when>
													<c:otherwise>
														<span class="txt_date" tabindex="0">${reply.modify_date }</span>
													</c:otherwise>
												</c:choose>
												<!-- 오늘 작성한 댓글일 경우 -->
												<c:if test="${today < reply.written_date }">
													<span class="ico_bbs ico_new" tabindex="0">새글</span>
												</c:if>
											</div>
											<div class="box_post">
												<p class="desc_info">
													<span class="mention_nickname" style="font-size: 12px;"><b>@${reply.nickname }</b></span>
													<span class="original_comment" tabindex="0">${reply.content }</span>
												</p>
												
											</div>
											<div class="comment_more">
												<a onclick="clickInsertMention(${reply.idx });" class="link_write" 
												id="mention_insert" style="cursor: pointer;">답글</a>
											</div>
											<c:if test="${userIdx eq reply.user_idx }">
												<div class="comment_more_my_option" id="my_option[${reply.idx }]">
													<a class="menu_item" id="modify_comment" onclick="modifyBtn(${reply.idx});"><span id="modifyText[${reply.idx }]">수정</span></a>&nbsp;&nbsp;&nbsp;
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
													<textarea placeholder="댓글을 작성해주세요." id="textbox_mention[${reply.idx }]" maxlength="600" style="height: 86px;"></textarea>
												</div>
												<div class="wrap_menu">
													<div class="area_r">
														<div class="btn_group">
															<button class="btn_g full_type1 confirm_button" onclick="insertMention(${reply.idx})" style="font-size: 13px;">등록</button>
														</div>
												</div>
												</div>
											</div>
										</div>
									</div><!-- comment_info -->
								</div>
							</li>
						</c:if>
				</c:forEach>
			</ul>
		</div>
		<!-- 페이징 
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
		-->
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