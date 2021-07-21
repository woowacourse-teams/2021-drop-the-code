package com.wootech.dropthecode.config.auth.controller;

import com.wootech.dropthecode.config.auth.dto.response.LoginResponse;
import com.wootech.dropthecode.config.auth.service.OauthService;
import com.wootech.dropthecode.controller.RestApiDocumentTest;
import com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils;
import com.wootech.dropthecode.domain.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.ResultActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OauthController.class)
class OauthControllerTest extends RestApiDocumentTest {
    private static final String CODE = "XfWjrPh7lFumDNFpd3K5";
    private static final String GITHUB = "github";
    private static final String NAME = "air";
    private static final String EMAIL = "air.junseo@gmail.com";
    private static final String IMAGE_URL = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";
    private static final String STUDENT_ROLE = "STUDENT";
    private static final String ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final String REFRESH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Autowired
    private OauthController oauthController;

    @MockBean
    private OauthService oauthService;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, oauthController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, oauthController);
    }

    @Test
    @DisplayName("github 아이디로 로그인")
    void loginWithGithub() throws Exception {
        // given
        LoginResponse loginResponse = new LoginResponse(NAME, EMAIL, IMAGE_URL, Role.STUDENT, ACCESS_TOKEN, REFRESH_TOKEN);

        given(oauthService.login(any())).willReturn(loginResponse);

        // when
        ResultActions result = this.restDocsMockMvc.perform(
                get("/login/oauth?providerName=" + GITHUB + "&code=" + CODE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk());
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").value(NAME));
        result.andExpect(jsonPath("$.email").value(EMAIL));
        result.andExpect(jsonPath("$.imageUrl").value(IMAGE_URL));
        result.andExpect(jsonPath("$.role").value(STUDENT_ROLE));
        result.andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN));
        result.andExpect(jsonPath("$.refreshToken").value(REFRESH_TOKEN));
    }
}
