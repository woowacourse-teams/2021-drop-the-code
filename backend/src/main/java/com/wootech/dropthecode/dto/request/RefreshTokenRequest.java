package com.wootech.dropthecode.dto.request;

public class RefreshTokenRequest {
    /**
     * 만료된 access token 갱신을 위한 refresh token
     */
    private String refreshToken;

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
