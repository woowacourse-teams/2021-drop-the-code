package com.wootech.dropthecode.dto.response;

import java.time.LocalDateTime;

import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.dto.ReviewSummary;
import com.wootech.dropthecode.util.LocalDateTimeToArray;

public class ReviewResponse {
    /**
     * 리뷰 id
     */
    private Long id;
    /**
     * 리뷰 제목
     */
    private String title;
    /**
     * 리뷰 내용
     */
    private String content;
    /**
     * 리뷰 진행 상태
     */
    private Progress progress;
    /**
     * 선생님 프로필
     */
    private ProfileResponse teacherProfile;
    /**
     * 학생 프로필
     */
    private ProfileResponse studentProfile;
    /**
     * PR 링크
     */
    private String prUrl;
    /**
     * 리뷰 생성일
     */
    private Integer[] createdAt;

    public ReviewResponse() {
    }

    public ReviewResponse(Long id, String title, String content, Progress progress, ProfileResponse teacherProfile, ProfileResponse studentProfile, String prUrl, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.progress = progress;
        this.teacherProfile = teacherProfile;
        this.studentProfile = studentProfile;
        this.prUrl = prUrl;
        this.createdAt = LocalDateTimeToArray.convert(createdAt);
    }

    public static ReviewResponse from(ReviewSummary review) {
        return new ReviewResponse(review.getId(), review.getTitle(), review.getContent(), review.getProgress(),
                ProfileResponse.of(review.getTeacherId(), review.getTeacherName(), review.getTeacherImageUrl()),
                ProfileResponse.of(review.getStudentId(), review.getStudentName(), review.getStudentImageUrl()),
                review.getPrUrl(), review.getCreatedAt());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Progress getProgress() {
        return progress;
    }

    public ProfileResponse getTeacherProfile() {
        return teacherProfile;
    }

    public ProfileResponse getStudentProfile() {
        return studentProfile;
    }

    public String getPrUrl() {
        return prUrl;
    }

    public Integer[] getCreatedAt() {
        return createdAt;
    }
}
