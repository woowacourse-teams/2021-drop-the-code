package com.wootech.dropthecode.acceptance;

import com.wootech.dropthecode.dto.request.ReviewRequest;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.exception.ErrorResponse;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("리뷰 관련 인수 테스트")
public class ReviewAcceptanceTest {

    @Nested
    @DisplayName("리뷰 생성")
    class CreateReview extends AcceptanceTest {

        @Test
        @DisplayName("리뷰 요청 성공")
        void createReviewSuccess() {
            // given
            LoginResponse student = 로그인되어_있음("air");
            LoginResponse teacher = 로그인되어_있음("curry");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(student.getId())
                                                       .teacherId(teacher.getId())
                                                       .title("리뷰 요청합니다!")
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(student.getAccessToken(), reviewRequest);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.header("Location")).isNotNull();
        }

        @Test
        @DisplayName("student id가 빈 경우")
        void emptyStudentId() {
            // given
            LoginResponse student = 로그인되어_있음("air");
            LoginResponse teacher = 로그인되어_있음("curry");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(null)
                                                       .teacherId(teacher.getId())
                                                       .title("리뷰 요청합니다!")
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(student.getAccessToken(), reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }

        @Test
        @DisplayName("리뷰를 요청한 학생이 존재하지 않는 학생인 경우")
        void notExistStudentId() {
            // given
            Long notExistStudentId = 100L;
            LoginResponse teacher = 로그인되어_있음("curry");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(notExistStudentId)
                                                       .teacherId(teacher.getId())
                                                       .title("리뷰 요청합니다!")
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(teacher.getAccessToken(), reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }

        @Test
        @DisplayName("teacher id가 빈 경우")
        void emptyTeacherId() {
            // given
            LoginResponse student = 로그인되어_있음("air");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(student.getId())
                                                       .teacherId(null)
                                                       .title("리뷰 요청합니다!")
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(student.getAccessToken(), reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }

        @Test
        @DisplayName("리뷰를 요청받은 선생님이 존재하지 않는 선생님인 경우")
        void notExistTeacherId() {
            // given
            Long notExistTeacherId = 100L;
            LoginResponse student = 로그인되어_있음("air");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(student.getId())
                                                       .teacherId(notExistTeacherId)
                                                       .title("리뷰 요청합니다!")
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(student.getAccessToken(), reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }

        @Test
        @DisplayName("리뷰 요청 제목인 빈 경우")
        void emptyTitle() {
            // given
            LoginResponse student = 로그인되어_있음("air");
            LoginResponse teacher = 로그인되어_있음("curry");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(student.getId())
                                                       .teacherId(teacher.getId())
                                                       .title(null)
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(student.getAccessToken(), reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }

        @Test
        @DisplayName("리뷰 요청 내용이 빈 경우")
        void emptyContent() {
            // given
            LoginResponse student = 로그인되어_있음("air");
            LoginResponse teacher = 로그인되어_있음("curry");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(student.getId())
                                                       .teacherId(teacher.getId())
                                                       .title("리뷰 요청합니다!")
                                                       .content(null)
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(student.getAccessToken(), reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }

        @Test
        @DisplayName("리뷰 요청 pr링크가 빈 경우")
        void emptyPrUrl() {
            // given
            LoginResponse student = 로그인되어_있음("air");
            LoginResponse teacher = 로그인되어_있음("curry");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(student.getId())
                                                       .teacherId(teacher.getId())
                                                       .title("리뷰 요청합니다!")
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl(null)
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(student.getAccessToken(), reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }

        @Test
        @DisplayName("유효하지 않은 access token")
        void invalidAccessToken() {
            // given
            LoginResponse student = 로그인되어_있음("air");
            LoginResponse teacher = 로그인되어_있음("curry");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(student.getId())
                                                       .teacherId(teacher.getId())
                                                       .title("리뷰 요청합니다!")
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청("invalid.access.token", reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }

        @Disabled
        @Test
        @DisplayName("학생이 학생에게 리뷰 요청을 보내는 경우")
        void studentToStudent() {
            // given
            LoginResponse student1 = 로그인되어_있음("air");
            LoginResponse student2 = 로그인되어_있음("allie");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(student1.getId())
                                                       .teacherId(student2.getId())
                                                       .title("리뷰 요청합니다!")
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(student1.getAccessToken(), reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }

        @Disabled
        @Test
        @DisplayName("자기 자신에게 리뷰 요청을 보내는 경우")
        void selfRequest() {
            // given
            LoginResponse student = 로그인되어_있음("air");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(student.getId())
                                                       .teacherId(student.getId())
                                                       .title("리뷰 요청합니다!")
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(student.getAccessToken(), reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }

        @Disabled
        @Test
        @DisplayName("로그인을 한 멤버와 리뷰 요청인이 다른 경우")
        void studentNotSameLoginMember() {
            // given
            LoginResponse student = 로그인되어_있음("air");
            LoginResponse teacher = 로그인되어_있음("curry");
            LoginResponse anonymous = 로그인되어_있음("allie");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(anonymous.getId())
                                                       .teacherId(teacher.getId())
                                                       .title("리뷰 요청합니다!")
                                                       .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                       .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                       .build();

            // when
            ExtractableResponse<Response> response = 새로운_리뷰_요청(student.getAccessToken(), reviewRequest);
            ErrorResponse error = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(error.getErrorMessage()).isNotNull();
        }
    }

    @Nested
    @DisplayName("내가 받은 리뷰 목록 조회")
    class FindStudentReview {

        @Test
        @DisplayName("내가 받은 리뷰 목록 조회 성공")
        void findStudentReviewSuccess() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("존재하지 않는 학생인 경우")
        void notExistStudent() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("로그인 한 유저 id와 받은 리뷰 목록 조희 유저 id가 다른 경우")
        void findOtherStudentReview() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 access token")
        void invalidAccessToken() {
            // given

            // when

            // then
        }
    }

    @Nested
    @DisplayName("내가 리뷰한 리뷰 목록 조회")
    class FindTeacherReview {

        @Test
        @DisplayName("내가 리뷰한 리뷰 목록 조회 성공")
        void findTeacherReviewSuccess() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("존재하지 않는 선생님인 경우")
        void notExistTeacher() {
            // given

            // when

            // then
        }
    }

    @Nested
    @DisplayName("리뷰 목록 조회")
    class FindReview {

        @Test
        @DisplayName("리뷰 상세 목록 조회 성공")
        void findReviewByIdSuccess() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("존재하지 않는 리뷰 상세 목록 조회")
        void findReviewByNotExistId() {
            // given

            // when

            // then
        }
    }

    @Nested
    @DisplayName("리뷰 상태 변경")
    class ChangeReviewProgress {

        @Test
        @DisplayName("ON_GOING 상태의 리뷰를 TEACHER_COMPLETE 상태로 변경 성공")
        void onGoingToTeacherComplete() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("TEACHER_COMPLETE 상태의 리뷰를 FINISHED 상태로 변경 성공")
        void TeacherCompleteToFinished() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("존재하지 않은 리뷰인 경우")
        void notExistReview() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("선생님이 아닌데 ON_GOING -> TEACHER_COMPLETE 변경 요청을 하는 경우")
        void noTeacherWhenUpdateToCompleteReview() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("ON_GOING 상태가 아닌데 ON_GOING -> TEACHER_COMPLETE 변경 요청을 하는 경우")
        void noOnGoingButUpdateToCompleteReview() {
            // given

            // when

            // then
        }


        @Test
        @DisplayName("학생이 아닌데 TEACHER_COMPLETE -> FINISHED 변경 요청을 하는 경우")
        void noStudentWhenUpdateToFinishReview() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("TEACHER_COMPLETE 상태가 아닌데 TEACHER_COMPLETE -> FINISHED 변경 요청을 하는 경우")
        void noTeacherCompletedButUpdateToFinishReview() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 access token")
        void invalidAccessToken() {
            // given

            // when

            // then
        }
    }

    public static ExtractableResponse<Response> 새로운_리뷰_요청(String accessToken, ReviewRequest request) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + accessToken)
                          .contentType(APPLICATION_JSON_VALUE)
                          .body(request)
                          .when()
                          .post("/reviews")
                          .then()
                          .log().all()
                          .extract();
    }
}
