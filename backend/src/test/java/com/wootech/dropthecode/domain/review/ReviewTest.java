package com.wootech.dropthecode.domain.review;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.exception.AuthorizationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.builder.MemberBuilder.dummyMember;
import static com.wootech.dropthecode.builder.ReviewBuilder.dummyReview;
import static org.assertj.core.api.Assertions.*;

public class ReviewTest {

    @DisplayName("Student Id, Teacher Id 값 Validation - 성공")
    @Test
    void validateMemberId() {
        Member teacher = dummyMember(1L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Jinho", "jh8579@gmail.com", "s3://jh8579", "github url", Role.STUDENT, null);

        Review completedReview = dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.TEACHER_COMPLETED);

        assertThatCode(() -> {
            completedReview.validateAuthorityOfStudent(student.getId());
            completedReview.validateAuthorityOfTeacher(teacher.getId());
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("리뷰 내용 수정 - 권한이 있는 경우")
    void update() {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "original title", "original content", "original pr link", 0L, Progress.ON_GOING);

        // when
        review.update(2L, "new title", "new content", "new pr link");

        // then
        assertThat(review).extracting("title").isEqualTo("new title");
        assertThat(review).extracting("content").isEqualTo("new content");
        assertThat(review).extracting("prUrl").isEqualTo("new pr link");
    }

    @Test
    @DisplayName("리뷰 내용 수정 - 권한이 없는 경우")
    void updateNoAuthorization() {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "original title", "original content", "original pr link", 0L, Progress.ON_GOING);

        // when
        // then
        assertThatThrownBy(() -> review.update(1L, "new title", "new content", "new pr link"))
                .isInstanceOf(AuthorizationException.class);
    }

    @Test
    @DisplayName("Pending 상태인지 확인")
    void isPending() {
        // given
        Review pendingReview = dummyReview(null, null, "title1", "content1", "pr1", 0L, Progress.PENDING);
        Review deniedReview = dummyReview(null, null, "title1", "content1", "pr1", 0L, Progress.DENIED);
        Review onGoingReview = dummyReview(null, null, "title1", "content1", "pr1", 0L, Progress.ON_GOING);
        Review teacherCompletedReview = dummyReview(null, null, "title1", "content1", "pr1", 0L, Progress.TEACHER_COMPLETED);
        Review finishedReview = dummyReview(null, null, "title1", "content1", "pr1", 0L, Progress.FINISHED);

        // when
        boolean pending = pendingReview.isPending();
        boolean denied = deniedReview.isPending();
        boolean onGoing = onGoingReview.isPending();
        boolean teacherCompleted = teacherCompletedReview.isPending();
        boolean finished = finishedReview.isPending();

        // then
        assertThat(pending).isTrue();
        assertThat(denied).isFalse();
        assertThat(onGoing).isFalse();
        assertThat(teacherCompleted).isFalse();
        assertThat(finished).isFalse();
    }

    @Test
    @DisplayName("Review 주인이 아닌 경우 검증 테스트")
    void validatesOwnerByLoginId() {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);
        Review review = dummyReview(teacher, student, "title", "content", "pr link", 0L, Progress.PENDING);

        // when
        // then
        assertThatThrownBy(() -> review.validateAuthorityOfStudent(teacher.getId()))
                .isInstanceOf(AuthorizationException.class);
    }
}