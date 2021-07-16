package com.wootech.dropthecode.config.auth.controller;

import com.wootech.dropthecode.config.auth.dto.request.AuthorizationRequest;
import com.wootech.dropthecode.config.auth.dto.response.LoginResponse;
import com.wootech.dropthecode.controller.RestApiDocumentTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OauthControllerTest extends RestApiDocumentTest {
    private static final String CODE = "XfWjrPh7lFumDNFpd3K5";
    private static final String GITHUB = "github";
    private static final String NAME = "air";
    private static final String EMAIL = "air.junseo@gmail.com";
    private static final String IMAGE_URL = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";
    private static final String ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final String REFRESH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("github 아이디로 로그인")
    void loginWithGithub() throws Exception {
        // given
        AuthorizationRequest authorizationRequest = new AuthorizationRequest(GITHUB, CODE);
        LoginResponse loginResponse = new LoginResponse(NAME, EMAIL, IMAGE_URL, ACCESS_TOKEN, REFRESH_TOKEN);

        given(oauthService.login(anyString(), any())).willReturn(loginResponse);
        String body = objectMapper.writeValueAsString(authorizationRequest);

        // when
        ResultActions result = this.restDocsMockMvc.perform(
                post("/login/oauth")
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk());
        result.andExpect(cookie().value("refreshToken", REFRESH_TOKEN));
        result.andExpect(jsonPath("$.name").value(NAME));
        result.andExpect(jsonPath("$.email").value(EMAIL));
        result.andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN));
        result.andExpect(jsonPath("$.imageUrl").value(IMAGE_URL));
    }
}
