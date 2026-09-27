/* 모든 페이지 공통: ajax 요청에 CSRF 토큰을 붙인다 (Spring Security). */
(function () {
	const token = document.querySelector('meta[name="_csrf"]');
	const header = document.querySelector('meta[name="_csrf_header"]');
	if (token && header && window.jQuery) {
		$.ajaxSetup({
			beforeSend: function (xhr) {
				xhr.setRequestHeader(header.content, token.content);
			}
		});
	}
})();

/* JSON ajax 헬퍼 */
function sendJson(method, url, data) {
	return $.ajax({
		url: url,
		type: method,
		data: data === undefined ? undefined : JSON.stringify(data),
		contentType: 'application/json;charset=UTF-8',
		dataType: 'json'
	});
}

function alertError(xhr) {
	const message = xhr.responseJSON && xhr.responseJSON.message;
	alert(message || '요청을 처리하지 못했습니다.');
}

/* 목록 화면의 '전체 선택' 체크박스 */
$(function () {
	$('#allCheckBtn').on('change', function () {
		$("input:checkbox[name='each']").prop('checked', this.checked);
	});
	$("input:checkbox[name='each']").on('change', function () {
		const all = $("input:checkbox[name='each']").length;
		const checked = $("input:checkbox[name='each']:checked").length;
		$('#allCheckBtn').prop('checked', all === checked);
	});
	/* data-confirm 속성이 있는 폼은 제출 전에 확인 */
	$('form[data-confirm]').on('submit', function () {
		return confirm(this.dataset.confirm);
	});
	/* 게시판 등급이 부족한 메뉴 */
	$('[data-denied]').on('click', function (e) {
		e.preventDefault();
		alert(this.dataset.denied);
	});
});
