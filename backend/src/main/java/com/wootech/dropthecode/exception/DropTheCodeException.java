package com.wootech.dropthecode.exception;

import org.springframework.http.HttpStatus;

public class DropTheCodeException extends RuntimeException {
    private HttpStatus httpStatus;

    public DropTheCodeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
