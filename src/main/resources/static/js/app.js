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
	notify('error', message || '요청을 처리하지 못했습니다.');
}

/*
 * 알림(토스트): 오른쪽 아래에 잠깐 떴다가 저절로 사라진다. 브라우저 기본 alert 대신 사용.
 *   notify('success' | 'warning' | 'error', '메시지')
 */
const NOTIFY_ICONS = {
	success: 'bi-check-circle-fill',
	warning: 'bi-exclamation-circle-fill',
	error: 'bi-x-circle-fill'
};

function notify(type, message) {
	let stack = document.querySelector('.toast-stack');
	if (!stack) {
		stack = document.createElement('div');
		stack.className = 'toast-stack';
		stack.setAttribute('role', 'status');
		stack.setAttribute('aria-live', 'polite');
		document.body.append(stack);
	}
	const toast = document.createElement('div');
	toast.className = 'sb-toast ' + type;
	const icon = document.createElement('i');
	icon.className = 'bi ' + (NOTIFY_ICONS[type] || NOTIFY_ICONS.success);
	const text = document.createElement('span');
	text.textContent = message;
	toast.append(icon, text);
	stack.append(toast);
	// 오류는 더 오래, 긴 메시지는 읽을 시간만큼 더 오래 보여준다
	const duration = Math.max(type === 'error' ? 4000 : 2500, message.length * 100);
	setTimeout(function () {
		toast.classList.add('hide');
		setTimeout(function () { toast.remove(); }, 200);
	}, duration);
}

/* 페이지를 이동하거나 새로고침한 뒤에 알림을 띄운다 (이동 직후 알림이 사라지지 않게) */
notify.next = function (type, message) {
	try {
		sessionStorage.setItem('sb-notify', JSON.stringify({ type: type, message: message }));
	} catch (e) {
		// 저장소를 쓸 수 없으면 알림을 생략한다
	}
};

/*
 * 확인창: 화면 가운데에 띄우고, 확인을 누르면 true를 돌려준다. 브라우저 기본 confirm 대신 사용.
 *   confirmDialog({ title, text, okText, danger }).then(function (ok) { if (ok) { ... } });
 *   danger: true면 빨간 버튼 + 휴지통 아이콘 (삭제·탈퇴 등)
 */
function confirmDialog(options) {
	const opts = Object.assign({ title: '', text: '', okText: '확인', danger: false }, options);
	let modalEl = document.getElementById('sbConfirm');
	if (!modalEl) {
		modalEl = document.createElement('div');
		modalEl.id = 'sbConfirm';
		modalEl.className = 'modal fade sb-confirm';
		modalEl.tabIndex = -1;
		modalEl.innerHTML =
			'<div class="modal-dialog modal-dialog-centered">' +
			'  <div class="modal-content">' +
			'    <div class="modal-body">' +
			'      <div class="confirm-icon"><i class="bi"></i></div>' +
			'      <h5 class="confirm-title"></h5>' +
			'      <p class="confirm-text"></p>' +
			'    </div>' +
			'    <div class="modal-footer">' +
			'      <button type="button" class="btn btn-light" data-bs-dismiss="modal">취소</button>' +
			'      <button type="button" class="btn confirm-ok"></button>' +
			'    </div>' +
			'  </div>' +
			'</div>';
		document.body.append(modalEl);
	}
	modalEl.querySelector('.confirm-title').textContent = opts.title;
	const textEl = modalEl.querySelector('.confirm-text');
	textEl.textContent = opts.text;
	textEl.hidden = !opts.text;
	modalEl.querySelector('.confirm-icon').classList.toggle('danger', opts.danger);
	modalEl.querySelector('.confirm-icon i').className = 'bi ' + (opts.danger ? 'bi-trash' : 'bi-question-lg');
	const okButton = modalEl.querySelector('.confirm-ok');
	okButton.textContent = opts.okText;
	okButton.className = 'btn confirm-ok ' + (opts.danger ? 'btn-danger' : 'btn-primary');

	const modal = bootstrap.Modal.getOrCreateInstance(modalEl);
	return new Promise(function (resolve) {
		let confirmed = false;
		okButton.onclick = function () {
			confirmed = true;
			modal.hide();
		};
		modalEl.addEventListener('shown.bs.modal', function () { okButton.focus(); }, { once: true });
		modalEl.addEventListener('hidden.bs.modal', function () { resolve(confirmed); }, { once: true });
		modal.show();
	});
}

$(function () {
	/* 이전 페이지에서 예약한 알림 */
	try {
		const pending = sessionStorage.getItem('sb-notify');
		if (pending) {
			sessionStorage.removeItem('sb-notify');
			const data = JSON.parse(pending);
			notify(data.type, data.message);
		}
	} catch (e) {
		// 저장소를 쓸 수 없으면 무시
	}

	/* 목록 화면의 '전체 선택' 체크박스 */
	$('#allCheckBtn').on('change', function () {
		$("input:checkbox[name='each']").prop('checked', this.checked);
	});
	$("input:checkbox[name='each']").on('change', function () {
		const all = $("input:checkbox[name='each']").length;
		const checked = $("input:checkbox[name='each']:checked").length;
		$('#allCheckBtn').prop('checked', all === checked);
	});

	/*
	 * data-confirm 속성이 있는 폼은 제출 전에 확인창을 띄운다.
	 *   data-confirm="제목"  data-confirm-text="부가 설명"  data-confirm-ok="버튼 글자"  data-confirm-danger
	 * 체크박스(each)로 고르는 폼은 하나도 고르지 않았으면 안내만 띄운다.
	 */
	$('form[data-confirm]').on('submit', function (e) {
		const form = this;
		e.preventDefault();
		if ($(form).find("input:checkbox[name='each']").length && !$(form).find("input:checkbox[name='each']:checked").length) {
			notify('warning', '삭제할 항목을 선택하세요.');
			return;
		}
		confirmDialog({
			title: form.dataset.confirm,
			text: form.dataset.confirmText,
			okText: form.dataset.confirmOk,
			danger: form.hasAttribute('data-confirm-danger')
		}).then(function (ok) {
			if (ok) {
				// 기본 submit을 직접 호출해서 이 확인 처리가 다시 실행되지 않게 한다
				HTMLFormElement.prototype.submit.call(form);
			}
		});
	});

	/* 게시판 등급이 부족한 메뉴 */
	$('[data-denied]').on('click', function (e) {
		e.preventDefault();
		notify('warning', this.dataset.denied);
	});
});
