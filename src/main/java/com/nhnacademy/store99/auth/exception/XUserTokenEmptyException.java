package com.nhnacademy.store99.auth.exception;

import com.nhnacademy.store99.auth.common.exception.BadRequestException;

/**
 * X-USER-TOKEN 의 값이 비어있을 시 발생하는 예외
 *
 * @author Ahyeon Song
 */
public class XUserTokenEmptyException extends BadRequestException {
    public XUserTokenEmptyException(String message) {
        super(message);
    }
}
