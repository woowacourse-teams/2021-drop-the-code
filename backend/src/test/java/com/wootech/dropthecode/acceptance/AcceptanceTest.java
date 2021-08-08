package com.wootech.dropthecode.acceptance;

import com.wootech.dropthecode.domain.oauth.InMemoryProviderRepository;
import com.wootech.dropthecode.domain.oauth.OauthProvider;
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
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    private int port;

    @MockBean
    protected InMemoryProviderRepository inMemoryProviderRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected LoginResponse 로그인되어_있음() {
        ExtractableResponse<Response> response = 로그인_요청();
        return response.as(LoginResponse.class);
    }

    protected ErrorResponse 예외_결과(ExtractableResponse<Response> response) {
        return response.as(ErrorResponse.class);
    }

    protected ExtractableResponse<Response> 로그인_요청() {
        fakeGithubProviderResponse();

        return RestAssured.given()
                          .log().all()
                          .when()
                          .get("/login/oauth?providerName=github&code=authorizationCode")
                          .then()
                          .log().all()
                          .statusCode(HttpStatus.OK.value())
                          .extract();
    }

    private void fakeGithubProviderResponse() {
        String serverAddress = "http://localhost:" + port;
        given(inMemoryProviderRepository.findByProviderName("github"))
                .willReturn(OauthProvider.builder()
                                         .clientId("fakeClientId")
                                         .clientSecret("fakeClientSecret")
                                         .tokenUrl(serverAddress + "/fake/login/oauth/access_token")
                                         .userInfoUrl(serverAddress + "/fake/user")
                                         .build());
    }
}
