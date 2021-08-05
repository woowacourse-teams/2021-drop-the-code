package com.wootech.dropthecode.dto.response;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    public boolean isNotValid() {
        return Objects.isNull(accessToken);
    }
}
