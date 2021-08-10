package com.wootech.dropthecode.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequest {
    /**
     * 학생 id
     */
    @NotNull
    private Long studentId;
    /**
     * 선생님 id
     */
    @NotNull
    private Long teacherId;
    /**
     * 리뷰 요청 제목
     */
    @NotBlank
    private String title;
    /**
     * 리뷰 요청 내용
     */
    @NotBlank
    private String content;
    /**
     * 리뷰 요청 pr 링크
     */
    @NotBlank
    private String prUrl;

    @Builder
    public ReviewRequest(Long studentId, Long teacherId, String title, String content, String prUrl) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.title = title;
        this.content = content;
        this.prUrl = prUrl;
    }
}
