package com.wootech.dropthecode.config.auth.domain;

public class OauthProvider {
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String tokenUrl;
    private String userInfoUrl;

    public OauthProvider(OauthProperties.User user, OauthProperties.Provider provider) {
        this(user.getClientId(), user.getClientSecret(), user.getRedirectUri(), provider.getTokenUri(), provider.getUserInfoUri());
    }

    public OauthProvider(String clientId, String clientSecret, String redirectUrl, String tokenUrl, String userInfoUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.tokenUrl = tokenUrl;
        this.userInfoUrl = userInfoUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }
}
