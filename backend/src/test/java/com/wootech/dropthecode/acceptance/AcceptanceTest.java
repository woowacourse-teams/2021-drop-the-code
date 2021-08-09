package com.wootech.dropthecode.acceptance;

import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wootech.dropthecode.domain.oauth.InMemoryProviderRepository;
import com.wootech.dropthecode.domain.oauth.OauthProvider;
import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.exception.ErrorResponse;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.BeforeEach;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils.OBJECT_MAPPER;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class AcceptanceTest {

    @LocalServerPort
    private int port;

    @MockBean
    protected InMemoryProviderRepository inMemoryProviderRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected LoginResponse 학생_로그인되어_있음(String name) {
        ExtractableResponse<Response> response = 로그인_요청(name);
        return response.as(LoginResponse.class);
    }

    protected LoginResponse 리뷰어_로그인되어_있음(String name) {
        선생님_등록_요청(name);
        ExtractableResponse<Response> response = 로그인_요청(name);
        return response.as(LoginResponse.class);
    }

    protected LoginResponse 유효하지_않은_로그인() {
        return LoginResponse.builder()
                            .accessToken("INVALID_TOKEN")
                            .build();
    }

    protected void 선생님_등록_요청(String name) {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음(name);

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TechSpec techSpecJavascript = 정삭적인_리뷰어_언어_기술_스택_Javascript();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecJava, techSpecJavascript);

        // when
        try {
            ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected TeacherRegistrationRequest 정삭적인_리뷰어_정보(TechSpec... techSpec) {
        return TeacherRegistrationRequest.builder()
                                         .title("네이버 백엔드 개발자")
                                         .content("열심히 리뷰하겠습니다!")
                                         .career(3)
                                         .techSpecs(Arrays.asList(techSpec))
                                         .build();
    }

    protected TechSpec 정삭적인_리뷰어_언어_기술_스택_Java() {
        return TechSpec.builder().language("java").skills(Collections.singletonList("spring")).build();
    }

    protected TechSpec 정삭적인_리뷰어_언어_기술_스택_Javascript() {
        return TechSpec.builder().language("javascript").skills(Arrays.asList("react", "vue")).build();
    }

    private ExtractableResponse<Response> 선생님_등록_요청(LoginResponse loginResponse, TeacherRegistrationRequest request) throws JsonProcessingException {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + loginResponse.getAccessToken())
                          .contentType(ContentType.JSON)
                          .body(OBJECT_MAPPER.writeValueAsString(request))
                          .when()
                          .post("/teachers")
                          .then()
                          .log().all()
                          .extract();
    }

    protected ErrorResponse 예외_결과(ExtractableResponse<Response> response) {
        return response.as(ErrorResponse.class);
    }

    protected ExtractableResponse<Response> 로그인_요청(String name) {
        fakeGithubProviderResponse(name);

        return RestAssured.given()
                          .log().all()
                          .when()
                          .get("/login/oauth?providerName=github&code=authorizationCode")
                          .then()
                          .log().all()
                          .statusCode(HttpStatus.OK.value())
                          .extract();
    }

    private void fakeGithubProviderResponse(String name) {
        String serverAddress = "http://localhost:" + port;
        given(inMemoryProviderRepository.findByProviderName("github"))
                .willReturn(OauthProvider.builder()
                                         .clientId("fakeClientId")
                                         .clientSecret("fakeClientSecret")
                                         .tokenUrl(serverAddress + "/fake/login/oauth/access_token")
                                         .userInfoUrl(serverAddress + "/fake/user?name=" + name)
                                         .build());
    }
}
