package com.wootech.dropthecode.acceptance;

import com.wootech.dropthecode.dto.request.ReviewRequest;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.exception.ErrorResponse;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.*;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("리뷰 관련 인수 테스트")
class ReviewAcceptanceTest {

    @Nested
    @DisplayName("리뷰 생성")
    class CreateReview extends AcceptanceTest {
        private LoginResponse student;
        private LoginResponse teacher;

        @Override
        @BeforeEach
        public void setUp() {
            super.setUp();
            // given
            student = 로그인되어_있음("air");
            teacher = 로그인되어_있음("curry");
        }

        @Test
        @DisplayName("리뷰 요청 성공")
        void createReviewSuccess() {
            // given
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
            LoginResponse student2 = 로그인되어_있음("allie");
            ReviewRequest reviewRequest = ReviewRequest.builder()
                                                       .studentId(student.getId())
                                                       .teacherId(student2.getId())
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
        @DisplayName("자기 자신에게 리뷰 요청을 보내는 경우")
        void selfRequest() {
            // given
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
    class FindStudentReview extends AcceptanceTest {
        private LoginResponse student;
        private LoginResponse teacher;

        @Override
        @BeforeEach
        public void setUp() {
            super.setUp();
            // given
            student = 로그인되어_있음("air");
            teacher = 로그인되어_있음("curry");

            ReviewRequest reviewRequest1 = ReviewRequest.builder()
                                                        .studentId(student.getId())
                                                        .teacherId(teacher.getId())
                                                        .title("리뷰 요청합니다!")
                                                        .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                        .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                        .build();
            ReviewRequest reviewRequest2 = ReviewRequest.builder()
                                                        .studentId(student.getId())
                                                        .teacherId(teacher.getId())
                                                        .title("코리부!")
                                                        .content("기대중입니다!")
                                                        .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                        .build();
            새로운_리뷰_요청(student.getAccessToken(), reviewRequest1);
            새로운_리뷰_요청(student.getAccessToken(), reviewRequest2);
        }

        @Test
        @DisplayName("내가 받은 리뷰 목록 조회 성공")
        void findStudentReviewSuccess() {
            // when
            ExtractableResponse<Response> response = 내가_받은_리뷰_목록_조회_요청(student.getId(), student.getAccessToken());
            ReviewsResponse result = response.as(ReviewsResponse.class);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(result.getPageCount()).isEqualTo(1);
            assertThat(result.getReviews()).hasSize(2);
        }

        @Disabled
        @Test
        @DisplayName("로그인 한 유저 id와 받은 리뷰 목록 조희 유저 id가 다른 경우")
        void showOtherStudentReview() {
            // given
            Long anonymousMemberId = 100L;

            // when
            ExtractableResponse<Response> response = 내가_받은_리뷰_목록_조회_요청(anonymousMemberId, student.getAccessToken());
            ErrorResponse result = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(result.getErrorMessage()).isNotNull();
        }

        @Test
        @DisplayName("유효하지 않은 access token")
        void invalidAccessToken() {
            // when
            ExtractableResponse<Response> response = 내가_받은_리뷰_목록_조회_요청(student.getId(), student.getAccessToken() + "invalid");
            ErrorResponse result = 예외_결과(response);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(result.getErrorMessage()).isNotNull();
        }
    }

    @Nested
    @DisplayName("내가 리뷰한 리뷰 목록 조회")
    class FindTeacherReview extends AcceptanceTest {
        private LoginResponse student;
        private LoginResponse teacher;

        @Override
        @BeforeEach
        public void setUp() {
            super.setUp();
            // given
            student = 로그인되어_있음("air");
            teacher = 로그인되어_있음("curry");

            ReviewRequest reviewRequest1 = ReviewRequest.builder()
                                                        .studentId(student.getId())
                                                        .teacherId(teacher.getId())
                                                        .title("리뷰 요청합니다!")
                                                        .content("초보라 맞게 한지 잘 모르겠네요.. 잘 부탁드려요!")
                                                        .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                        .build();
            ReviewRequest reviewRequest2 = ReviewRequest.builder()
                                                        .studentId(student.getId())
                                                        .teacherId(teacher.getId())
                                                        .title("코리부!")
                                                        .content("기대중입니다!")
                                                        .prUrl("https://github.com/woowacourse-teams/2021-drop-the-code/pull/262")
                                                        .build();
            새로운_리뷰_요청(student.getAccessToken(), reviewRequest1);
            새로운_리뷰_요청(student.getAccessToken(), reviewRequest2);
        }

        @Test
        @DisplayName("내가 리뷰한 리뷰 목록 조회 성공")
        void findTeacherReviewSuccess() {
            // when
            ExtractableResponse<Response> response = 내가_맡은_리뷰_목록_조회_요청(teacher.getId());
            ReviewsResponse result = response.as(ReviewsResponse.class);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(result.getPageCount()).isEqualTo(1);
            assertThat(result.getReviews()).hasSize(2);
        }

        @Test
        @DisplayName("존재하지 않는 선생님인 경우")
        void notExistTeacher() {
            // given
            Long notExistTeacherId = 100L;

            // when
            ExtractableResponse<Response> response = 내가_맡은_리뷰_목록_조회_요청(notExistTeacherId);
            ReviewsResponse result = response.as(ReviewsResponse.class);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(result.getPageCount()).isEqualTo(0);
            assertThat(result.getReviews()).isEmpty();
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

    public static ExtractableResponse<Response> 내가_받은_리뷰_목록_조회_요청(Long id, String accessToken) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + accessToken)
                          .when()
                          .get("/reviews/student/{id}", id)
                          .then()
                          .log().all()
                          .extract();
    }

    public static ExtractableResponse<Response> 내가_맡은_리뷰_목록_조회_요청(Long id) {
        return RestAssured.given()
                          .log().all()
                          .when()
                          .get("/reviews/teacher/{id}", id)
                          .then()
                          .log().all()
                          .extract();
    }
}
