package com.wootech.dropthecode.acceptance;

import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.dto.response.AccessTokenResponse;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.exception.ErrorResponse;
import com.wootech.dropthecode.exception.OauthTokenRequestException;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@DisplayName("Auth 관련 인수 테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("OAuth 로그인 - 로그인 성공")
    void oAuthLoginTestSuccess() {
        // given
        // when
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        // then
        assertThat(loginResponse).usingRecursiveComparison().ignoringFields("accessToken", "refreshToken")
                                 .isEqualTo(LoginResponse.builder()
                                                         .id(1L)
                                                         .name("air")
                                                         .email("air@email.com")
                                                         .imageUrl("s3://image/air")
                                                         .githubUrl("https://github.com/air")
                                                         .role(Role.STUDENT)
                                                         .tokenType("Bearer")
                                                         .build());
        assertThat(loginResponse.getAccessToken()).isNotNull();
        assertThat(loginResponse.getRefreshToken()).isNotNull();
    }

    @Test
    @DisplayName("OAuth 로그인 - 유효하지 않은 provider 요청")
    void invalidProvider() {
        // given
        // when
        ErrorResponse error = 예외_결과(유효하지않은_OAUTH_서버_로그인_요청());

        // then
        assertThat(error.getErrorMessage()).isEqualTo("유효하지 않은 Oauth Provider입니다.");
    }

    @Test
    @DisplayName("access token 갱신 - 유효한 access token & 유효한 refresh token")
    void notExpiredAccessTokenAndNotExpiredRefreshToken() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 토큰_갱신_요청(loginResponse.getAccessToken(), loginResponse.getRefreshToken());
        AccessTokenResponse result = response.as(AccessTokenResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getAccessToken()).isNotNull();
    }

    @Test
    @DisplayName("access token 갱신 - 유효하지않은 access token & 유효하지않은 refresh token")
    void expiredAccessTokenAndExpiredRefreshToken() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 토큰_갱신_요청(loginResponse.getAccessToken() + "invalid", loginResponse.getRefreshToken() + "invalid");
        ErrorResponse error = 예외_결과(response);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(error.getErrorMessage()).isEqualTo("refresh token이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("access token 갱신 - 유효한 access token & 유효하지않은 refresh token")
    void notExpiredAccessTokenAndExpiredRefreshToken() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 토큰_갱신_요청(loginResponse.getAccessToken(), loginResponse.getRefreshToken() + "invalid");
        ErrorResponse error = 예외_결과(response);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(error.getErrorMessage()).isEqualTo("refresh token이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("access token 갱신 - 유효하지않은 access token & 유효한 refresh token")
    void invalidAccessTokenAndNotExpiredRefreshToken() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 토큰_갱신_요청(loginResponse.getAccessToken() + "invalid", loginResponse.getRefreshToken());
        ErrorResponse error = 예외_결과(response);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(error.getErrorMessage()).isEqualTo("유효하지 않은 토큰입니다.");
    }

    @Test
    @DisplayName("로그 아웃 성공")
    void logOutSuccess() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 로그아웃_요청(loginResponse.getAccessToken());

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("로그 아웃 실패 - 유효하지 않은 access token")
    void invalidAccessToken() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 로그아웃_요청(loginResponse.getAccessToken() + "invalid");
        ErrorResponse error = 예외_결과(response);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(error.getErrorMessage()).isEqualTo("access token이 유효하지 않습니다.");
    }

    public ExtractableResponse<Response> 유효하지않은_OAUTH_서버_로그인_요청() {
        doThrow(new OauthTokenRequestException("유효하지 않은 Oauth Provider입니다."))
                .when(inMemoryProviderRepository).findByProviderName("kakao");

        return RestAssured.given()
                          .log().all()
                          .when()
                          .get("/login/oauth?providerName=kakao&code=authorizationCode")
                          .then()
                          .log().all()
                          .statusCode(HttpStatus.BAD_REQUEST.value())
                          .extract();
    }

    public ExtractableResponse<Response> 토큰_갱신_요청(String accessToken, String refreshToken) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + accessToken)
                          .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                          .urlEncodingEnabled(true)
                          .param("refreshToken", refreshToken)
                          .when()
                          .post("/token")
                          .then()
                          .log().all()
                          .extract();
    }

    public ExtractableResponse<Response> 로그아웃_요청(String accessToken) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + accessToken)
                          .when()
                          .post("/logout")
                          .then()
                          .extract();
    }
}
