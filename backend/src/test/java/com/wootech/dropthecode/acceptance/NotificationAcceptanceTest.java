package com.wootech.dropthecode.acceptance;

import com.wootech.dropthecode.dto.request.ReviewRequest;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.dto.response.NotificationsResponse;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static com.wootech.dropthecode.acceptance.ReviewAcceptanceTest.리뷰_요청_데이터;
import static com.wootech.dropthecode.acceptance.ReviewAcceptanceTest.새로운_리뷰_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("알림 관련 인수테스트")
class NotificationAcceptanceTest extends AcceptanceTest {
    private LoginResponse student;
    private LoginResponse teacher;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        student = 학생_로그인되어_있음("air");
        teacher = 리뷰어_로그인되어_있음("curry");
    }

    @Test
    @DisplayName("로그인 멤버의 모든 알림 조회")
    void findAllNotification() {
        // given
        ReviewRequest reviewRequest = 리뷰_요청_데이터(student.getId(), teacher.getId());
        새로운_리뷰_요청(student.getAccessToken(), reviewRequest);

        // when
        ExtractableResponse<Response> response = 모든_알림_조회(teacher.getAccessToken());
        NotificationsResponse result = response.as(NotificationsResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getNotificationResponses()).hasSize(1);
        assertThat(result.getUnreadCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("읽지 않은 알림 읽기")
    void readNotification() {
        // given
        ReviewRequest reviewRequest = 리뷰_요청_데이터(student.getId(), teacher.getId());
        새로운_리뷰_요청(student.getAccessToken(), reviewRequest);

        // when
        ExtractableResponse<Response> findNotification = 모든_알림_조회(teacher.getAccessToken());
        NotificationsResponse original = findNotification.as(NotificationsResponse.class);
        Long notificationId = original.getNotificationResponses().get(0).getId();

        ExtractableResponse<Response> read = 알림_읽기_요청(teacher.getAccessToken(), notificationId);

        ExtractableResponse<Response> afterRead = 모든_알림_조회(teacher.getAccessToken());
        NotificationsResponse result = afterRead.as(NotificationsResponse.class);

        // then
        assertThat(read.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(afterRead.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getNotificationResponses()).hasSize(1);
        assertThat(result.getUnreadCount()).isEqualTo(0);
    }

    public static ExtractableResponse<Response> 모든_알림_조회(String accessToken) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + accessToken)
                          .accept(APPLICATION_JSON_VALUE)
                          .when()
                          .get("/notifications")
                          .then()
                          .log().all()
                          .extract();
    }

    public static ExtractableResponse<Response> 알림_읽기_요청(String accessToken, Long notificationId) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + accessToken)
                          .accept(APPLICATION_JSON_VALUE)
                          .when()
                          .patch("/notifications/" + notificationId)
                          .then()
                          .log().all()
                          .extract();
    }
}
