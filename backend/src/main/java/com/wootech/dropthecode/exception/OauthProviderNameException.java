package com.wootech.dropthecode.exception;

import org.springframework.http.HttpStatus;

public class OauthProviderNameException extends DropTheCodeException {
    public OauthProviderNameException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
