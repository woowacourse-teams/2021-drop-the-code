package com.wootech.dropthecode.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private String errorMessage;

    public ErrorResponse(String message) {
        this.errorMessage = message;
    }
}
