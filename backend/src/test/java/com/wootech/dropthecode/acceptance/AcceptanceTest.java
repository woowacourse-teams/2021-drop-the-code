package com.wootech.dropthecode.acceptance;

import com.wootech.dropthecode.domain.oauth.InMemoryProviderRepository;
import com.wootech.dropthecode.domain.oauth.OauthProvider;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.exception.ErrorResponse;
import com.wootech.dropthecode.util.DatabaseCleanup;
import com.wootech.dropthecode.util.DatabaseDefaultInsert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.BeforeEach;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static com.wootech.dropthecode.acceptance.MemberAcceptanceTest.선생님_기본_등록_정보;
import static com.wootech.dropthecode.acceptance.MemberAcceptanceTest.선생님_등록_요청;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private DatabaseDefaultInsert databaseDefaultInsert;

    @MockBean
    protected InMemoryProviderRepository inMemoryProviderRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
        databaseDefaultInsert.insertDefaultLanguageAndSkill();
    }

    protected LoginResponse 학생_로그인되어_있음(String name) {
        ExtractableResponse<Response> response = 로그인_요청(name);
        return response.as(LoginResponse.class);
    }

    protected LoginResponse 리뷰어_로그인되어_있음(String name) {
        ExtractableResponse<Response> response = 로그인_요청(name);
        LoginResponse loginMember = response.as(LoginResponse.class);
        선생님_등록_요청(loginMember, 선생님_기본_등록_정보());
        return loginMember;
    }

    protected LoginResponse 유효하지_않은_로그인() {
        return LoginResponse.builder()
                            .accessToken("INVALID_TOKEN")
                            .build();
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
