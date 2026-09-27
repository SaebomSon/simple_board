package com.newsp.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 컨트롤러 파라미터에 로그인 회원({@link com.newsp.user.User})을 DB의 최신 정보로 주입한다.
 * (@ModelAttribute를 쓰면 요청 파라미터가 바인딩되어 level 등을 조작할 수 있으므로 사용하지 않는다.)
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
