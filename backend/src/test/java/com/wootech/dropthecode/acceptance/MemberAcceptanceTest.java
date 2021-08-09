package com.wootech.dropthecode.acceptance;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.domain.Skill;
import com.wootech.dropthecode.domain.bridge.LanguageSkill;
import com.wootech.dropthecode.domain.oauth.InMemoryProviderRepository;
import com.wootech.dropthecode.domain.oauth.OauthProvider;
import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.dto.response.MemberResponse;
import com.wootech.dropthecode.repository.LanguageRepository;
import com.wootech.dropthecode.repository.SkillRepository;
import com.wootech.dropthecode.repository.bridge.LanguageSkillRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils.OBJECT_MAPPER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("멤버 관련 인수 테스트")
public class MemberAcceptanceTest extends AcceptanceTest{

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    LanguageSkillRepository languageSkillRepository;

    @MockBean
    private InMemoryProviderRepository inMemoryProviderRepository;

    @BeforeEach
    void initializeData() {
        Map<Long, Language> languageMap = insertLanguage()
                .stream()
                .collect(Collectors.toMap(Language::getId, Function.identity()));

        Map<Long, Skill> skillMap = insertSkill()
                .stream()
                .collect(Collectors.toMap(Skill::getId, Function.identity()));

        insertLanguageSkill(languageMap, skillMap);

    }

    public List<Language> insertLanguage() {
        List<Language> languages = Arrays.asList(
                Language.builder().name("java").build(),
                Language.builder().name("javascript").build(),
                Language.builder().name("python").build(),
                Language.builder().name("kotlin").build(),
                Language.builder().name("c").build()
        );
        return languageRepository.saveAll(languages);
    }

    public List<Skill> insertSkill() {
        List<Skill> skills = Arrays.asList(
                Skill.builder().name("spring").build(),
                Skill.builder().name("vue").build(),
                Skill.builder().name("react").build(),
                Skill.builder().name("angular").build(),
                Skill.builder().name("django").build()
        );
        return skillRepository.saveAll(skills);
    }

