package com.nhnacademy.store99.auth.secure_key_manager.exception;

public class SecureKeyMangerException extends RuntimeException {
    public static final String MESSAGE = "Secure Key Manager 오류 발생 : ";

    public SecureKeyMangerException(String message) {
        super(MESSAGE + message);
    }
}
