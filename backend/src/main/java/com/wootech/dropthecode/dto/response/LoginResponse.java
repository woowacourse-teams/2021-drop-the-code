package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
     * 사용자 github 주소
     */
    private String githubUrl;

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
