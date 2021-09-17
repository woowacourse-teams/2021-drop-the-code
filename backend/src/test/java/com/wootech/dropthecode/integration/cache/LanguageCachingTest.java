package com.wootech.dropthecode.integration.cache;

import com.wootech.dropthecode.repository.LanguageRepository;
import com.wootech.dropthecode.util.DatabaseDefaultInsert;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LanguageCachingTest extends CachingTest {

    @Autowired
    private DatabaseDefaultInsert databaseDefaultInsert;

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    @DisplayName("전체 언어 목록 캐싱 테스트")
    void languagesCacheableTest() {

        // given
        databaseDefaultInsert.insertDefaultLanguageAndSkill();

        // when
        languageRepository.findAll();
        languageRepository.findAll();
        languageRepository.findAll();

        // then
        final long languageHitCount = sessionFactory.getStatistics().getQueryCacheHitCount();
        assertThat(languageHitCount).isEqualTo(2);
    }
}
