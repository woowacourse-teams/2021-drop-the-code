package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Progress;

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

    public ReviewResponse() {
    }

    public ReviewResponse(Long id, String title, String content, Progress progress, ProfileResponse teacherProfile, ProfileResponse studentProfile) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.progress = progress;
        this.teacherProfile = teacherProfile;
        this.studentProfile = studentProfile;
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
}
