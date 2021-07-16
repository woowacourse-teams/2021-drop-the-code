package com.wootech.dropthecode.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherFilterRequest;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.dto.response.*;
import com.wootech.dropthecode.service.TeacherService;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.controller.RestDocsMockMvcUtils.OBJECT_MAPPER;
import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MemberControllerTest extends RestApiDocumentTest {

    @MockBean
    private TeacherService teacherService;

    @DisplayName("AccessToken과 유저 정보를 가져오는 테스트")
    @Test
    void oauthTest() throws Exception {
        this.restDocsMockMvc.perform(get("/login/oauth").param("code", "1234")
                                                        .param("redirectUrl", "/main"))
                            .andExpect(status().isFound())
                            .andExpect(cookie().value("jwt", any(String.class)))
                            .andExpect(cookie().value("name", any(String.class)))
                            .andExpect(cookie().value("email", any(String.class)))
                            .andExpect(cookie().value("imageUrl", any(String.class)))
                            .andDo(print());
    }

    @DisplayName("AccessToken과 유저 정보를 가져올 때 필수 requestParam이 없을 때 실패하는 테스트")
    @Test
    void oauthFailTest() throws Exception {
        this.failRestDocsMockMvc.perform(get("/login/oauth").param("code", "1234"))
                                .andExpect(status().isBadRequest())
                                .andDo(print());

        this.failRestDocsMockMvc.perform(get("/login/oauth").param("redirectUrl", "/main"))
                                .andExpect(status().isBadRequest())
                                .andDo(print());
    }


    @DisplayName("리뷰어 등록 테스트 - 성공")
    @Test
    void registerTeacherTest() throws Exception {
        List<TechSpec> techSpecs = Arrays.asList(new TechSpec("java", Arrays.asList("Spring", "Servlet")));
        TeacherRegistrationRequest request
                = new TeacherRegistrationRequest(techSpecs, 3, "백엔드 개발자입니다.", "환영합니다.");

        this.restDocsMockMvc.perform(post("/teachers").with(userToken())
                                                      .content(OBJECT_MAPPER.writeValueAsString(request))
                                                      .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isCreated())
                            .andDo(print());
    }

    @DisplayName("리뷰어 등록 테스트 - 필드 값이 하나라도 들어있지 않은 경우 실패")
    @Test
    void registerTeacherFailTest() throws Exception {
        TeacherRegistrationRequest request =
                new TeacherRegistrationRequest(null, 3, "백엔드 개발자입니다.", "환영합니다.");

        this.failRestDocsMockMvc.perform(post("/teachers").with(userToken())
                                                          .content(OBJECT_MAPPER.writeValueAsString(request))
                                                          .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest())
                                .andDo(print());
    }

    //    @DisplayName("리뷰어로 이미 등록되어 있는 사용자가 리뷰어 등록을 할 경우 실패")
    //    @Test
    //    void duplicateTeacherFailTest() {
    //
    //    }

    @DisplayName("리뷰어 목록 조회 테스트 - 성공")
    @Test
    void findAllTeacherTest() throws Exception {
        TeacherPaginationResponse response = new TeacherPaginationResponse(
                Collections.singletonList(
                        new TeacherProfileResponse(1L, "seed@gmail.com", "seed", "s3://seed.jpg", "배민 개발자", "열심히 가르쳐드리겠습니다.", 3, 12, 1.4, new TechSpecResponse(Arrays
                                .asList(new LanguageResponse(1L, "java"), new LanguageResponse(2L, "javascript")), Arrays.asList(new SkillResponse(1L, "spring"), new SkillResponse(2L, "react"))))
                ),
                1
        );

        given(teacherService.findAll(isA(TeacherFilterRequest.class), isA(Pageable.class))).willReturn(response);
        this.restDocsMockMvc
                .perform(get("/teachers?skills=Java&skills=Spring&career=3&size=10&sort=career,desc&page=5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(response)))
                .andDo(print());
    }

//    @DisplayName("리뷰어 목록 조회 테스트 - 필수 필드 값이 없을 경우 실패")
//    @Test
//    void findAllTeacherFailTest() throws Exception {
//        this.failRestDocsMockMvc
//                .perform(get("/teachers")
//                        .param("language", "Java")
//                        .param("skills", "Spring")
//                        .param("career", "3")
//                        .param("size", "")
//                        .param("page", "5"))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }
}
