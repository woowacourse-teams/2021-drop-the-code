package com.wootech.dropthecode.acceptance;

import java.util.List;

import com.wootech.dropthecode.dto.response.LanguageSkillsResponse;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("언어 관련 인수 테스트")
class LanguageAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("언어 및 기술 전체 목록 조회")
    void getLanguagesAndSkills() {
        // given

        // when
        ExtractableResponse<Response> response = 리뷰_상세_조회_요청();
        List<LanguageSkillsResponse> result = response.jsonPath().getList(".", LanguageSkillsResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).hasSize(5);
        assertThat(result.get(0).getLanguage().getName()).isEqualTo("java");
        assertThat(result.get(0).getSkills().get(0).getName()).isEqualTo("spring");
        assertThat(result.get(1).getLanguage().getName()).isEqualTo("javascript");
        assertThat(result.get(1).getSkills()).extracting("name").contains("vue", "react", "angular");
    }

    public static ExtractableResponse<Response> 리뷰_상세_조회_요청() {
        return RestAssured.given()
                          .log().all()
                          .when()
                          .get("/languages")
                          .then()
                          .log().all()
                          .extract();
    }
}
