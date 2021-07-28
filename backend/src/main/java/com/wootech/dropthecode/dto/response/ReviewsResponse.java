package com.wootech.dropthecode.dto.response;

import java.util.List;

public class ReviewsResponse {
    /**
     * 리뷰 리스트
     */
    private List<ReviewResponse> reviews;

    /**
     * 전체 페이지 수
     */
    private int pageCount;

    public ReviewsResponse() {
    }

    public ReviewsResponse(List<ReviewResponse> reviews, int pageCount) {
        this.reviews = reviews;
        this.pageCount = pageCount;
    }

    public List<ReviewResponse> getReviews() {
        return reviews;
    }

    public int getPageCount() {
        return pageCount;
    }
}
