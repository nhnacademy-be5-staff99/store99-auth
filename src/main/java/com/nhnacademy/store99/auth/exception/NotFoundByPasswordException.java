package com.nhnacademy.store99.auth.exception;

public class NotFoundByPasswordException extends RuntimeException {

    public NotFoundByPasswordException() {
        super();
    }

    public NotFoundByPasswordException(String message) {
        super(message);
    }
}
