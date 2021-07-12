package com.wootech.dropthecode.config.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenRequest {
    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    private String code;

    public TokenRequest() {
    }

    public TokenRequest(String clientId, String clientSecret, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCode() {
        return code;
    }
}
