package com.wootech.dropthecode.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FeedbackRequest {
    /**
     * 평점
     */
    @NotNull
    private Integer star;

    /**
     * 코멘트
     */
    @NotBlank
    private String comment;

    public FeedbackRequest() {
    }

    public FeedbackRequest(Integer star, String comment) {
        this.star = star;
        this.comment = comment;
    }

    public Integer getStar() {
        return star;
    }

    public String getComment() {
        return comment;
    }
}
