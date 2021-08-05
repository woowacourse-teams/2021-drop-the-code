package com.wootech.dropthecode.dto.response;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OauthTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("error")
    String error;

    @JsonProperty("error_description")
    String errorDescription;

    @JsonProperty("error_uri")
    String errorUri;

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

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public String getErrorUri() {
        return errorUri;
    }

    public boolean isNotValid() {
        return Objects.isNull(accessToken);
    }
}
