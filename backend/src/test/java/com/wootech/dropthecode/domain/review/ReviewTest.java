package com.wootech.dropthecode.domain.review;

import java.time.LocalDateTime;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.exception.ReviewException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

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

    @DisplayName("학생이 학생에게 요청 보냈을 때 - 실패")
    @Test
    void invalidReviewByRequestToStudent() {
        Member fakeTeacher = dummyMember(1L, "1000", "Fafi", "fafi@gmail.com", "s3://fafi2143", "github url", Role.STUDENT, null);
        Member student = dummyMember(2L, "1000", "Fafi", "fafi@gmail.com", "s3://fafi2143", "github url", Role.STUDENT, null);

        assertThatThrownBy(() -> {
            dummyReview(fakeTeacher, student, "test title", "test content", "github/3", 0L, Progress.PENDING);
        }).isInstanceOf(ReviewException.class)
          .hasMessage("리뷰어 권한이 없는 사용자에게는 리뷰를 요청할 수 없습니다.");
    }

    @DisplayName("자기 자신에게 요청 보냈을 때 - 실패")
    @Test
    void invalidReviewBySelfRequest() {
        Member teacher = dummyMember(2L, "1000", "Fafi", "fafi@gmail.com", "s3://fafi2143", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Fafi", "fafi@gmail.com", "s3://fafi2143", "github url", Role.STUDENT, null);

        assertThatThrownBy(() -> {
            dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.PENDING);
        }).isInstanceOf(ReviewException.class)
          .hasMessage("자신에게는 리뷰를 요청할 수 없습니다.");
    }

    @DisplayName("로그인한 사용자와 요청된 리뷰의 학생id가 다를 때 - 실패")
    @Test
    void invalidReviewByLoginMemberId() {
        Member teacher = dummyMember(1L, "1000", "Fafi", "fafi@gmail.com", "s3://fafi2143", "github url", Role.TEACHER, null);
        Member student = dummyMember(2L, "1000", "Fafi", "fafi@gmail.com", "s3://fafi2143", "github url", Role.STUDENT, null);

        assertThatThrownBy(() -> {
            dummyReview(teacher, student, "test title", "test content", "github/3", 0L, Progress.PENDING).validateAuthorityOfStudent(3L);
        }).isInstanceOf(AuthorizationException.class)
          .hasMessage("리뷰 작업에 대한 권한이 없습니다!");
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
    @DisplayName("PENDING인 경우")
    void isPending() {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "title1", "content1", "pr1", 0L, Progress.PENDING);

        // when
        // then
        assertThatNoException().isThrownBy(review::validateReviewProgressIsPending);
    }

    @ParameterizedTest
    @DisplayName("PENDING이 아닌 경우 - 400 에러")
    @EnumSource(value = Progress.class, names = {"DENIED", "ON_GOING", "TEACHER_COMPLETED", "FINISHED"})
    void isNotPending(Progress progress) {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "title1", "content1", "pr1", 0L, progress);

        // when
        // then
        assertThatThrownBy(review::validateReviewProgressIsPending)
                .isInstanceOf(ReviewException.class);
    }

    @Test
    @DisplayName("ON_GOING인 경우")
    void isOnGoing() {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "title1", "content1", "pr1", 0L, Progress.ON_GOING);

        // when
        // then
        assertThatNoException().isThrownBy(review::validateReviewProgressIsOnGoing);
    }

    @ParameterizedTest
    @DisplayName("ON_GOING이 아닌 경우 - 400 에러")
    @EnumSource(value = Progress.class, names = {"PENDING", "DENIED", "TEACHER_COMPLETED", "FINISHED"})
    void isNotOnGoing(Progress progress) {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "title1", "content1", "pr1", 0L, progress);

        // when
        // then
        assertThatThrownBy(review::validateReviewProgressIsOnGoing)
                .isInstanceOf(ReviewException.class);
    }

    @Test
    @DisplayName("TEACHER_COMPLETED인 경우")
    void isTeacherCompleted() {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "title1", "content1", "pr1", 0L, Progress.TEACHER_COMPLETED);

        // when
        // then
        assertThatNoException().isThrownBy(review::validateReviewProgressIsTeacherCompleted);
    }

    @ParameterizedTest
    @DisplayName("TEACHER_COMPLETED가 아닌 경우 - 400 에러")
    @EnumSource(value = Progress.class, names = {"PENDING", "DENIED", "ON_GOING", "FINISHED"})
    void isNotTeacherCompleted(Progress progress) {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "title1", "content1", "pr1", 0L, progress);

        // when
        // then
        assertThatThrownBy(review::validateReviewProgressIsTeacherCompleted)
                .isInstanceOf(ReviewException.class);
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

    @Test
    @DisplayName("경과 시간 업데이트")
    void calculateElapsedTime() {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "title1", "content1", "pr1", 0L,
                Progress.TEACHER_COMPLETED, LocalDateTime.now().minusDays(3));
        Long original = review.getElapsedTime();

        // when
        review.updateElapsedTime();

        // then
        assertThat(original).isNotSameAs(review.getElapsedTime());
    }

    @Test
    @DisplayName("경과 시간 업데이트 - 경과시간이 비어있는 경우")
    void emptyElapsedTime() {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "title1", "content1", "pr1", 0L, Progress.TEACHER_COMPLETED);
        Long original = review.getElapsedTime();

        // when
        review.updateElapsedTime();

        // then
        assertThat(original).isEqualTo(0L);
    }

    @Test
    @DisplayName("경과 시간 계산")
    void updateElapsedTime() {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "title1", "content1", "pr1", 10800000L,
                Progress.TEACHER_COMPLETED, LocalDateTime.now().minusDays(3));

        // when
        Long elapsedTime = review.calculateElapsedTime();

        // then
        assertThat(elapsedTime).isEqualTo(3);
    }

    @Test
    @DisplayName("리뷰의 담당 선생님이 아닌 경우 - 403에러")
    void invalidateAuthorityOfTeacher() {
        // given
        Member teacher = dummyMember(1L, "1", "air.junseo@gmail.com", "air", "s3://image1", "github url1", Role.TEACHER, null);
        Member student = dummyMember(2L, "2", "max9106@naver.com", "max", "s3://image2", "github url2", Role.STUDENT, null);

        Review review = dummyReview(teacher, student, "original title", "original content", "original pr link", 0L, Progress.ON_GOING);

        // when
        // then
        assertThatThrownBy(() -> review.validateAuthorityOfTeacher(student.getId()))
                .isInstanceOf(AuthorizationException.class);
    }
}
