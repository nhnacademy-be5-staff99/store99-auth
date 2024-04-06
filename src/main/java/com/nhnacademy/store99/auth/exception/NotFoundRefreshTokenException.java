package com.nhnacademy.store99.auth.exception;

public class NotFoundRefreshTokenException extends RuntimeException {

    public NotFoundRefreshTokenException() {
    }

    public NotFoundRefreshTokenException(String message) {
        super(message);
    }

    public NotFoundRefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
