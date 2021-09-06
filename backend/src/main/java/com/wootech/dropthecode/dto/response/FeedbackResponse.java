package com.wootech.dropthecode.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedbackResponse {
    /**
     * 피드백 ID
     */
    private Long id;

    /**
     * 피드백 별점 수
     */
    private Integer star;

    /**
     * 피드백 내용
     */
    private String comment;

    /**
     * 피드백 작성한 유저 정보
     */
    private ProfileResponse studentProfile;

    @Builder
    public FeedbackResponse(Long id, Integer star, String comment, ProfileResponse studentProfile) {
        this.id = id;
        this.star = star;
        this.comment = comment;
        this.studentProfile = studentProfile;
    }
}
