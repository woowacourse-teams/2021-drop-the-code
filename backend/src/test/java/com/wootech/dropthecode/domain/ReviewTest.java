package com.wootech.dropthecode.domain;

import com.wootech.dropthecode.dto.request.AuthorizationRequest;
import com.wootech.dropthecode.exception.ReviewException;

import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
public class ReviewTest {

    @Test
    @DisplayName("Teacher Completed 상태 변경이 가능한 경우 - 성공")
    void updateTeacherCompletedReview() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.STUDENT, null);

        Review onGoingReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);
        Review completedReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.TEACHER_COMPLETED);

        assertThatCode(onGoingReview::setTeacherCompleteProgress)
                .doesNotThrowAnyException();
    }

    @DisplayName("Teacher Completed 상태 변경이 가능한 경우 - 실패")
    void updateTeacherCompletedReviewFail() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.STUDENT, null);

        Review completedReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.TEACHER_COMPLETED);

        assertThatThrownBy(completedReview::setTeacherCompleteProgress)
                .isInstanceOf(ReviewException.class);
    }

    @Test
    @DisplayName("Finished 상태 변경이 가능한 경우 - 성공")
    void updateFinishedReview() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.STUDENT, null);

        Review completedReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.TEACHER_COMPLETED);

        assertThatCode(completedReview::setFinishedProgress)
                .doesNotThrowAnyException();
    }

    @DisplayName("Finished 상태 변경이 가능한 경우 - 실패")
    void updateFinishedReviewFail() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.STUDENT, null);

        Review onGoingReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatThrownBy(onGoingReview::setFinishedProgress)
                .isInstanceOf(ReviewException.class);
    }

    @Test
    @DisplayName("Student Id, Teacher Id 값 Validation - 성공")
    void validateMemberId() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.STUDENT, null);

        Review completedReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.TEACHER_COMPLETED);

        assertThatCode(() -> {
            completedReview.validateMemberIdAsStudent(student.getId());
            completedReview.validateMemberIdAsTeacher(teacher.getId());
        }).doesNotThrowAnyException();
    }

    @DisplayName("Finished 상태 변경이 가능한 경우 - 학생 ID 실패")
    void validateStudentIdFail() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.STUDENT, null);

        Review onGoingReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatThrownBy(() -> onGoingReview.validateMemberIdAsStudent(teacher.getId()))
                .isInstanceOf(AuthorizationRequest.class);
    }

    @DisplayName("Finished 상태 변경이 가능한 경우 - 선생님 ID 실패")
    void validateTeacherIdFail() {
        Member teacher = new Member(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.TEACHER, null);
        Member student = new Member(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", Role.STUDENT, null);

        Review onGoingReview = new Review(teacher, student, "test title", "test content", "github/3", 0L, Progress.ON_GOING);

        assertThatThrownBy(() -> onGoingReview.validateMemberIdAsTeacher(student.getId()))
                .isInstanceOf(AuthorizationRequest.class);
    }
}
