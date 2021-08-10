package com.wootech.dropthecode.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorizationRequest {
    /**
     * oauth 서버 이름(ex. github, naver, google)
     */
    private String providerName;

    /**
     * URL 디코딩 한 Authorization Code
     */
    private String code;

    public AuthorizationRequest(String providerName, String code) {
        this.providerName = providerName;
        this.code = code;
    }
}
