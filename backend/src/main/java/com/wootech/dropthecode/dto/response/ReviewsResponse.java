package com.wootech.dropthecode.dto.response;

import java.util.List;

public class ReviewsResponse {
    /**
     * 리뷰 리스트
     */
    private List<ReviewResponse> reviews;

    public ReviewsResponse() {
    }

    public ReviewsResponse(List<ReviewResponse> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewResponse> getReviews() {
        return reviews;
    }
}
