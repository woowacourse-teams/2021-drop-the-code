package com.wootech.dropthecode.integration.cache;

import java.time.LocalDateTime;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.domain.review.Review;
import com.wootech.dropthecode.repository.MemberRepository;
import com.wootech.dropthecode.repository.ReviewRepository;
import com.wootech.dropthecode.repository.TeacherProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.builder.MemberBuilder.dummyMember;
import static com.wootech.dropthecode.builder.ReviewBuilder.dummyReview;
import static com.wootech.dropthecode.builder.TeacherProfileBuilder.dummyTeacherProfile;
import static org.assertj.core.api.Assertions.assertThat;

class MemberCachingTest extends CachingTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeacherProfileRepository teacherProfileRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Member student;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();

        student = dummyMember("1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, LocalDateTime.now());
        final Member air = dummyMember("1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER);
        final TeacherProfile airTeacher = dummyTeacherProfile("title1", "content1", 10, air, LocalDateTime.now());

        memberRepository.save(student);
        teacherProfileRepository.save(airTeacher);

        final Review review = dummyReview(air, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);
        reviewRepository.save(review);

        air.addReviewAsTeacher(review);
        teacherProfileRepository.save(airTeacher);
    }

    @Test
    @DisplayName("멤버 캐시 등록 테스트 - 멤버, 선생님, 리뷰 모두 캐싱")
    void memberCacheableTest() {

        // when
        memberRepository.findById(2L);
        memberRepository.findById(2L);
        memberRepository.findById(2L);

        // then
        final long memberHitCount = sessionFactory.getStatistics().getCacheRegionStatistics("member").getHitCount();
        assertThat(memberHitCount).isEqualTo(2);
    }

    @Test
    @DisplayName("멤버 업데이트 시 캐시 갱신 테스트")
    void memberCacheEvictWhenMemberUpdateTest() {

        // given
        memberRepository.findById(1L);

        // when
        student.setRole(Role.TEACHER);
        memberRepository.save(student);

        // then
        final boolean existsCache = sessionFactory.getCache().containsEntity(Member.class, 1L);
        assertThat(existsCache).isFalse();
    }
}
