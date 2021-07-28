package com.wootech.dropthecode.dto;

import java.time.LocalDateTime;

import com.wootech.dropthecode.domain.Progress;

public class ReviewSummary {
    private Long id;
    private String title;
    private String content;
    private Progress progress;
    private Long teacherId;
    private String teacherName;
    private String teacherImageUrl;
    private Long studentId;
    private String studentName;
    private String studentImageUrl;
    private String prUrl;
    private LocalDateTime createdAt;

    public ReviewSummary() {
    }

    public ReviewSummary(Long id, String title, String content, Progress progress, Long teacherId, String teacherName,
                         String teacherImageUrl, Long studentId, String studentName, String studentImageUrl, String prUrl,
                         LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.progress = progress;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherImageUrl = teacherImageUrl;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentImageUrl = studentImageUrl;
        this.prUrl = prUrl;
        this.createdAt = createdAt;
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

    public Long getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherImageUrl() {
        return teacherImageUrl;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentImageUrl() {
        return studentImageUrl;
    }

    public String getPrUrl() {
        return prUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
