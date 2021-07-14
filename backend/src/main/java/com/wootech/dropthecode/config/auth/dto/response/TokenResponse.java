package com.wootech.dropthecode.config.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    public TokenResponse() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getTokenWithType() {
        return tokenType + " " + accessToken;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", scope='" + scope + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
