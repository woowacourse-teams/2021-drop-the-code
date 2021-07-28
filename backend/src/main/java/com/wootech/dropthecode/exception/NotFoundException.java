package com.wootech.dropthecode.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends DropTheCodeException {
    public NotFoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
