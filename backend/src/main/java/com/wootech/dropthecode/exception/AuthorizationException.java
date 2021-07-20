package com.wootech.dropthecode.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends DropTheCodeException {

    public AuthorizationException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
