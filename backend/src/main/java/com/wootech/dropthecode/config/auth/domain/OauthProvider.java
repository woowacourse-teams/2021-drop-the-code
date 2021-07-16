package com.wootech.dropthecode.config.auth.domain;

public class OauthProvider {
    private String clientId;
    private String clientSecret;
    private String tokenUrl;
    private String userInfoUrl;

    public OauthProvider(OauthProperties.User value, OauthProperties.Provider provider) {
        this(value.getClientId(), value.getClientSecret(), provider.getTokenUri(), provider.getUserInfoUri());
    }

    public OauthProvider(String clientId, String clientSecret, String tokenUrl, String userInfoUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenUrl = tokenUrl;
        this.userInfoUrl = userInfoUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }
}
