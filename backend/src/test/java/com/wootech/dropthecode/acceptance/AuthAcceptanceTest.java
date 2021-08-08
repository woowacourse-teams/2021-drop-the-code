package com.wootech.dropthecode.acceptance;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.domain.oauth.InMemoryProviderRepository;
import com.wootech.dropthecode.domain.oauth.OauthProvider;
import com.wootech.dropthecode.dto.response.AccessTokenResponse;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.exception.ErrorResponse;
import com.wootech.dropthecode.exception.OauthTokenRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@DisplayName("Auth 관련 인수 테스트")
public class AuthAcceptanceTest extends AcceptanceTest {
    @Autowired
    private Environment environment;

    @MockBean
    private InMemoryProviderRepository inMemoryProviderRepository;

    @Test
    @DisplayName("OAuth 로그인 - 로그인 성공")
    void oAuthLoginTestSuccess() throws UnknownHostException {
        // given
        // when
        LoginResponse loginResponse = 로그인되어_있음();

        // then
        assertThat(loginResponse).usingRecursiveComparison().ignoringFields("accessToken", "refreshToken")
                                 .isEqualTo(LoginResponse.builder()
                                                         .id(1L)
                                                         .name("air")
                                                         .email("air@email.com")
                                                         .imageUrl("s3://image")
                                                         .githubUrl("https://github.com")
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
    void notExpiredAccessTokenAndNotExpiredRefreshToken() throws UnknownHostException {
        // given
        LoginResponse loginResponse = 로그인되어_있음();

        // when
        ExtractableResponse<Response> response = 토큰_갱신_요청(loginResponse.getAccessToken(), loginResponse.getRefreshToken());
        AccessTokenResponse result = response.as(AccessTokenResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getAccessToken()).isNotNull();
    }

    @Test
    @DisplayName("access token 갱신 - 유효하지않은 access token & 유효하지않은 refresh token")
    void expiredAccessTokenAndExpiredRefreshToken() throws UnknownHostException {
        // given
        LoginResponse loginResponse = 로그인되어_있음();

        // when
        ExtractableResponse<Response> response = 토큰_갱신_요청(loginResponse.getAccessToken() + "invalid", loginResponse.getRefreshToken() + "invalid");
        ErrorResponse error = 예외_결과(response);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(error.getErrorMessage()).isEqualTo("refresh token이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("access token 갱신 - 유효한 access token & 유효하지않은 refresh token")
    void notExpiredAccessTokenAndExpiredRefreshToken() throws UnknownHostException {
        // given
        LoginResponse loginResponse = 로그인되어_있음();

        // when
        ExtractableResponse<Response> response = 토큰_갱신_요청(loginResponse.getAccessToken(), loginResponse.getRefreshToken() + "invalid");
        ErrorResponse error = 예외_결과(response);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(error.getErrorMessage()).isEqualTo("refresh token이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("access token 갱신 - 유효하지않은 access token & 유효한 refresh token")
    void invalidAccessTokenAndNotExpiredRefreshToken() throws UnknownHostException {
        // given
        LoginResponse loginResponse = 로그인되어_있음();

        // when
        ExtractableResponse<Response> response = 토큰_갱신_요청(loginResponse.getAccessToken() + "invalid", loginResponse.getRefreshToken());
        ErrorResponse error = 예외_결과(response);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(error.getErrorMessage()).isEqualTo("유효하지 않은 토큰입니다.");
    }

    @Test
    @DisplayName("로그 아웃 성공")
    void logOutSuccess() throws UnknownHostException {
        // given
        LoginResponse loginResponse = 로그인되어_있음();

        // when
        ExtractableResponse<Response> response = 로그아웃_요청(loginResponse.getAccessToken());

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("로그 아웃 실패 - 유효하지 않은 access token")
    void invalidAccessToken() throws UnknownHostException {
        // given
        LoginResponse loginResponse = 로그인되어_있음();

        // when
        ExtractableResponse<Response> response = 로그아웃_요청(loginResponse.getAccessToken() + "invalid");
        ErrorResponse error = 예외_결과(response);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(error.getErrorMessage()).isEqualTo("access token이 유효하지 않습니다.");
    }


    public LoginResponse 로그인되어_있음() throws UnknownHostException {
        ExtractableResponse<Response> response = 로그인_요청();
        return response.as(LoginResponse.class);
    }

    public ErrorResponse 예외_결과(ExtractableResponse<Response> response) {
        return response.as(ErrorResponse.class);
    }

    public ExtractableResponse<Response> 로그인_요청() throws UnknownHostException {
        String serverAddress = "http://3.37.245.216:" + port;
//        String serverAddress = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port;
        given(inMemoryProviderRepository.findByProviderName("github"))
                .willReturn(OauthProvider.builder()
                                         .clientId("fakeClientId")
                                         .clientSecret("fakeClientSecret")
                                         .tokenUrl(serverAddress + "/fake/login/oauth/access_token")
                                         .userInfoUrl(serverAddress + "/fake/user")
                                         .build());

        return RestAssured.given()
                          .log().all()
                          .when()
                          .get("/login/oauth?providerName=github&code=authorizationCode")
                          .then()
                          .log().all()
                          .statusCode(HttpStatus.OK.value())
                          .extract();
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
