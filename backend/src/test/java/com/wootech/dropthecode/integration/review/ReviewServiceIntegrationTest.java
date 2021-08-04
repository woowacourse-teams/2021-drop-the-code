package com.wootech.dropthecode.integration.review;

import java.util.Optional;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.exception.ReviewException;
import com.wootech.dropthecode.repository.MemberRepository;
import com.wootech.dropthecode.repository.ReviewRepository;
import com.wootech.dropthecode.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("리뷰 요청 취소 동작 확인 - Pending 상태")
    void cancelReview() {
        // given
        Member teacher = new Member("1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER);
        Member student = new Member("2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT);
        memberRepository.save(teacher);
        memberRepository.save(student);

        Review review = new Review(teacher, student, "original title", "original content", "original pr link", 0L, Progress.PENDING);
        Review savedReview = reviewRepository.save(review);

        // when
        reviewService.cancelRequest(savedReview.getId());
        Optional<Review> foundReview = reviewRepository.findById(savedReview.getId());

        // then
        assertThat(foundReview.isPresent()).isEqualTo(false);
    }

    @ParameterizedTest
    @DisplayName("리뷰 요청 취소 동작 확인 - Pending이 아닌 경우")
    @EnumSource(value = Progress.class, names = {"DENIED", "ON_GOING", "TEACHER_COMPLETED", "FINISHED"})
    void cancelReview(Progress progress) {
        // given
        Member teacher = new Member("1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER);
        Member student = new Member("2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT);
        memberRepository.save(teacher);
        memberRepository.save(student);

        Review review = new Review(teacher, student, "original title", "original content", "original pr link", 0L, progress);
        Review savedReview = reviewRepository.save(review);

        // when
        // then
        assertThatThrownBy(() -> reviewService.cancelRequest(savedReview.getId()))
                .isInstanceOf(ReviewException.class);

        Optional<Review> foundReview = reviewRepository.findById(savedReview.getId());
        assertThat(foundReview.isPresent()).isEqualTo(true);
    }
}
