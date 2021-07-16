package com.wootech.dropthecode.config.auth.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2")
public class OauthProperties {
    private final Map<String, User> user = new HashMap<>();

    private final Map<String, Provider> provider = new HashMap<>();

    public Map<String, User> getUser() {
        return user;
    }

    public Map<String, Provider> getProvider() {
        return provider;
    }

    public static class User {
        private String clientId;
        private String clientSecret;
        private String redirectUri;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public void setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
        }
    }

    public static class Provider {
        private String tokenUri;
        private String userInfoUri;
        private String userNameAttribute;

        public String getTokenUri() {
            return tokenUri;
        }

        public void setTokenUri(String tokenUri) {
            this.tokenUri = tokenUri;
        }

        public String getUserInfoUri() {
            return userInfoUri;
        }

        public void setUserInfoUri(String userInfoUri) {
            this.userInfoUri = userInfoUri;
        }

        public String getUserNameAttribute() {
            return userNameAttribute;
        }

        public void setUserNameAttribute(String userNameAttribute) {
            this.userNameAttribute = userNameAttribute;
        }
    }
}
