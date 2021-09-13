package com.wootech.dropthecode.acceptance;

import java.util.Arrays;
import java.util.Collections;

import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.dto.response.MemberResponse;

import org.springframework.http.HttpStatus;
import org.springframework.util.StopWatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("멤버 관련 인수 테스트")
class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("로그인 한 유저 정보 조회 성공")
    void membersMeSuccess() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 로그인_한_유저_정보_조회_요청(loginResponse);
        MemberResponse memberResponse = response.as(MemberResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        assertThat(memberResponse.getEmail()).isEqualTo("air@email.com");
        assertThat(memberResponse.getRole()).isEqualTo(Role.STUDENT);
    }

    @Test
    @DisplayName("유효하지 않은 access token")
    void membersMeInvalidAccessToken() {
        // given
        LoginResponse loginResponse = 유효하지_않은_로그인();

        // when
        ExtractableResponse<Response> response = 로그인_한_유저_정보_조회_요청(loginResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("멤버 삭제 성공 - 학생")
    void deleteMemberSuccess() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 멤버_삭제_요청(loginResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("멤버 삭제 성공 - 선생님")
    void deleteTeacherMemberSuccess() {
        // given
        LoginResponse loginResponse = 리뷰어_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 멤버_삭제_요청(loginResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("[쉬운 테스트를 위한 메서드]- 멤버 id로 삭제 성공")
    void deleteMemberWithId() {
        // given
        LoginResponse loginResponse = 리뷰어_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 멤버ID로_삭제_요청(loginResponse.getId());

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("유효하지 않은 access token")
    void deleteMemberInvalidAccessToken() {
        // given
        LoginResponse loginResponse = 유효하지_않은_로그인();

        // when
        ExtractableResponse<Response> response = 멤버_삭제_요청(loginResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("선생님 등록 성공")
    void registerTeacherSuccess() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");
        TeacherRegistrationRequest request = 선생님_기본_등록_정보();

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 선생님 기술 스펙에 존재하지 않는 언어가 들어있는 경우")
    void notExistLanguageInTeacherTechSpec() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        TechSpec techSpecNotExistLanguage = 유효하지_않은_스택_존재하지_않는_언어();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecNotExistLanguage);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 선생님 기술 스펙에 존재하지 않는 기술이 들어있는 경우")
    void notExistSkillInTeacherTechSpec() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        TechSpec techSpecNotExistSkill = 유효하지_않은_스택_존재하지_않은_기술();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecNotExistSkill);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 선생님 기술이 언어에 포함되지 않는 경우")
    void notContainSkill() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        TechSpec techSpecSkillsNotInLanguage = 유효하지_않은_스택_기술이_언어에_속해있지_않은_스택();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecSkillsNotInLanguage);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 자기소개 제목이 빈 경우")
    void emptyTitle() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TeacherRegistrationRequest request = 유효하지_않은_리뷰어_정보_제목_없음(techSpecJava);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 자기소개 내용이 빈 경우")
    void emptyContent() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TeacherRegistrationRequest request = 유효하지_않은_리뷰어_정보_내용_없음(techSpecJava);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 경력이 빈 경우")
    void emptyCareer() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TeacherRegistrationRequest request = 유효하지_않은_리뷰어_정보_경력_없음(techSpecJava);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 선생님 기술 스펙이 빈 경우")
    void emptyTechSpec() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음("air");

        TeacherRegistrationRequest request = 유효하지_않은_리뷰어_정보_기술_스택_없음();

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 이미 선생님으로 등록된 경우")
    void alreadyRegisterTeacher() {
        // given
        LoginResponse loginResponse = 리뷰어_로그인되어_있음("air");
        TeacherRegistrationRequest request = 선생님_기본_등록_정보();

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 유효하지 않은 access token")
    void invalidAccessToken() {
        // given
        LoginResponse loginResponse = 유효하지_않은_로그인();
        TeacherRegistrationRequest request = 선생님_기본_등록_정보();

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("선생님 전체 목록 조회 성공")
    void findAllTeacher() {
        // given
        // when
        ExtractableResponse<Response> response = 선생님_전체_목록_조회_요청("java");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("언어 필터 자체가 없는 경우")
    void notExistLanguageFilter() {
        // given
        // when
        ExtractableResponse<Response> response = 선생님_전체_목록_조회_요청(null);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 단일 조회 성공")
    void findTeacherByIdSuccess() {
        // given
        LoginResponse loginResponse = 리뷰어_로그인되어_있음("air");

        // when
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 2; i++) {
            ExtractableResponse<Response> response = 선생님_단일_조회_요청(loginResponse.getId());
        }

        stopWatch.stop();
        System.out.println("걸린 시간 : " + stopWatch.getTotalTimeSeconds());


        // then
        //        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("선생님 단일 조회 존재하지 않는 멤버 id인 경우")
    void findTeacherByNotExistId() {
        // given
        // when
        ExtractableResponse<Response> response = 선생님_단일_조회_요청(1L);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 정보 수정")
    void updateTeacherProfile() {
        // given
        LoginResponse teacher = 리뷰어_로그인되어_있음("air");
        TeacherRegistrationRequest teacherRegistrationRequest = 수정할_선생님_등록_정보();

        // when
        ExtractableResponse<Response> response = 선생님_정보_수정_요청(teacher, teacherRegistrationRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("선생님 등록 취소")
    void deleteTeacherRegistration() {
        // given
        LoginResponse teacher = 리뷰어_로그인되어_있음("air");

        // when
        ExtractableResponse<Response> response = 선생님_등록_취소_요청(teacher);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> 선생님_등록_요청(LoginResponse loginResponse, TeacherRegistrationRequest request) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + loginResponse.getAccessToken())
                          .contentType(APPLICATION_JSON_VALUE)
                          .body(request)
                          .when()
                          .post("/teachers")
                          .then()
                          .log().all()
                          .extract();
    }

    public static ExtractableResponse<Response> 선생님_정보_수정_요청(LoginResponse loginResponse, TeacherRegistrationRequest request) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + loginResponse.getAccessToken())
                          .contentType(APPLICATION_JSON_VALUE)
                          .body(request)
                          .when()
                          .put("/teachers/me")
                          .then()
                          .log().all()
                          .extract();
    }

    private ExtractableResponse<Response> 로그인_한_유저_정보_조회_요청(LoginResponse loginResponse) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + loginResponse.getAccessToken())
                          .when()
                          .get("/members/me")
                          .then()
                          .log().all()
                          .extract();
    }

    private ExtractableResponse<Response> 멤버_삭제_요청(LoginResponse loginResponse) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + loginResponse.getAccessToken())
                          .when()
                          .delete("/members/me")
                          .then()
                          .log().all()
                          .extract();
    }

    private ExtractableResponse<Response> 멤버ID로_삭제_요청(Long id) {
        return RestAssured.given()
                          .log().all()
                          .when()
                          .delete("/members/{id}", id)
                          .then()
                          .log().all()
                          .extract();
    }

    private ExtractableResponse<Response> 선생님_등록_취소_요청(LoginResponse loginResponse) {
        return RestAssured.given()
                          .log().all()
                          .header("Authorization", "Bearer " + loginResponse.getAccessToken())
                          .when()
                          .delete("/teachers/me")
                          .then()
                          .log().all()
                          .extract();
    }

    private ExtractableResponse<Response> 선생님_전체_목록_조회_요청(String language) {
        return RestAssured.given()
                          .log().all()
                          .contentType(ContentType.JSON)
                          .when()
                          .param("language", language)
                          .get("/teachers")
                          .then()
                          .log().all()
                          .extract();
    }

    private ExtractableResponse<Response> 선생님_단일_조회_요청(Long id) {
        return RestAssured.given()
                          .log().all()
                          .contentType(ContentType.JSON)
                          .when()
                          .get("/teachers/" + id)
                          .then()
                          .log().all()
                          .extract();
    }

    public static TeacherRegistrationRequest 선생님_기본_등록_정보() {
        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TechSpec techSpecJavascript = 정삭적인_리뷰어_언어_기술_스택_Javascript();
        return 정삭적인_리뷰어_정보(techSpecJava, techSpecJavascript);
    }

    public static TeacherRegistrationRequest 수정할_선생님_등록_정보() {
        TechSpec techSpecKotlin = 정삭적인_리뷰어_언어_기술_스택_Kotlin();
        TechSpec techSpecJavascript = 정삭적인_리뷰어_언어_기술_스택_Javascript();
        return 수정할_리뷰어_정보(techSpecKotlin, techSpecJavascript);
    }


    public static TechSpec 정삭적인_리뷰어_언어_기술_스택_Java() {
        return TechSpec.builder()
                       .language("java")
                       .skills(Collections.singletonList("spring"))
                       .build();
    }

    public static TechSpec 정삭적인_리뷰어_언어_기술_스택_Javascript() {
        return TechSpec.builder()
                       .language("javascript")
                       .skills(Arrays.asList("react", "vue"))
                       .build();
    }

    public static TeacherRegistrationRequest 정삭적인_리뷰어_정보(TechSpec... techSpec) {
        return TeacherRegistrationRequest.builder()
                                         .title("네이버 백엔드 개발자")
                                         .content("열심히 리뷰하겠습니다!")
                                         .career(3)
                                         .techSpecs(Arrays.asList(techSpec))
                                         .build();
    }

    public static TechSpec 정삭적인_리뷰어_언어_기술_스택_Kotlin() {
        return TechSpec.builder()
                       .language("kotlin")
                       .skills(Collections.singletonList("spring"))
                       .build();
    }

    public static TeacherRegistrationRequest 수정할_리뷰어_정보(TechSpec... techSpec) {
        return TeacherRegistrationRequest.builder()
                                         .title("배민 백엔드 개발자")
                                         .content("열심히 리뷰하겠습니다!")
                                         .career(5)
                                         .techSpecs(Arrays.asList(techSpec))
                                         .build();
    }

    private TeacherRegistrationRequest 유효하지_않은_리뷰어_정보_제목_없음(TechSpec... techSpec) {
        return TeacherRegistrationRequest.builder()
                                         .content("열심히 리뷰하겠습니다!")
                                         .career(3)
                                         .techSpecs(Arrays.asList(techSpec))
                                         .build();
    }

    private TeacherRegistrationRequest 유효하지_않은_리뷰어_정보_내용_없음(TechSpec... techSpec) {
        return TeacherRegistrationRequest.builder()
                                         .title("네이버 백엔드 개발자")
                                         .career(3)
                                         .techSpecs(Arrays.asList(techSpec))
                                         .build();
    }

    private TeacherRegistrationRequest 유효하지_않은_리뷰어_정보_경력_없음(TechSpec... techSpec) {
        return TeacherRegistrationRequest.builder()
                                         .title("네이버 백엔드 개발자")
                                         .content("열심히 리뷰하겠습니다!")
                                         .techSpecs(Arrays.asList(techSpec))
                                         .build();
    }

    private TeacherRegistrationRequest 유효하지_않은_리뷰어_정보_기술_스택_없음() {
        return TeacherRegistrationRequest.builder()
                                         .title("네이버 백엔드 개발자")
                                         .content("열심히 리뷰하겠습니다!")
                                         .career(3)
                                         .build();
    }

    private TechSpec 유효하지_않은_스택_기술이_언어에_속해있지_않은_스택() {
        return TechSpec.builder()
                       .language("java")
                       .skills(Collections.singletonList("react"))
                       .build();
    }

    private TechSpec 유효하지_않은_스택_존재하지_않는_언어() {
        return TechSpec.builder()
                       .language("go")
                       .skills(Collections.singletonList("spring"))
                       .build();
    }

    private TechSpec 유효하지_않은_스택_존재하지_않은_기술() {
        return TechSpec.builder()
                       .language("java")
                       .skills(Collections.singletonList("jpa"))
                       .build();
    }
}
