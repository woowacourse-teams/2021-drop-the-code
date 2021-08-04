package com.wootech.dropthecode.domain;

import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.exception.ReviewException;

import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
public class ReviewTest {

    @DisplayName("Teacher Completed 상태 변경이 가능한 경우 - 성공")
    @Test
    void updateTeacherCompletedReview() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review onGoingReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatCode(() -> onGoingReview.completeProgress(1L))
                .doesNotThrowAnyException();
    }

    @DisplayName("Teacher Completed 상태 변경이 가능한 경우 - 실패")
    @Test
    void updateTeacherCompletedReviewFail() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review completedReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.TEACHER_COMPLETED);

        assertThatThrownBy(() -> completedReview.completeProgress(1L))
                .isInstanceOf(ReviewException.class);
    }

    @DisplayName("Finished 상태 변경이 가능한 경우 - 성공")
    @Test
    void updateFinishedReview() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review completedReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.TEACHER_COMPLETED);

        assertThatCode(() -> completedReview.finishProgress(2L))
                .doesNotThrowAnyException();
    }

    @DisplayName("Finished 상태 변경이 가능한 경우 - 실패")
    @Test
    void updateFinishedReviewFail() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review onGoingReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatThrownBy(() -> onGoingReview.finishProgress(2L))
                .isInstanceOf(ReviewException.class);
    }

    @DisplayName("Student Id, Teacher Id 값 Validation - 성공")
    @Test
    void validateMemberId() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review completedReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.TEACHER_COMPLETED);

        assertThatCode(() -> {
            completedReview.validateMemberIdAsStudent(student.getId());
            completedReview.validateMemberIdAsTeacher(teacher.getId());
        }).doesNotThrowAnyException();
    }

    @DisplayName("Finished 상태 변경이 가능한 경우 - 학생 ID 실패")
    @Test
    void validateStudentIdFail() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review onGoingReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatThrownBy(() -> onGoingReview.validateMemberIdAsStudent(teacher.getId()))
                .isInstanceOf(AuthorizationException.class);
    }

    @DisplayName("Finished 상태 변경이 가능한 경우 - 선생님 ID 실패")
    @Test
    void validateTeacherIdFail() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review onGoingReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatThrownBy(() -> onGoingReview.validateMemberIdAsTeacher(student.getId()))
                .isInstanceOf(AuthorizationException.class);
    }

    @Test
    @DisplayName("리뷰 내용 수정")
    void update() {
        // given
        Member teacher = new Member(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = new Member(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = new Review(teacher, student, "original title", "original content", "original pr link", 0L, Progress.ON_GOING);

        // when
        review.update("new title", "new content", "new pr link");

        // then
        assertThat(review).extracting("title").isEqualTo("new title");
        assertThat(review).extracting("content").isEqualTo("new content");
        assertThat(review).extracting("prUrl").isEqualTo("new pr link");
    }
}
