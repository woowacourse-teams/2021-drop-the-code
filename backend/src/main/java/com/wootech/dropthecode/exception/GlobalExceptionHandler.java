package com.wootech.dropthecode.exception;

import java.util.Objects;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleBindingException(BindingResult bindingResult) {
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException e) {
        String message = e.getParameterName() + " parameter is missing";
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(polishErrorMessage(e)));
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorResponse> handlePropertyReferenceException(PropertyReferenceException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("[" + e.getPropertyName() + "](은)는 없는 정렬 조건입니다."));
    }

    @ExceptionHandler(DropTheCodeException.class)
    public ResponseEntity<ErrorResponse> dropTheCodeExceptionHandler(DropTheCodeException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
    }

    private String polishErrorMessage(BindException e) {
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(fieldError.getField());
        builder.append("](은)는 ");
        builder.append(fieldError.getDefaultMessage());
        builder.append(" 입력된 값은: [");
        builder.append(fieldError.getRejectedValue());
        builder.append("] 입니다.");
        return builder.toString();
    }
}
