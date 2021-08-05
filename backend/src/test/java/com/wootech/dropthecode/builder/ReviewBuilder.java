package com.wootech.dropthecode.builder;

import java.time.LocalDateTime;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.dto.ReviewSummary;

public class ReviewBuilder {

    private ReviewBuilder() {
    }

    public static Review dummyReview(Member teacher, Member student, String title, String content, String prUrl, Long elapsedTime) {
        return Review.builder()
                     .teacher(teacher)
                     .student(student)
                     .title(title)
                     .content(content)
                     .prUrl(prUrl)
                     .elapsedTime(elapsedTime)
                     .build();
    }

    public static Review dummyReview(Member teacher, Member student, String title, String content, String prUrl, Long elapsedTime, Progress progress) {
        return Review.builder()
                     .teacher(teacher)
                     .student(student)
                     .title(title)
                     .content(content)
                     .prUrl(prUrl)
                     .elapsedTime(elapsedTime)
                     .progress(progress)
                     .build();
    }

    public static ReviewSummary dummyReviewSummary(Long id, String title, String content, Progress progress, Long teacherId, String teacherName,
                                                   String teacherImageUrl, Long studentId, String studentName, String studentImageUrl, String prUrl,
                                                   LocalDateTime createdAt) {
        return ReviewSummary.builder()
                            .id(id)
                            .title(title)
                            .content(content)
                            .progress(progress)
                            .teacherId(teacherId)
                            .teacherName(teacherName)
                            .teacherImageUrl(teacherImageUrl)
                            .studentId(studentId)
                            .studentName(studentName)
                            .studentImageUrl(studentImageUrl)
                            .prUrl(prUrl)
                            .createdAt(createdAt)
                            .build();
    }
}
