package com.wootech.dropthecode.exception;

import org.springframework.http.HttpStatus;

public class OauthException extends DropTheCodeException {
    public OauthException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
