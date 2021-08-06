package com.wootech.dropthecode.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackSearchCondition {
    /**
     * Feedback을 작성한 유저의 ID
     */
    private Long studentId;

    /**
     * Feedback을 받은 리뷰어의 ID
     */
    private Long teacherId;

    @Builder
    public FeedbackSearchCondition(Long studentId, Long teacherId) {
        this.studentId = studentId;
        this.teacherId = teacherId;
    }
}
