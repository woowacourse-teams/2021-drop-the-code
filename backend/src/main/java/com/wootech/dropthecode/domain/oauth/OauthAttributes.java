package com.wootech.dropthecode.domain.oauth;

import java.util.Arrays;
import java.util.Map;

public enum OauthAttributes {
    GITHUB("github") {
        @Override
        public UserProfile of(Map<String, Object> attributes) {
            return new UserProfile(
                    String.valueOf(attributes.get("id")),
                    (String) attributes.get("name"),
                    (String) attributes.get("email"),
                    (String) attributes.get("avatar_url")
            );
        }
    },
    NAVER("naver") {
        @Override
        public UserProfile of(Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return new UserProfile(
                    (String) response.get("id"),
                    (String) response.get("name"),
                    (String) response.get("email"),
                    (String) response.get("profile_image")
            );
        }
    },
    GOOGLE("google") {
        @Override
        public UserProfile of(Map<String, Object> attributes) {
            return new UserProfile(
                    String.valueOf(attributes.get("sub")),
                    (String) attributes.get("name"),
                    (String) attributes.get("email"),
                    (String) attributes.get("picture")
            );
        }
    };

    private final String providerName;

    OauthAttributes(String name) {
        this.providerName = name;
    }

    public static UserProfile extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values())
                     .filter(provider -> providerName.equals(provider.providerName))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new)
                     .of(attributes);
    }

    public abstract UserProfile of(Map<String, Object> attributes);
}
