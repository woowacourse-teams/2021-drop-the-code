package com.wootech.dropthecode.integration.cache;

import java.time.LocalDateTime;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.domain.review.Review;
import com.wootech.dropthecode.repository.MemberRepository;
import com.wootech.dropthecode.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.builder.MemberBuilder.dummyMember;
import static com.wootech.dropthecode.builder.ReviewBuilder.dummyReview;
import static org.assertj.core.api.Assertions.assertThat;

class ReviewCachingTest extends CachingTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Review review;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();

        Member teacher = dummyMember("1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, LocalDateTime.now());
        Member student = dummyMember("1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, LocalDateTime.now());
        memberRepository.save(teacher);
        memberRepository.save(student);

        review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);
        reviewRepository.save(review);
    }

    @Test
    @DisplayName("리뷰 캐시 등록 테스트")
    void reviewCacheableTest() {

        // when
        reviewRepository.findById(1L);
        reviewRepository.findById(1L);
        reviewRepository.findById(1L);

        // then
        final long reviewHitCount = sessionFactory.getStatistics().getCacheRegionStatistics("review").getHitCount();
        assertThat(reviewHitCount).isEqualTo(2);
    }

    @Test
    @DisplayName("리뷰 업데이트 시 캐시 제거 테스트")
    void reviewCacheEvictWhenReviewUpdateTest() {

        // given
        reviewRepository.findById(1L);

        // when
        review.updateElapsedTime();
        reviewRepository.save(review);

        // then
        final boolean existsCache = sessionFactory.getCache().containsEntity(Member.class, 1L);
        assertThat(existsCache).isFalse();
    }
}
