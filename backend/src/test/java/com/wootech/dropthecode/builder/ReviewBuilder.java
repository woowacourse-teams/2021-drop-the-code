package com.wootech.dropthecode.builder;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Review;

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
}
