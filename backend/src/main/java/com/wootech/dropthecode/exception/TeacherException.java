package com.wootech.dropthecode.exception;

import org.springframework.http.HttpStatus;

public class TeacherException extends DropTheCodeException {
    public TeacherException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
