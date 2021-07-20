package com.wootech.dropthecode.config.auth.dto.response;

public class LoginResponse {
    /**
     * 사용자 이름
     */
    private String name;

    /**
     * 사용자 이메일
     */
    private String email;

    /**
     * 사용자 프로필 이미지 주소
     */
    private String imageUrl;

    /**
     * access token
     */
    private String accessToken;

    /**
     * refresh token
     */
    private String refreshToken;

    public LoginResponse() {
    }

    public LoginResponse(String name, String email, String imageUrl, String accessToken, String refreshToken) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
