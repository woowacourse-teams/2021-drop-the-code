package com.wootech.dropthecode.acceptance;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@DisplayName("Auth 관련 인수 테스트")
public class AuthAcceptanceTest {

    @Nested
    @DisplayName("OAuth 로그인 테스트")
    class OAuthLogin extends AcceptanceTest {

        @Test
        @DisplayName("성공")
        void oAuthLoginTestSuccess() {
            // given
            로그인_요청();

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 provider 요청")
        void invalidProvider() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 code")
        void invalidCode() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 token")
        void invalidToken() {
            // given

            // when

            // then
        }
    }

    @Nested
    @DisplayName("access token 갱신")
    class RefreshAccessToken {

        @Test
        @DisplayName("유효하지 않은 access token")
        void invalidAccessToken() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 refresh token")
        void invalidRefreshToken() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("만료된 access token & 만료된 refresh token")
        void expiredAccessTokenAndExpiredRefreshToken() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("만료되지 않은 access token & 만료된 refresh token")
        void notExpiredAccessTokenAndExpiredRefreshToken() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("만료된 access token & 만료되지 않은 refresh token")
        void expiredAccessTokenAndNotExpiredRefreshToken() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("만료되지 않은 access token & 만료되지 않은 refresh token")
        void notExpiredAccessTokenAndNotExpiredRefreshToken() {
            // given

            // when

            // then
        }
    }

    @Nested
    @DisplayName("로그아웃")
    class Logout {

        @Test
        @DisplayName("로그 아웃 성공")
        void logOutSuccess() {
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

    public static ExtractableResponse<Response> 로그인_요청() {
        return RestAssured.given()
                          .log().all()
                          .when()
                          .get("/login/oauth?providerName=github&code=authorizationCode")
                          .then()
                          .log().all()
                          .statusCode(HttpStatus.OK.value())
                          .extract();
    }

    public static ExtractableResponse<Response> 토큰_갱신() {
        return RestAssured.given()
                          .log().all()
                          .auth().oauth2("access.token")
                          .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                          .urlEncodingEnabled(true)
                          .param("refreshToken", "refresh.token")
                          .when()
                          .post("/token")
                          .then()
                          .log().all()
                          .statusCode(HttpStatus.OK.value())
                          .extract();
    }
}
