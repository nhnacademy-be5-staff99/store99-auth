package com.nhnacademy.store99.auth.exception;

public class LoginDtoNotParsingException extends RuntimeException {

    public LoginDtoNotParsingException() {
        super("로그인 형식을 파싱할 수 없습니다.");
    }
}
