package com.wootech.dropthecode.exception;

import org.springframework.http.HttpStatus;

public class DropTheCodeException extends RuntimeException {
    private final HttpStatus httpStatus;

    public DropTheCodeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
