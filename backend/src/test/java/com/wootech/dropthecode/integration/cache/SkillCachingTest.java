package com.wootech.dropthecode.integration.cache;

import com.wootech.dropthecode.repository.SkillRepository;
import com.wootech.dropthecode.util.DatabaseDefaultInsert;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SkillCachingTest extends CachingTest {

    @Autowired
    private DatabaseDefaultInsert databaseDefaultInsert;

    @Autowired
    private SkillRepository skillRepository;

    @Test
    @DisplayName("전체 스킬 목록 캐싱 테스트")
    void skillsCacheableTest() {

        // given
        databaseDefaultInsert.insertDefaultLanguageAndSkill();

        // when
        skillRepository.findAll();
        skillRepository.findAll();
        skillRepository.findAll();

        // then
        final long skillHitCount = sessionFactory.getStatistics().getQueryCacheHitCount();
        assertThat(skillHitCount).isEqualTo(2);
    }
}
