package com.wootech.dropthecode.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedbackPaginationResponse {
    /**
     * 피드백 목록
     */
    private List<FeedbackResponse> feedbacks;

    /**
     * 총 페이지 수
     */
    private Integer pageCount;

    @Builder
    public FeedbackPaginationResponse(List<FeedbackResponse> feedbacks, Integer pageCount) {
        this.feedbacks = feedbacks;
        this.pageCount = pageCount;
    }
}
