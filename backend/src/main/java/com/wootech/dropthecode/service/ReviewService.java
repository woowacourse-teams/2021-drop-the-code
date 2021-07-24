package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.ReviewSummary;
import com.wootech.dropthecode.dto.response.ReviewResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.repository.ReviewRepository;

import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewsResponse findStudentReview(Long id) {
        List<ReviewResponse> reviewResponses = reviewRepository.findAllByStudent_Id(id, ReviewSummary.class)
                                                               .stream()
                                                               .map(ReviewResponse::of)
                                                               .collect(Collectors.toList());
        return new ReviewsResponse(reviewResponses);
    }
}
