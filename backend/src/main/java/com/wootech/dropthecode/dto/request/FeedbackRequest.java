package com.wootech.dropthecode.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class FeedbackRequest {
    /**
     * 1부터 5까지의 평점
     */
    @NotNull
    @Range(min = 1, max = 5)
    private Integer star;

    /**
     * 코드 리뷰에 대한 코멘트
     */
    @NotBlank
    private String comment;

    public FeedbackRequest(Integer star, String comment) {
        this.star = star;
        this.comment = comment;
    }
}
