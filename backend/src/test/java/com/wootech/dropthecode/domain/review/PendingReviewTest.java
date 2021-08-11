package com.wootech.dropthecode.domain.review;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.exception.ReviewException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.builder.MemberBuilder.dummyMember;
import static com.wootech.dropthecode.builder.ReviewBuilder.dummyReview;
import static org.assertj.core.api.Assertions.assertThatCode;

class PendingReviewTest {

    @DisplayName("사용자가 리뷰를 취소한 경우 - 성공")
    @Test
    void cancelReview() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.PENDING);

        assertThatCode(() -> new PendingReview(review).cancel(2L)).doesNotThrowAnyException();
    }

    @DisplayName("시용자가 리뷰를 취소한 경우 - PENDING 이 아닐 경우 실패")
    @Test
    void cancelReviewFailIfProgressIsNotPending() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatCode(() -> new PendingReview(review)).isInstanceOf(ReviewException.class);
    }

    @DisplayName("시용자가 리뷰를 취소한 경우 - 요청 MemberID 가 리뷰의 학생 ID가 아닐 경우 실패")
    @Test
    void cancelReviewFailIfMemberIdIsInvalid() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.PENDING);

        assertThatCode(() -> new PendingReview(review).cancel(3L)).isInstanceOf(AuthorizationException.class);
    }

    @DisplayName("선생님이 리뷰를 거절한 경우 - 성공")
    @Test
    void denyReview() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.PENDING);

        assertThatCode(() -> new PendingReview(review).deny(1L)).doesNotThrowAnyException();
    }

    @DisplayName("선생님이 리뷰를 거절한 경우 - PENDING 이 아닐 경우 실패")
    @Test
    void denyReviewFailIfProgressIsNotPending() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatCode(() -> new PendingReview(review)).isInstanceOf(ReviewException.class);
    }

    @DisplayName("선생님이 리뷰를 거절한 경우 - 요청 MemberID 가 리뷰의 선생님 ID가 아닐 경우 실패")
    @Test
    void denyReviewFailIfMemberIdIsInvalid() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.PENDING);

        assertThatCode(() -> new PendingReview(review).deny(3L)).isInstanceOf(AuthorizationException.class);
    }

    @DisplayName("선생님이 리뷰를 수락한 경우 - 성공")
    @Test
    void acceptReview() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.PENDING);

        assertThatCode(() -> new PendingReview(review).accept(1L)).doesNotThrowAnyException();
    }

    @DisplayName("시용자가 리뷰를 수락한 경우 - PENDING 이 아닐 경우 실패")
    @Test
    void acceptReviewFailIfProgressIsNotPending() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatCode(() -> new PendingReview(review)).isInstanceOf(ReviewException.class);
    }

    @DisplayName("시용자가 리뷰를 수락한 경우 - 요청 MemberID 가 리뷰의 선생님 ID가 아닐 경우 실패")
    @Test
    void acceptReviewFailIfMemberIdIsInvalid() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.PENDING);

        assertThatCode(() -> new PendingReview(review).accept(3L)).isInstanceOf(AuthorizationException.class);
    }
}
