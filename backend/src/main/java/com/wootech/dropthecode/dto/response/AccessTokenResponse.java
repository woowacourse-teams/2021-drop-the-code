package com.wootech.dropthecode.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessTokenResponse {
    /**
     * 갱신된 access Token
     */
    private String accessToken;

    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
