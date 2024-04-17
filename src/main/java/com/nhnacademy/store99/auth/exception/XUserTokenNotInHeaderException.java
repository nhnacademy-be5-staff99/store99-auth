package com.nhnacademy.store99.auth.exception;

import com.nhnacademy.store99.auth.common.exception.BadRequestException;

/**
 * logout header 에 X-USER-TOKEN 이 없을 시 발생하는 예외
 *
 * @author Ahyeon Song
 */
public class XUserTokenNotInHeaderException extends BadRequestException {
    public XUserTokenNotInHeaderException(String message) {
        super(message);
    }
}
