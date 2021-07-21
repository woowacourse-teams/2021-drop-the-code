package com.wootech.dropthecode.config.auth;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.service.AuthService;
import com.wootech.dropthecode.service.OauthService;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.ReviewCreateRequest;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.service.LanguageService;
import com.wootech.dropthecode.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationInterceptorTest {
    private static final String BEARER = "Bearer";
    private static final String VALID_ACCESS_TOKEN = "valid.access.token";
    private static final String INVALID_ACCESS_TOKEN = "invalid.access.token";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OauthService oauthService;

    @MockBean
    private AuthService authService;

    @MockBean
    private LanguageService languageService;

    @MockBean
    private TeacherService teacherService;

    @Nested
    @DisplayName("인터셉터 거치지 않는 요청 확인")
    class NoApplyInterceptor {

        @Test
        @DisplayName("GET /login/oauth")
        void login() {
            // given
            LoginResponse loginResponse = new LoginResponse("air", "air.junseo@gmail.com",
                    "image url", Role.STUDENT, "access-token", "refresh-token");

            given(oauthService.login(any())).willReturn(loginResponse);

            // when
            WebTestClient.ResponseSpec response = webTestClient.get()
                                                               .uri("/login/oauth?providerName=github&code=xxxx")
                                                               .exchange();

            // then
            response.expectStatus().isOk();
        }

        @Test
        @DisplayName("GET /languages")
        void languages() {
            // given
            // when
            WebTestClient.ResponseSpec response = webTestClient.get()
                                                               .uri("/languages")
                                                               .exchange();

            // then
            response.expectStatus().isOk();
        }

        @Test
        @DisplayName("GET /teachers")
        void teachers() {
            // given
            // when
            WebTestClient.ResponseSpec response = webTestClient.get()
                                                               .uri("/teachers?language=javascript&career=3&sort=career,desc")
                                                               .exchange();

            // then
            response.expectStatus().isOk();
        }

        @Test
        @DisplayName("GET /reviews/teacher/{id}")
        void teacherReview() {
            // given
            // when
            WebTestClient.ResponseSpec response = webTestClient.get()
                                                               .uri("/reviews/teacher/1")
                                                               .exchange();

            // then
            response.expectStatus().isOk();
        }

        @Test
        @DisplayName("GET /reviews/{id}")
        void reviewDetail() {
            // given
            // when
            WebTestClient.ResponseSpec response = webTestClient.get()
                                                               .uri("/reviews/1")
                                                               .exchange();

            // then
            response.expectStatus().isOk();
        }
    }

    @Nested
    @DisplayName("인터셉터 거치는 요청 확인")
    class ApplyInterceptor {

        @Test
        @DisplayName("POST /teachers")
        void teachers() {
            // given
            doThrow(new AuthorizationException("access token이 유효하지 않습니다."))
                    .when(authService).validatesAccessToken(INVALID_ACCESS_TOKEN);
            List<TechSpec> techSpecs = Collections.singletonList(new TechSpec("java", Arrays.asList("Spring", "Servlet")));
            TeacherRegistrationRequest request
                    = new TeacherRegistrationRequest("백엔드 개발자입니다.", "환영합니다.", 3, techSpecs);

            // when
            WebTestClient.ResponseSpec response = webTestClient.post()
                                                               .uri("/teachers")
                                                               .header("Authorization", BEARER + INVALID_ACCESS_TOKEN)
                                                               .body(Mono.just(request), TeacherRegistrationRequest.class)
                                                               .exchange();

            // then
            response.expectStatus().isUnauthorized();
        }

        @Test
        @DisplayName("POST /teachers with token")
        void teachersWithToken() {
            // given
            doNothing().when(authService).validatesAccessToken(VALID_ACCESS_TOKEN);
            List<TechSpec> techSpecs = Collections.singletonList(new TechSpec("java", Arrays.asList("Spring", "Servlet")));
            TeacherRegistrationRequest request
                    = new TeacherRegistrationRequest("백엔드 개발자입니다.", "환영합니다.", 3, techSpecs);

            // when
            WebTestClient.ResponseSpec response = webTestClient.post()
                                                               .uri("/teachers")
                                                               .header("Authorization", BEARER + VALID_ACCESS_TOKEN)
                                                               .body(Mono.just(request), TeacherRegistrationRequest.class)
                                                               .exchange();

            // then
            response.expectStatus().isCreated();
        }

        @Test
        @DisplayName("POST /reviews")
        void createReview() {
            // given
            doThrow(new AuthorizationException("access token이 유효하지 않습니다."))
                    .when(authService).validatesAccessToken(INVALID_ACCESS_TOKEN);
            ReviewCreateRequest reviewCreateRequest =
                    new ReviewCreateRequest(1L, 2L, "review title", "review content", "pr link");

            // when
            WebTestClient.ResponseSpec response = webTestClient.post()
                                                               .uri("/reviews")
                                                               .header("Authorization", BEARER + INVALID_ACCESS_TOKEN)
                                                               .body(Mono.just(reviewCreateRequest), ReviewCreateRequest.class)
                                                               .exchange();

            // then
            response.expectStatus().isUnauthorized();
        }

        @Test
        @DisplayName("POST /reviews with token")
        void createReviewWithToken() {
            // given
            doNothing().when(authService).validatesAccessToken(VALID_ACCESS_TOKEN);
            ReviewCreateRequest reviewCreateRequest =
                    new ReviewCreateRequest(1L, 2L, "review title", "review content", "pr link");

            // when
            WebTestClient.ResponseSpec response = webTestClient.post()
                                                               .uri("/reviews")
                                                               .header("Authorization", BEARER + VALID_ACCESS_TOKEN)
                                                               .body(Mono.just(reviewCreateRequest), ReviewCreateRequest.class)
                                                               .exchange();

            // then
            response.expectStatus().isCreated();
        }

        @Test
        @DisplayName("GET /reviews/student/{id}")
        void studentReview() {
            // given
            doThrow(new AuthorizationException("access token이 유효하지 않습니다."))
                    .when(authService).validatesAccessToken(INVALID_ACCESS_TOKEN);

            // when
            WebTestClient.ResponseSpec response = webTestClient.get()
                                                               .uri("/reviews/student/1")
                                                               .header("Authorization", BEARER + INVALID_ACCESS_TOKEN)
                                                               .exchange();

            // then
            response.expectStatus().isUnauthorized();
        }


        @Test
        @DisplayName("GET /reviews/student/{id} with token")
        void studentReviewWithToken() {
            // given
            doNothing().when(authService).validatesAccessToken(VALID_ACCESS_TOKEN);

            // when
            WebTestClient.ResponseSpec response = webTestClient.get()
                                                               .uri("/reviews/student/1")
                                                               .header("Authorization", BEARER + VALID_ACCESS_TOKEN)
                                                               .exchange();

            // then
            response.expectStatus().isOk();
        }

        @Test
        @DisplayName("PATCH /reviews/{id}")
        void updateReview() {
            // given
            doThrow(new AuthorizationException("access token이 유효하지 않습니다."))
                    .when(authService).validatesAccessToken(INVALID_ACCESS_TOKEN);

            // when
            WebTestClient.ResponseSpec response = webTestClient.patch()
                                                               .uri("/reviews/1")
                                                               .header("Authorization", BEARER + INVALID_ACCESS_TOKEN)
                                                               .exchange();

            // then
            response.expectStatus().isUnauthorized();
        }

        @Test
        @DisplayName("PATCH /reviews/{id} with token")
        void updateReviewWithToken() {
            // given
            doNothing().when(authService).validatesAccessToken(VALID_ACCESS_TOKEN);

            // when
            WebTestClient.ResponseSpec response = webTestClient.patch()
                                                               .uri("/reviews/1")
                                                               .header("Authorization", BEARER + VALID_ACCESS_TOKEN)
                                                               .exchange();

            // then
            response.expectStatus().isNoContent();
        }
    }
}
