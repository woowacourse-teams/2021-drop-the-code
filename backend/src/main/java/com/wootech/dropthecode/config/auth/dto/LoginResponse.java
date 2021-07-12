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
}
