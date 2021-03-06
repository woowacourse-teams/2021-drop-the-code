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

import org.springframework.beans.factory.annotation.Autowired;
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

class MemberControllerTest extends RestApiDocumentTest {

    @Autowired
    private MemberController memberController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, memberController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, memberController);
    }

    @DisplayName("????????? ??? ?????? ?????? ??????")
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

    @DisplayName("?????? ?????? ?????? ????????? - ??????")
    @Test
    void deleteMemberMyselfTest() throws Exception {
        this.restDocsMockMvc.perform(delete("/members/me").with(userToken()))
                            .andExpect(status().isNoContent())
                            .andDo(print());
    }

    @DisplayName("?????? ?????? ????????? - ??????")
    @Test
    void deleteMemberTest() throws Exception {
        this.restDocsMockMvc.perform(delete("/members/1"))
                            .andExpect(status().isNoContent())
                            .andDo(print());
    }

    @DisplayName("????????? ?????? ????????? - ??????")
    @Test
    void registerTeacherTest() throws Exception {
        List<TechSpec> techSpecs = Arrays.asList(
                new TechSpec("java", Arrays.asList("spring", "servlet")),
                new TechSpec("javascript", Arrays.asList("vue", "react"))
        );
        TeacherRegistrationRequest request
                = new TeacherRegistrationRequest("????????? ??????????????????.", "???????????????.", 3, techSpecs);

        this.restDocsMockMvc.perform(post("/teachers")
                .with(userToken())
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isCreated())
                            .andDo(print());
    }

    @DisplayName("????????? ?????? ????????? - ?????? ?????? ???????????? ???????????? ?????? ?????? ??????")
    @Test
    void registerTeacherFailTest() throws Exception {
        List<TechSpec> techSpecs = Arrays.asList(
                new TechSpec("java", Arrays.asList("spring", "servlet")),
                new TechSpec("javascript", Arrays.asList("vue", "react"))
        );
        TeacherRegistrationRequest request =
                new TeacherRegistrationRequest("????????? ??????????????????.", "???????????????.", null, techSpecs);

        this.failRestDocsMockMvc.perform(post("/teachers")
                .with(userToken())
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest())
                                .andDo(print());
    }

    @DisplayName("????????? ?????? ????????? - ??????")
    @Test
    void updateTeacherTest() throws Exception {
        List<TechSpec> techSpecs = Arrays.asList(
                new TechSpec("java", Arrays.asList("spring", "servlet")),
                new TechSpec("javascript", Arrays.asList("vue", "react"))
        );
        TeacherRegistrationRequest request
                = new TeacherRegistrationRequest("????????? ??????????????????.", "???????????????.", 3, techSpecs);

        this.restDocsMockMvc.perform(put("/teachers/me")
                .with(userToken())
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isNoContent())
                            .andDo(print());
    }

    @DisplayName("????????? ?????? ????????? - ?????? ?????? ???????????? ???????????? ?????? ?????? ??????")
    @Test
    void updateTeacherFailTest() throws Exception {
        List<TechSpec> techSpecs = Arrays.asList(
                new TechSpec("java", Arrays.asList("spring", "servlet")),
                new TechSpec("javascript", Arrays.asList("vue", "react"))
        );
        TeacherRegistrationRequest request =
                new TeacherRegistrationRequest("????????? ??????????????????.", "???????????????.", null, techSpecs);

        this.failRestDocsMockMvc.perform(put("/teachers/me")
                .with(userToken())
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest())
                                .andDo(print());
    }

    @DisplayName("????????? ?????? ?????? ????????? - ??????")
    @Test
    void findAllTeacherTest() throws Exception {
        TeacherPaginationResponse response = new TeacherPaginationResponse(
                Collections.singletonList(new TeacherProfileResponse(
                                1L,
                                "seed@gmail.com",
                                "seed",
                                "s3://seed.jpg",
                                "githubUrl",
                                "?????? ?????????",
                                "????????? ???????????????????????????.",
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

    @DisplayName("????????? ?????? ?????? ????????? - ?????? ?????? ?????? ?????? ?????? ??????")
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

    @DisplayName("????????? ?????? ?????? ????????? - DB??? ?????? ????????? ????????? ?????? ??????")
    @Test
    void findAllTeacherFailIfLanguageNotExistsTest() throws Exception {

        given(teacherService.findAll(isA(TeacherFilterRequest.class), isA(Pageable.class))).willThrow(new TeacherException("???????????? ?????? ???????????????."));

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

    @DisplayName("????????? ?????? ?????? ????????? - ???????????? ?????? ?????? ???????????? ????????? ?????? ??????")
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

    @DisplayName("????????? ?????? ?????? ????????? - ??????")
    @Test
    void findTeacherTest() throws Exception {

        final TeacherProfileResponse response = new TeacherProfileResponse(
                1L,
                "seed@gmail.com",
                "seed",
                "s3://seed.jpg",
                "githubUrl",
                "?????? ?????????",
                "????????? ???????????????????????????.",
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

    @DisplayName("????????? ?????? ?????? ????????? - ???????????? ?????? ????????? ID??? ?????? ??????")
    @Test
    void findTeacherFailIfIdIsInvalidTest() throws Exception {
        given(teacherService.findTeacherResponseById(1L)).willThrow(new TeacherException("???????????? ?????? ???????????? ID ?????????."));

        this.restDocsMockMvc
                .perform(get("/teachers/1"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("????????? ?????? ????????? - ??????")
    @Test
    void deleteTeacherTest() throws Exception {
        this.restDocsMockMvc.perform(delete("/teachers/me").with(userToken()))
                            .andExpect(status().isNoContent())
                            .andDo(print());
    }
}
