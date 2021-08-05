package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
    /**
     * 사용자 id
     */
    private final Long id;

    /**
     * 사용자 이름
     */
    private final String name;

    /**
     * 사용자 이메일
     */
    private final String email;

    /**
     * 사용자 프로필 이미지 주소
     */
    private final String imageUrl;

    /**
     * 사용자 github 주소
     */
    private final String githubUrl;

    /**
     * 선생님 등록 여부(STUDENT, TEACHER)
     */
    private final Role role;

    /**
     * 토큰 타입
     */
    private final String tokenType;

    /**
     * access token
     */
    private final String accessToken;

    /**
     * refresh token
     */
    private final String refreshToken;

    @Builder
    public LoginResponse(Long id, String name, String email, String imageUrl, String githubUrl, Role role, String tokenType, String accessToken, String refreshToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.githubUrl = githubUrl;
        this.role = role;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
