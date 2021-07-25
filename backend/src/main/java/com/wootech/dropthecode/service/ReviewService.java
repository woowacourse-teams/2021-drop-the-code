package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.ReviewSummary;
import com.wootech.dropthecode.dto.response.ReviewResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.repository.ReviewPagingAndSortingRepository;
import com.wootech.dropthecode.repository.ReviewRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewPagingAndSortingRepository repository;

    public ReviewService(ReviewRepository reviewRepository, ReviewPagingAndSortingRepository repository) {
        this.reviewRepository = reviewRepository;
        this.repository = repository;
    }

    public ReviewsResponse findStudentReview(Long id, Pageable pageable) {
        List<ReviewResponse> reviewResponses = repository.findAllByStudent_Id(id, pageable, ReviewSummary.class)
                                                               .stream()
                                                               .map(ReviewResponse::of)
                                                               .collect(Collectors.toList());
        return new ReviewsResponse(reviewResponses);
    }
}
