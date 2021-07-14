package com.wootech.dropthecode.config.auth.dto;

public class LoginResponse {
    private String name;
    private String email;
    private String imageUrl;
    private String accessToken;
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