    public void insertLanguageSkill(Map<Long, Language> languageMap, Map<Long, Skill> skillMap) {
        List<LanguageSkill> languageSkills = Arrays.asList(
                new LanguageSkill(languageMap.get(1L), skillMap.get(1L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(2L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(3L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(4L)),
                new LanguageSkill(languageMap.get(3L), skillMap.get(5L)),
                new LanguageSkill(languageMap.get(4L), skillMap.get(1L))
        );
        languageSkillRepository.saveAll(languageSkills);
    }

    @Test
    @DisplayName("로그인 한 유저 정보 조회 성공")
    void membersMeSuccess() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

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

    @Test
    @DisplayName("멤버 삭제 성공")
    void deleteMemberSuccess() {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

        // when
        ExtractableResponse<Response> response = 멤버_삭제_요청(loginResponse);

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

    @Test
    @DisplayName("선생님 등록 성공")
    void registerTeacherSuccess() throws Exception {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TechSpec techSpecJavascript = 정삭적인_리뷰어_언어_기술_스택_Javascript();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecJava, techSpecJavascript);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
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

    @Test
    @DisplayName("선생님 등록 실패 - 선생님 기술 스펙에 존재하지 않는 언어가 들어있는 경우")
    void notExistLanguageInTeacherTechSpec() throws Exception {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

        TechSpec techSpecNotExistLanguage = 유효하지_않은_스택_존재하지_않는_언어();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecNotExistLanguage);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 선생님 기술 스펙에 존재하지 않는 기술이 들어있는 경우")
    void notExistSkillInTeacherTechSpec() throws Exception {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

        TechSpec techSpecNotExistSkill = 유효하지_않은_스택_존재하지_않은_기술();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecNotExistSkill);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 선생님 기술이 언어에 포함되지 않는 경우")
    void notContainSkill() throws Exception {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

        TechSpec techSpecSkillsNotInLanguage = 유효하지_않은_스택_기술이_언어에_속해있지_않은_스택();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecSkillsNotInLanguage);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 자기소개 제목이 빈 경우")
    void emptyTitle() throws Exception {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TeacherRegistrationRequest request = 유효하지_않은_리뷰어_정보_제목_없음(techSpecJava);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 자기소개 내용이 빈 경우")
    void emptyContent() throws Exception {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TeacherRegistrationRequest request = 유효하지_않은_리뷰어_정보_내용_없음(techSpecJava);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 경력이 빈 경우")
    void emptyCareer() throws Exception {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TeacherRegistrationRequest request = 유효하지_않은_리뷰어_정보_경력_없음(techSpecJava);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 선생님 기술 스펙이 빈 경우")
    void emptyTechSpec() throws Exception {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

        TeacherRegistrationRequest request = 유효하지_않은_리뷰어_정보_기술_스택_없음();

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 이미 선생님으로 등록된 경우")
    void alreadyRegisterTeacher() throws Exception {
        // given
        LoginResponse loginResponse = 리뷰어_로그인되어_있음();

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TechSpec techSpecJavascript = 정삭적인_리뷰어_언어_기술_스택_Javascript();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecJava, techSpecJavascript);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 등록 실패 - 유효하지 않은 access token")
    void invalidAccessToken() throws Exception {
        // given
        LoginResponse loginResponse = 유효하지_않은_로그인();

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TechSpec techSpecJavascript = 정삭적인_리뷰어_언어_기술_스택_Javascript();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecJava, techSpecJavascript);

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
        ExtractableResponse<Response> response = RestAssured.given()
                                                            .log().all()
                                                            .contentType(ContentType.JSON)
                                                            .when()
                                                            .param("language", "java")
                                                            .get("/teachers")
                                                            .then()
                                                            .log().all()
                                                            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("언어 필터 자체가 없는 경우")
    void notExistLanguageFilter() {
        // given
        // when
        ExtractableResponse<Response> response = RestAssured.given()
                                                            .log().all()
                                                            .contentType(ContentType.JSON)
                                                            .when()
                                                            .get("/teachers")
                                                            .then()
                                                            .log().all()
                                                            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("선생님 단일 조회 성공")
    void findTeacherByIdSuccess() throws Exception {
        // given
        LoginResponse loginResponse = 리뷰어_로그인되어_있음();
        // when
        ExtractableResponse<Response> response = RestAssured.given()
                                                            .log().all()
                                                            .contentType(ContentType.JSON)
                                                            .when()
                                                            .get("/teachers/" + loginResponse.getId())
                                                            .then()
                                                            .log().all()
                                                            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("선생님 단일 조회 존재하지 않는 멤버 id인 경우")
    void findTeacherByNotExistId() {
        // given
        // when
        ExtractableResponse<Response> response = RestAssured.given()
                                                            .log().all()
                                                            .contentType(ContentType.JSON)
                                                            .when()
                                                            .get("/teachers/1")
                                                            .then()
                                                            .log().all()
                                                            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public LoginResponse 학생_로그인되어_있음() {
        ExtractableResponse<Response> response = 로그인_요청();
        return response.as(LoginResponse.class);
    }

    public LoginResponse 리뷰어_로그인되어_있음() throws Exception {
        선생님_등록_요청();
        ExtractableResponse<Response> response = 로그인_요청();
        return response.as(LoginResponse.class);
    }

    public LoginResponse 유효하지_않은_로그인() {
        return LoginResponse.builder()
                            .accessToken("INVALID_TOKEN")
                            .build();
    }

    public ExtractableResponse<Response> 로그인_요청() {
        String serverAddress = "http://localhost:" + port;
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

    public void 선생님_등록_요청() throws Exception {
        // given
        LoginResponse loginResponse = 학생_로그인되어_있음();

        TechSpec techSpecJava = 정삭적인_리뷰어_언어_기술_스택_Java();
        TechSpec techSpecJavascript = 정삭적인_리뷰어_언어_기술_스택_Javascript();
        TeacherRegistrationRequest request = 정삭적인_리뷰어_정보(techSpecJava, techSpecJavascript);

        // when
        ExtractableResponse<Response> response = 선생님_등록_요청(loginResponse, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private TeacherRegistrationRequest 정삭적인_리뷰어_정보(TechSpec... techSpec) {
        return TeacherRegistrationRequest.builder()
                                         .title("네이버 백엔드 개발자")
                                         .content("열심히 리뷰하겠습니다!")
                                         .career(3)
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

    private TechSpec 정삭적인_리뷰어_언어_기술_스택_Java() {
        return TechSpec.builder().language("java").skills(Collections.singletonList("spring")).build();
    }

    private TechSpec 정삭적인_리뷰어_언어_기술_스택_Javascript() {
        return TechSpec.builder().language("javascript").skills(Arrays.asList("react", "vue")).build();
    }

    private TechSpec 유효하지_않은_스택_기술이_언어에_속해있지_않은_스택() {
        return TechSpec.builder().language("java").skills(Collections.singletonList("react")).build();
    }

    private TechSpec 유효하지_않은_스택_존재하지_않는_언어() {
        return TechSpec.builder().language("go").skills(Collections.singletonList("spring")).build();
    }

    private TechSpec 유효하지_않은_스택_존재하지_않은_기술() {
        return TechSpec.builder().language("java").skills(Collections.singletonList("jpa")).build();
    }
}
