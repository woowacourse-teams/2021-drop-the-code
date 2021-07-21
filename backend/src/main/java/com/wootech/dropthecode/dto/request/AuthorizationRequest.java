package com.wootech.dropthecode.dto.request;

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

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
