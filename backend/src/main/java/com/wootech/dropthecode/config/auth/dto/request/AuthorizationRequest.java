package com.wootech.dropthecode.config.auth.dto.request;

public class AuthorizationRequest {
    private String providerName;
    private String authorizationCode;

    public AuthorizationRequest() {
    }

    public String getProviderName() {
        return providerName;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }
}
