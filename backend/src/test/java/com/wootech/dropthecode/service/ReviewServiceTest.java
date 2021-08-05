package com.wootech.dropthecode.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.dto.ReviewSummary;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;
import com.wootech.dropthecode.dto.response.ReviewResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.exception.NotFoundException;
import com.wootech.dropthecode.repository.ReviewRepository;

import org.springframework.data.domain.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wootech.dropthecode.builder.ReviewBuilder.dummyReviewSummary;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@DisplayName("ReviewService Test")
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @DisplayName("member 아이디를 통해 해당 member가 받은 리뷰 목록 조회")
    void studentReview() {
        // given
        Long memberId = 2L;
        ReviewSearchCondition condition = new ReviewSearchCondition(Arrays.asList(Progress.ON_GOING, Progress.FINISHED), null);
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
        List<ReviewSummary> reviews = Arrays.asList(
                dummyReviewSummary(1L, "title1", "content1", Progress.ON_GOING,
                        1L, "teacher1", "s3://teacher1Img",
                        memberId, "student1", "s3://student1Img",
                        "prUrl1", LocalDateTime.now()),
                dummyReviewSummary(2L, "title2", "content2", Progress.ON_GOING,
                        3L, "teacher2", "s3://teacher2Img",
                        memberId, "student1", "s3://student1Img",
                        "prUrl1", LocalDateTime.now()),
                dummyReviewSummary(3L, "title3", "content3", Progress.FINISHED,
                        4L, "teacher3", "s3://teacher3Img",
                        memberId, "student1", "s3://student1Img",
                        "prUrl1", LocalDateTime.now())
        );
        Page<ReviewSummary> pageReviews = new PageImpl<>(reviews, pageable, 10);
        given(reviewRepository.searchPageByStudentId(memberId, condition, pageable))
                .willReturn(pageReviews);

        // when
        ReviewsResponse response = reviewService.findStudentReview(memberId, condition, pageable);

        // then
        assertThat(response.getPageCount()).isEqualTo(4);
        assertThat(response.getReviews()).hasSize(3);
        assertThat(response.getReviews()).extracting("title")
                                         .contains("title1", "title2", "title3");
    }

    @Test
    @DisplayName("member 아이디를 통해 해당 member가 한 리뷰 목록 조회")
    void teacherReview() {
        // given
        Long memberId = 1L;
        ReviewSearchCondition condition = new ReviewSearchCondition(Arrays.asList(Progress.ON_GOING, Progress.FINISHED), null);
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
        List<ReviewSummary> reviews = Arrays.asList(
                new ReviewSummary(1L, "title1", "content1", Progress.ON_GOING,
                        memberId, "teacher1", "s3://teacher1Img",
                        2L, "student1", "s3://student1Img",
                        "prUrl1", LocalDateTime.now()),
                new ReviewSummary(2L, "title2", "content2", Progress.ON_GOING,
                        memberId, "teacher1", "s3://teacher1Img",
                        3L, "student2", "s3://student2Img",
                        "prUrl1", LocalDateTime.now()),
                new ReviewSummary(3L, "title3", "content3", Progress.FINISHED,
                        memberId, "teacher1", "s3://teacher1Img",
                        4L, "student3", "s3://student3Img",
                        "prUrl1", LocalDateTime.now())
        );
        Page<ReviewSummary> pageReviews = new PageImpl<>(reviews, pageable, 10);
        given(reviewRepository.searchPageByTeacherId(memberId, condition, pageable))
                .willReturn(pageReviews);

        // when
        ReviewsResponse response = reviewService.findTeacherReview(memberId, condition, pageable);

        // then
        assertThat(response.getPageCount()).isEqualTo(4);
        assertThat(response.getReviews()).hasSize(3);
        assertThat(response.getReviews()).extracting("title")
                                         .contains("title1", "title2", "title3");
    }

    @Test
    @DisplayName("리뷰 아이디로 리뷰 조회 - 성공")
    void findByIdSuccess() {
        // given
        Long reviewId = 1L;
        ReviewSummary reviewSummary = new ReviewSummary(
                1L, "title1", "content1", Progress.FINISHED,
                1L, "teacher", "s3://image1",
                2L, "student", "s3://image2",
                "prUrl", LocalDateTime.now()
        );
        given(reviewRepository.findByReviewId(reviewId)).willReturn(Optional.of(reviewSummary));

        // when
        ReviewResponse result = reviewService.findReviewSummaryById(reviewId);

        // then
        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(ReviewResponse.from(reviewSummary));
    }

    @Test
    @DisplayName("리뷰 아이디로 리뷰 조회 - 실패")
    void findByIdFail() {
        // given
        Long reviewId = 1L;
        given(reviewRepository.findByReviewId(reviewId)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> reviewService.findReviewSummaryById(reviewId))
                .isInstanceOf(NotFoundException.class);
    }
}
