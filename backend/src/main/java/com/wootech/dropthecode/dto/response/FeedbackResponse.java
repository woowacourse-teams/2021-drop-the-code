package com.wootech.dropthecode.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedbackResponse {
    /**
     * 피드백 ID
     */
    private final Long id;

    /**
     * 피드백 별점 수
     */
    private final Integer star;

    /**
     * 피드백 내용
     */
    private final String comment;

    @Builder
    public FeedbackResponse(Long id, Integer star, String comment) {
        this.id = id;
        this.star = star;
        this.comment = comment;
    }
}
