package com.nhnacademy.store99.auth.exception;

import com.nhnacademy.store99.auth.common.exception.BadRequestException;

/**
 * @author Ahyeon Song
 */
public class LoginDtoNotParsingException extends BadRequestException {
    public LoginDtoNotParsingException(String message) {
        super(message);
    }
}
