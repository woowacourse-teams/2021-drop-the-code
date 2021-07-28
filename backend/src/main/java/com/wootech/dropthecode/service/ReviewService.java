package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.dto.ReviewSummary;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;
import com.wootech.dropthecode.dto.response.ReviewResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.exception.NotFoundException;
import com.wootech.dropthecode.repository.ReviewRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public ReviewsResponse findStudentReview(Long id, ReviewSearchCondition condition, Pageable pageable) {
        Page<ReviewSummary> pageReviews = reviewRepository.searchPageByStudentId(id, condition, pageable);

        List<ReviewResponse> reviews = pageReviews.stream()
                                                  .map(ReviewResponse::from)
                                                  .collect(Collectors.toList());
        return new ReviewsResponse(reviews, pageReviews.getTotalPages());
    }

    @Transactional(readOnly = true)
    public ReviewsResponse findTeacherReview(Long id, ReviewSearchCondition condition, Pageable pageable) {
        Page<ReviewSummary> pageReviews = reviewRepository.searchPageByTeacherId(id, condition, pageable);

        List<ReviewResponse> reviews = pageReviews.stream()
                                                  .map(ReviewResponse::from)
                                                  .collect(Collectors.toList());
        return new ReviewsResponse(reviews, pageReviews.getTotalPages());
    }

    @Transactional(readOnly = true)
    public ReviewResponse findById(Long id) {
        ReviewSummary review = reviewRepository.findByReviewId(id)
                                               .orElseThrow(() -> new NotFoundException("존재하지 않는 리뷰입니다."));
        return ReviewResponse.from(review);
    }
}
