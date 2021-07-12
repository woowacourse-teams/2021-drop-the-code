package com.wootech.dropthecode.config.auth;

import java.util.Arrays;

public enum OauthProvider {
    GITHUB("https://github.com/login/oauth/access_token");

    private final String tokenRequestUrl;

    OauthProvider(String tokenRequestUrl) {
        this.tokenRequestUrl = tokenRequestUrl;
    }

    public static String getTokenRequestUrl(String providerName) {
        String capitalProviderName = providerName.toUpperCase();
        return Arrays.stream(values())
                     .filter(provider -> provider.name().equals(capitalProviderName))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new).tokenRequestUrl;
    }
}
