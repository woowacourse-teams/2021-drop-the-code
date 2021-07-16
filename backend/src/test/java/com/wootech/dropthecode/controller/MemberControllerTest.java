package com.wootech.dropthecode.controller;

import java.util.Arrays;
import java.util.List;

import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static capital.scalable.restdocs.misc.AuthorizationSnippet.documentAuthorization;
import static com.wootech.dropthecode.controller.RestDocsMockMvcUtils.OBJECT_MAPPER;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTest extends RestApiDocumentTest {

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

        this.restDocsMockMvc.perform(post("/teachers").with(userToken())
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

    protected RequestPostProcessor userToken() {
        return request -> {
            request.addHeader("Authorization", "Bearer aaa.bbb.ccc");
            return documentAuthorization(request, "User jwt token required.");
        };
    }

    @DisplayName("리뷰어 목록 조회 테스트 - 성공")
    @Test
    void findAllTeacherTest() throws Exception {
        this.restDocsMockMvc.perform(get("/teachers").param("skills", "Java", "Spring")
                                                     .param("career", "3")
                                                     .param("limit", "10")
                                                     .param("page", "5"))
                            .andExpect(status().isOk())
                            .andDo(print());
    }

    @DisplayName("리뷰어 목록 조회 테스트 - 필수 필드 값이 없을 경우 실패")
    @Test
    void findAllTeacherFailTest() throws Exception {
        this.failRestDocsMockMvc
                .perform(get("/teachers")
                        .param("skills", "Java", "Spring")
                        .param("career", "3")
                        .param("limit", "")
                        .param("page", "5"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
