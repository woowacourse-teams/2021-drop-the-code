package com.wootech.dropthecode.exception;

import org.springframework.http.HttpStatus;

public class OauthTokenException extends DropTheCodeException {
    public OauthTokenException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
