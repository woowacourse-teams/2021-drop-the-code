package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.config.OauthConfig;
import com.wootech.dropthecode.domain.oauth.InMemoryProviderRepository;
import com.wootech.dropthecode.domain.oauth.OauthProvider;
import com.wootech.dropthecode.exception.OauthTokenRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(OauthConfig.class)
@DataJpaTest
public class InMemoryProviderRepositoryTest {

    @Autowired
    private InMemoryProviderRepository inMemoryProviderRepository;

    @ParameterizedTest
    @DisplayName("지원하는 provider 찾기")
    @ValueSource(strings = {"github", "google", "naver"})
    void findSupportedProvider(String providerName) {
        // given
        // when
        OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

        // then
        assertThat(provider).isNotNull();
    }

    @Test
    @DisplayName("지원하지 않는 provider 찾기")
    void findNotSupportedProvider() {
        // given
        // when
        // then
        assertThatThrownBy(() -> inMemoryProviderRepository.findByProviderName("kakao"))
                .isInstanceOf(OauthTokenRequestException.class);
    }
}
