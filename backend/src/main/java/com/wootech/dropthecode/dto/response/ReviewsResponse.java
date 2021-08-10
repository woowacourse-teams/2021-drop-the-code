package com.wootech.dropthecode.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewsResponse {
    /**
     * 리뷰 리스트
     */
    private List<ReviewResponse> reviews;

    /**
     * 전체 페이지 수
     */
    private int pageCount;

    @Builder
    public ReviewsResponse(List<ReviewResponse> reviews, int pageCount) {
        this.reviews = reviews;
        this.pageCount = pageCount;
    }
}
