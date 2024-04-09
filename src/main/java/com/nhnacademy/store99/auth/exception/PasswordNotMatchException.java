package com.nhnacademy.store99.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class PasswordNotMatchException extends AuthenticationException {
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
