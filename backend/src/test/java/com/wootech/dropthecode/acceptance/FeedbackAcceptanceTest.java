package com.wootech.dropthecode.acceptance;

import com.wootech.dropthecode.dto.request.FeedbackRequest;
import com.wootech.dropthecode.dto.response.FeedbackPaginationResponse;
import com.wootech.dropthecode.dto.response.LoginResponse;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static com.wootech.dropthecode.acceptance.ReviewAcceptanceTest.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Feedback 관련 인수 테스트")
public class FeedbackAcceptanceTest extends AcceptanceTest {
    private LoginResponse student1;
    private LoginResponse teacher1;
    private LoginResponse student2;
    private LoginResponse teacher2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        // given
        student1 = 학생_로그인되어_있음("air");
        teacher1 = 리뷰어_로그인되어_있음("allie");

        student2 = 학생_로그인되어_있음("seed");
        teacher2 = 리뷰어_로그인되어_있음("fafi");

        reviewCreateToFinishProcess(student1, teacher1);
        reviewCreateToFinishProcess(student1, teacher2);
        reviewCreateToFinishProcess(student2, teacher1);
        reviewCreateToFinishProcess(student2, teacher2);
    }

    @Test
    @DisplayName("feedback 전체 목록 조회 - 학생 id, 선생님 id로 검색")
    void findFeedbacksWithStudentIdAndTeacherId() {
        // when
        ExtractableResponse<Response> response = 피드백_조회_요청(student1.getId(), teacher1.getId());
        FeedbackPaginationResponse result = response.as(FeedbackPaginationResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getFeedbacks()).hasSize(1);
        assertThat(result.getFeedbacks()).extracting("studentProfile")
                                         .extracting("name")
                                         .contains(student1.getName());
    }

    @Test
    @DisplayName("feedback 전체 목록 조회 - 학생 id")
    void findFeedbacksWithStudentId() {
        // when
        ExtractableResponse<Response> response = 피드백_조회_요청(student1.getId(), null);
        FeedbackPaginationResponse result = response.as(FeedbackPaginationResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getFeedbacks()).hasSize(2);
        assertThat(result.getFeedbacks()).extracting("studentProfile")
                                         .extracting("name")
                                         .contains(student1.getName());
    }

    @Test
    @DisplayName("feedback 전체 목록 조회 - 선생님 id로 검색")
    void findFeedbacksWithTeacherId() {
        // when
        ExtractableResponse<Response> response = 피드백_조회_요청(null, teacher1.getId());
        FeedbackPaginationResponse result = response.as(FeedbackPaginationResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getFeedbacks()).hasSize(2);
        assertThat(result.getFeedbacks()).extracting("studentProfile")
                                         .extracting("name")
                                         .contains(student1.getName(), student2.getName());
    }

    @Test
    @DisplayName("feedback 전체 목록 조회 - 조건 없이")
    void findFeedbacksWithNoCondition() {
        // when
        ExtractableResponse<Response> response = 피드백_조회_요청(null, null);
        FeedbackPaginationResponse result = response.as(FeedbackPaginationResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getFeedbacks()).hasSize(4);
        assertThat(result.getFeedbacks()).extracting("studentProfile")
                                         .extracting("name")
                                         .contains(student1.getName(), student2.getName());
    }

    private void reviewCreateToFinishProcess(LoginResponse student, LoginResponse teacher) {
        ExtractableResponse<Response> reviewResponse =
                새로운_리뷰_요청(student.getAccessToken(), 리뷰_요청_데이터(student.getId(), teacher.getId()));
        String reviewId = reviewResponse.header("Location").substring(9);

        리뷰_수락_요청(reviewId, teacher.getAccessToken());
        선생님_리뷰_완료_요청(reviewId, teacher.getAccessToken());
        FeedbackRequest feedback = new FeedbackRequest(5, "많이 배웠습니다!");
        학생_리뷰_완료_요청(reviewId, student.getAccessToken(), feedback);
    }

    public static ExtractableResponse<Response> 피드백_조회_요청(Long studentId, Long teacherId) {
        return RestAssured.given()
                          .log().all()
                          .param("studentId", studentId)
                          .param("teacherId", teacherId)
                          .when()
                          .get("/feedbacks")
                          .then()
                          .log().all()
                          .extract();
    }

}
