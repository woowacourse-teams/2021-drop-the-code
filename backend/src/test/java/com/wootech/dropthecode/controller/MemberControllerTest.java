package com.wootech.dropthecode.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherFilterRequest;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.dto.response.*;
import com.wootech.dropthecode.exception.TeacherException;
import com.wootech.dropthecode.service.MemberService;
import com.wootech.dropthecode.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.ResultActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils.OBJECT_MAPPER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
public class MemberControllerTest extends RestApiDocumentTest {

    @Autowired
    private MemberController memberController;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, memberController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, memberController);
    }

    @DisplayName("로그인 한 유저 정보 조회")
    @Test
    void membersMe() throws Exception {
        // given
        MemberResponse memberResponse = new MemberResponse(1L, "air", "air.junseo@gmail.com", "s3://image url", "github url", Role.TEACHER);
        given(memberService.findByLoginMember(any())).willReturn(memberResponse);

        // when
        ResultActions resultActions = this.restDocsMockMvc.perform(get("/members/me").with(userToken()));

        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.name").value("air"))
                     .andExpect(jsonPath("$.email").value("air.junseo@gmail.com"))
                     .andExpect(jsonPath("$.imageUrl").value("s3://image url"))
                     .andExpect(jsonPath("$.role").value("TEACHER"));

    }

    @DisplayName("멤버 삭제 테스트 - 성공")
    @Test
    void deleteMemberTest() throws Exception {
        this.restDocsMockMvc.perform(delete("/members/1"))
                            .andExpect(status().isNoContent())
                            .andDo(print());
    }

    @DisplayName("리뷰어 등록 테스트 - 성공")
    @Test
    void registerTeacherTest() throws Exception {
        List<TechSpec> techSpecs = Arrays.asList(
                new TechSpec("java", Arrays.asList("spring", "servlet")),
                new TechSpec("javascript", Arrays.asList("vue", "react"))
        );
        TeacherRegistrationRequest request
                = new TeacherRegistrationRequest("백엔드 개발자입니다.", "환영합니다.", 3, techSpecs);

        this.restDocsMockMvc.perform(post("/teachers")
                .with(userToken())
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isCreated())
                            .andDo(print());
    }

    @DisplayName("리뷰어 등록 테스트 - 필드 값이 하나라도 들어있지 않은 경우 실패")
    @Test
    void registerTeacherFailTest() throws Exception {
        List<TechSpec> techSpecs = Arrays.asList(
                new TechSpec("java", Arrays.asList("spring", "servlet")),
                new TechSpec("javascript", Arrays.asList("vue", "react"))
        );
        TeacherRegistrationRequest request =
                new TeacherRegistrationRequest("백엔드 개발자입니다.", "환영합니다.", null, techSpecs);

        this.failRestDocsMockMvc.perform(post("/teachers")
                .with(userToken())
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest())
                                .andDo(print());
    }

    @DisplayName("리뷰어 수정 테스트 - 성공")
    @Test
    void updateTeacherTest() throws Exception {
        List<TechSpec> techSpecs = Arrays.asList(
                new TechSpec("java", Arrays.asList("spring", "servlet")),
                new TechSpec("javascript", Arrays.asList("vue", "react"))
        );
        TeacherRegistrationRequest request
                = new TeacherRegistrationRequest("백엔드 개발자입니다.", "환영합니다.", 3, techSpecs);

        this.restDocsMockMvc.perform(put("/teachers/me")
                .with(userToken())
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isNoContent())
                            .andDo(print());
    }

    @DisplayName("리뷰어 등록 테스트 - 필드 값이 하나라도 들어있지 않은 경우 실패")
    @Test
    void updateTeacherFailTest() throws Exception {
        List<TechSpec> techSpecs = Arrays.asList(
                new TechSpec("java", Arrays.asList("spring", "servlet")),
                new TechSpec("javascript", Arrays.asList("vue", "react"))
        );
        TeacherRegistrationRequest request =
                new TeacherRegistrationRequest("백엔드 개발자입니다.", "환영합니다.", null, techSpecs);

        this.failRestDocsMockMvc.perform(post("/teachers/me")
                .with(userToken())
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest())
                                .andDo(print());
    }

    @DisplayName("리뷰어 목록 조회 테스트 - 성공")
    @Test
    void findAllTeacherTest() throws Exception {
        TeacherPaginationResponse response = new TeacherPaginationResponse(
                Collections.singletonList(new TeacherProfileResponse(
                                1L,
                                "seed@gmail.com",
                                "seed",
                                "s3://seed.jpg",
                                "githubUrl",
                                "배민 개발자",
                                "열심히 가르쳐드리겠습니다.",
                                3,
                                12,
                                1.4,
                                new TechSpecResponse(
                                        Arrays.asList(
                                                new LanguageResponse(1L, "java"),
                                                new LanguageResponse(2L, "javascript")),
                                        Arrays.asList(
                                                new SkillResponse(1L, "spring"),
                                                new SkillResponse(2L, "react"))
                                )
                        )
                ),
                1
        );

        given(teacherService.findAll(isA(TeacherFilterRequest.class), isA(Pageable.class))).willReturn(response);
        this.restDocsMockMvc
                .perform(get("/teachers?language=java&skills=spring&career=3&page=5&size=10&sort=career,desc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(response)))
                .andDo(print());
    }

    @DisplayName("리뷰어 목록 조회 테스트 - 필수 필드 값이 없을 경우 실패")
    @Test
    void findAllTeacherFailTest() throws Exception {
        this.failRestDocsMockMvc
                .perform(get("/teachers")
                        .param("language", "")
                        .param("skills", "spring")
                        .param("career", "3")
                        .param("size", "2")
                        .param("page", "1"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("리뷰어 목록 조회 테스트 - DB에 없는 언어를 입력할 경우 실패")
    @Test
    void findAllTeacherFailIfLanguageNotExistsTest() throws Exception {

        given(teacherService.findAll(isA(TeacherFilterRequest.class), isA(Pageable.class))).willThrow(new TeacherException("존재하지 않는 언어입니다."));

        this.failRestDocsMockMvc
                .perform(get("/teachers")
                        .param("language", "golang")
                        .param("skills", "spring")
                        .param("career", "3")
                        .param("size", "2")
                        .param("page", "1"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("리뷰어 목록 조회 테스트 - 허용하지 않은 정렬 조건으로 검색할 경우 실패")
    @Test
    void findAllTeacherFailIfSortIsInvalidTest() throws Exception {

        String sort = "test";
        ClassTypeInformation<TeacherProfile> type = ClassTypeInformation.from(TeacherProfile.class);

        given(teacherService.findAll(isA(TeacherFilterRequest.class), isA(Pageable.class)))
                .willThrow(new PropertyReferenceException(sort, type, new Stack<>()));

        this.failRestDocsMockMvc
                .perform(get("/teachers")
                        .param("language", "golang")
                        .param("skills", "spring")
                        .param("career", "3")
                        .param("size", "2")
                        .param("page", "1"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("리뷰어 단일 조회 테스트 - 성공")
    @Test
    void findTeacherTest() throws Exception {

        final TeacherProfileResponse response = new TeacherProfileResponse(
                1L,
                "seed@gmail.com",
                "seed",
                "s3://seed.jpg",
                "githubUrl",
                "배민 개발자",
                "열심히 가르쳐드리겠습니다.",
                3,
                12,
                1.4,
                new TechSpecResponse(
                        Arrays.asList(
                                new LanguageResponse(1L, "java"),
                                new LanguageResponse(2L, "javascript")),
                        Arrays.asList(
                                new SkillResponse(1L, "spring"),
                                new SkillResponse(2L, "react"))
                )
        );

        given(teacherService.findTeacherResponseById(1L)).willReturn(response);

        this.restDocsMockMvc
                .perform(get("/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(response)))
                .andDo(print());
    }

    @DisplayName("리뷰어 단일 조회 테스트 - 유효하지 않은 회원의 ID일 경우 실패")
    @Test
    void findTeacherFailIfIdIsInvalidTest() throws Exception {
        given(teacherService.findTeacherResponseById(1L)).willThrow(new TeacherException("존재하지 않는 리뷰어의 ID 입니다."));

        this.restDocsMockMvc
                .perform(get("/teachers/1"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
