package com.wootech.dropthecode.exception;

import org.springframework.http.HttpStatus;

public class OauthTokenRequestException extends DropTheCodeException {
    public OauthTokenRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
