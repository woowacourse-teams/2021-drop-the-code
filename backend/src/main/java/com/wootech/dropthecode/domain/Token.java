package com.wootech.dropthecode.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Token {
    private String value;
    private long expiredTime;

    public Token(String value, long expiredTime) {
        this.value = value;
        this.expiredTime = expiredTime;
    }
}
