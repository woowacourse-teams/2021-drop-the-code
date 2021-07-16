package com.wootech.dropthecode.config.auth.config;

import java.util.Map;

import com.wootech.dropthecode.config.auth.domain.InMemoryProviderRepository;
import com.wootech.dropthecode.config.auth.domain.OauthProperties;
import com.wootech.dropthecode.config.auth.domain.OauthProvider;
import com.wootech.dropthecode.config.auth.util.OauthAdapter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
public class OauthConfig {

    private final OauthProperties properties;

    public OauthConfig(OauthProperties properties) {
        this.properties = properties;
    }

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        Map<String, OauthProvider> providers = OauthAdapter.getOauthProviders(properties);
        return new InMemoryProviderRepository(providers);
    }
}
