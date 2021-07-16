package com.wootech.dropthecode.config.auth.dto.request;

public class AuthorizationRequest {
    /**
     * oauth 서버 이름(ex. github, naver, google)
     */
    private String providerName;

    /**
     * URL 디코딩 한 Authorization Code
     */
    private String code;

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(String providerName, String code) {
        this.providerName = providerName;
        this.code = code;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getCode() {
        return code;
    }
}
