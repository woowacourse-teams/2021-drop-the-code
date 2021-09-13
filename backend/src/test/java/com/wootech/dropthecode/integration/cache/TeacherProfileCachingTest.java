package com.wootech.dropthecode.integration.cache;

import java.time.LocalDateTime;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.repository.TeacherProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.builder.MemberBuilder.dummyMember;
import static com.wootech.dropthecode.builder.TeacherProfileBuilder.dummyTeacherProfile;
import static org.assertj.core.api.Assertions.assertThat;

class TeacherProfileCachingTest extends CachingTest {

    @Autowired
    private TeacherProfileRepository teacherProfileRepository;

    private TeacherProfile airTeacher;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();

        final Member air = dummyMember("1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER);
        airTeacher = dummyTeacherProfile("title1", "content1", 10, air, LocalDateTime.now());
        teacherProfileRepository.save(airTeacher);
    }

    @Test
    @DisplayName("리뷰어 캐시 등록 테스트")
    void teacherCacheableTest() {

        // when
        teacherProfileRepository.findById(1L);
        teacherProfileRepository.findById(1L);
        teacherProfileRepository.findById(1L);

        // then
        final long teacherProfileCacheHitCount = sessionFactory.getStatistics().getCacheRegionStatistics("teacherProfile").getHitCount();
        assertThat(teacherProfileCacheHitCount).isEqualTo(2);
    }

    @Test
    @DisplayName("리뷰어 업데이트 시 캐시 제거 테스트")
    void teacherCacheEvictWhenTeacherUpdateTest() {

        // given
        teacherProfileRepository.findById(1L);

        // when
        airTeacher.deleteWithMember();
        teacherProfileRepository.save(airTeacher);

        // then
        final boolean existsCache = sessionFactory.getCache().containsEntity(Member.class, 1L);
        assertThat(existsCache).isFalse();
    }
}
