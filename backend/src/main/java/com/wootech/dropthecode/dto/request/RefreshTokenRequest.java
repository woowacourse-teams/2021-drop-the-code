package com.wootech.dropthecode.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenRequest {
    /**
     * 만료된 access token 갱신을 위한 refresh token
     */
    private String refreshToken;

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
