package com.wootech.dropthecode.dto.response;

import lombok.Getter;

@Getter
public class AccessTokenResponse {
    /**
     * 갱신된 access Token
     */
    private final String accessToken;

    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
