package com.wootech.dropthecode.controller;

import java.util.List;

import com.wootech.dropthecode.dto.response.LanguageResponse;
import com.wootech.dropthecode.dto.response.LanguageSkillsResponse;
import com.wootech.dropthecode.dto.response.SkillResponse;
import com.wootech.dropthecode.service.LanguageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.wootech.dropthecode.controller.RestDocsMockMvcUtils.OBJECT_MAPPER;
import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LanguageController.class)
public class LanguageControllerTest extends RestApiDocumentTest {

    @Autowired
    private LanguageController languageController;

    @MockBean
    private LanguageService languageService;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, languageController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, languageController);
    }

    @Test
    @DisplayName("언어/기술 스택 목록 조회 - 성공")
    void studentReviews() throws Exception {
        List<LanguageSkillsResponse> response = asList(
                new LanguageSkillsResponse(new LanguageResponse(1L, "java"), asList(new SkillResponse(1L, "spring"), new SkillResponse(2L, "jpa"))),
                new LanguageSkillsResponse(new LanguageResponse(2L, "javascript"), asList(new SkillResponse(3L, "vue"), new SkillResponse(4L, "react")))
        );

        given(languageService.findAll()).willReturn(response);
        this.restDocsMockMvc
                .perform(get("/languages")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(response)))
                .andDo(print());
    }
}
