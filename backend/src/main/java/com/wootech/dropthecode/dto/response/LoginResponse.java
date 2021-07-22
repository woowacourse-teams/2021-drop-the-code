package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Role;

public class LoginResponse {
    /**
     * 사용자 id
     */
    private Long id;

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
     * 선생님 등록 여부(STUDENT, TEACHER)
     */
    private Role role;

    /**
     * 토큰 타입
     */
    private String tokenType;

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

    public LoginResponse(Long id, String name, String email, String imageUrl, Role role, String tokenType, String accessToken, String refreshToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
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

    public Role getRole() {
        return role;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
