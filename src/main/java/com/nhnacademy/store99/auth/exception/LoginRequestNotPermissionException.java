package com.nhnacademy.store99.auth.exception;

/**
 * 로그인 요청 메소드가 POST 가 아닐 시 예외 처리
 *
 * @author Ahyeon Song
 */
public class LoginRequestNotPermissionException extends RuntimeException {
    public LoginRequestNotPermissionException(String message) {
        super(String.format("로그인 요청은 POST Method 만 가능. (Your Method : %s)", message));
    }
}
