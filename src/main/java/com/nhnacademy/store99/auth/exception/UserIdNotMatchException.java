package com.nhnacademy.store99.auth.exception;

/**
 * Authentication 의 userId 와 Redis 의 userId 가 일치하지 않을 시 발생하는 예외
 *
 * @author Ahyeon Song
 */
public class UserIdNotMatchException extends RuntimeException {
    public UserIdNotMatchException(String message) {
        super(message);
    }
}
