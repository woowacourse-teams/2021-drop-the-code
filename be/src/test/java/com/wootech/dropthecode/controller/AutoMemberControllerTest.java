package com.wootech.dropthecode.controller;

import java.util.Arrays;

import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static capital.scalable.restdocs.misc.AuthorizationSnippet.documentAuthorization;
import static com.wootech.dropthecode.controller.RestDocsMockMvcUtils.OBJECT_MAPPER;
import static org.hamcrest.Matchers.any;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AutoMemberControllerTest extends RestApiDocumentTest {

    @DisplayName("")
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

    @DisplayName("")
    @Test
    void registerTeacherTest() throws Exception {
        TeacherRegistrationRequest request
                = new TeacherRegistrationRequest(Arrays.asList("java", "spring"), 3, "백엔드 개발자입니다.", "환영합니다.");

        this.restDocsMockMvc.perform(post("/teachers").with(userToken())
                                                      .content(OBJECT_MAPPER.writeValueAsString(request))
                                                      .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isCreated())
                            .andDo(print());
    }

    protected RequestPostProcessor userToken() {
        return request -> {
            request.addHeader("Authorization", "Bearer aaa.bbb.ccc");
            return documentAuthorization(request, "User jwt token required.");
        };
    }

    @DisplayName("")
    @Test
    void findAllTeacherTest() throws Exception {
        this.restDocsMockMvc.perform(get("/teachers").param("skills", "Java", "Spring")
                                                     .param("career", "3")
                                                     .param("limit", "10")
                                                     .param("page", "5"))
                            .andExpect(status().isOk())
                            .andDo(print());
    }
}
