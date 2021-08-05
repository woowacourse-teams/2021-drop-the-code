package com.wootech.dropthecode.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewsResponse {
    /**
     * 리뷰 리스트
     */
    private final List<ReviewResponse> reviews;

    /**
     * 전체 페이지 수
     */
    private final int pageCount;

    @Builder
    public ReviewsResponse(List<ReviewResponse> reviews, int pageCount) {
        this.reviews = reviews;
        this.pageCount = pageCount;
    }
}
