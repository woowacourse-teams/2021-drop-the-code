package com.wootech.dropthecode.integration.review;

import com.wootech.dropthecode.domain.*;
import com.wootech.dropthecode.dto.request.ReviewRequest;
import com.wootech.dropthecode.repository.MemberRepository;
import com.wootech.dropthecode.repository.ReviewRepository;
import com.wootech.dropthecode.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    @DisplayName("리뷰 수정 동작 확인")
    void reviewUpdate() {
        // given
        Member teacher = new Member("1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER);
        Member student = new Member("2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT);
        memberRepository.save(teacher);
        Member savedStudent = memberRepository.save(student);
        LoginMember loginMember = new LoginMember(savedStudent.getId());

        Review review = new Review(teacher, student, "original title", "original content", "original pr link", 0L, Progress.ON_GOING);
        Review originalReview = reviewRepository.save(review);

        ReviewRequest request =
                new ReviewRequest(1L, 2L, "new title", "new content", "new pr link");

        // when
        reviewService.updateReview(loginMember, originalReview.getId(), request);
        Review updatedReview = reviewRepository.findById(originalReview.getId()).get();

        // then
        assertThat(updatedReview).extracting("title").isEqualTo("new title");
        assertThat(updatedReview).extracting("content").isEqualTo("new content");
        assertThat(updatedReview).extracting("prUrl").isEqualTo("new pr link");
    }
}
