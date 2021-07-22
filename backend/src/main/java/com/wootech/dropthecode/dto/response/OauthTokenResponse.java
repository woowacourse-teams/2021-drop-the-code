package com.wootech.dropthecode.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OauthTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    public OauthTokenResponse() {
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
}
