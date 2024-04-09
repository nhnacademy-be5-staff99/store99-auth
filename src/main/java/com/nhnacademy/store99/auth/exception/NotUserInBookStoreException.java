package com.nhnacademy.store99.auth.exception;

import com.nhnacademy.store99.auth.common.exception.NotFoundException;

public class NotUserInBookStoreException extends NotFoundException {
    public NotUserInBookStoreException(String email) {
        super(String.format("User not found (UserEmail: %s)", email));
    }
}
