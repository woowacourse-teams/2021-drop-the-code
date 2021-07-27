package com.wootech.dropthecode.exception;

import org.springframework.http.HttpStatus;

public class ReviewException extends DropTheCodeException {
    public ReviewException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
