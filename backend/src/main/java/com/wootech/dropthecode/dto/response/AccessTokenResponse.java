package com.wootech.dropthecode.dto.response;

public class AccessTokenResponse {
    /**
     * 갱신된 access Token
     */
    private String accessToken;

    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
