package com.nhnacademy.store99.auth.exception;


public class LoginRequestNotPermissionException extends RuntimeException {
    public LoginRequestNotPermissionException(String message) {
        super(String.format("로그인 요청은 POST Method 만 가능. (Your Method : %s)", message));
    }
}
