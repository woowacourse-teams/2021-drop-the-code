package com.wootech.dropthecode.domain.oauth;

import java.util.HashMap;
import java.util.Map;

import com.wootech.dropthecode.exception.OauthException;

public class InMemoryProviderRepository {
    private final Map<String, OauthProvider> providers;

    public InMemoryProviderRepository(Map<String, OauthProvider> providers) {
        this.providers = new HashMap<>(providers);
    }

    public OauthProvider findByProviderName(String name) {
        validateProviderNameExists(name);
        return providers.get(name);
    }

    private void validateProviderNameExists(String name) {
        if (!providers.containsKey(name)) {
            throw new OauthException("유효하지 않은 Oauth Provider입니다.");
        }
    }
}
