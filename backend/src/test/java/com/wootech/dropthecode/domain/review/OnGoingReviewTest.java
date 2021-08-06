package com.wootech.dropthecode.domain.review;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.exception.ReviewException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.builder.MemberBuilder.dummyMember;
import static com.wootech.dropthecode.builder.ReviewBuilder.dummyReview;
import static org.assertj.core.api.Assertions.assertThatCode;

public class OnGoingReviewTest {

    @DisplayName("Teacher Completed 상태 변경이 가능한 경우 - 성공")
    @Test
    void updateTeacherCompletedReview() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatCode(() -> new OnGoingReview(review).complete(1L)).doesNotThrowAnyException();
    }

    @DisplayName("Teacher Completed 상태 변경이 가능한 경우 - 실패")
    @Test
    void updateTeacherCompletedReviewFail() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.TEACHER_COMPLETED);

        assertThatCode(() -> new OnGoingReview(review)).isInstanceOf(ReviewException.class);
    }
}
