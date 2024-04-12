package com.nhnacademy.store99.auth.exception;

/**
 * @author Ahyeon Song
 */
public class LoginDtoNotParsingException extends RuntimeException {
    public LoginDtoNotParsingException(String message) {
        super(message);
    }
}
