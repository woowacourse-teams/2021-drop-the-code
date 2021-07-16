package com.wootech.dropthecode.config.auth.dto.request;

public class AuthorizationRequest {
    private String providerName;
    private String code;

    public AuthorizationRequest() {
    }

    public String getProviderName() {
        return providerName;
    }

    public String getCode() {
        return code;
    }
}
