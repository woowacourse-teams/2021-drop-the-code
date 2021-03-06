package com.wootech.dropthecode.controller.auth.util;

import java.util.HashMap;
import java.util.Map;

import com.wootech.dropthecode.domain.oauth.OauthProperties;
import com.wootech.dropthecode.domain.oauth.OauthProvider;

public class OauthAdapter {

    private OauthAdapter() {}

    public static Map<String, OauthProvider> getOauthProviders(OauthProperties properties) {
        Map<String, OauthProvider> oauthProvider = new HashMap<>();

        properties.getUser().forEach((key, value) -> oauthProvider.put(key,
                new OauthProvider(value, properties.getProvider().get(key))));
        return oauthProvider;
    }
}
