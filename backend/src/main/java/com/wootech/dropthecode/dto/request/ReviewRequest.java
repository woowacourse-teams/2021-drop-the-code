package com.wootech.dropthecode.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    public ReviewRequest() {
    }

    public ReviewRequest(Long studentId, Long teacherId, String title, String content, String prUrl) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.title = title;
        this.content = content;
        this.prUrl = prUrl;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPrUrl() {
        return prUrl;
    }
}
