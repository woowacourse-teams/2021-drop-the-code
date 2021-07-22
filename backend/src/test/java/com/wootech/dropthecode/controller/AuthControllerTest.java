package com.wootech.dropthecode.controller;

import com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.dto.response.AccessTokenResponse;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.service.OauthService;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends RestApiDocumentTest {

    @Autowired
    private AuthController authController;

    @MockBean
    private OauthService oauthService;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, authController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, authController);
    }

    @Test
    @DisplayName("github 아이디로 로그인")
    void loginWithGithub() throws Exception {
        // given
        LoginResponse loginResponse = new LoginResponse(1L, NAME, EMAIL, IMAGE_URL, Role.STUDENT, BEARER, ACCESS_TOKEN, REFRESH_TOKEN);

        given(oauthService.login(any())).willReturn(loginResponse);

        // when
        ResultActions result = this.restDocsMockMvc.perform(
                get("/login/oauth?providerName=" + GITHUB + "&code=" + CODE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(1L))
              .andExpect(jsonPath("$.name").value(NAME))
              .andExpect(jsonPath("$.email").value(EMAIL))
              .andExpect(jsonPath("$.imageUrl").value(IMAGE_URL))
              .andExpect(jsonPath("$.role").value(STUDENT_ROLE))
              .andExpect(jsonPath("$.tokenType").value(BEARER))
              .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
              .andExpect(jsonPath("$.refreshToken").value(REFRESH_TOKEN));
    }

    @Test
    @DisplayName("access token 갱신 - refresh token이 유효한 경우")
    void refreshAccessToken() throws Exception {
        // given
        given(authService.refreshAccessToken(anyString(), any()))
                .willReturn(new AccessTokenResponse(NEW_ACCESS_TOKEN));

        // when
        ResultActions result = this.restDocsMockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(userToken())
                .param("refreshToken", REFRESH_TOKEN));

        // then
        result.andExpect(status().isOk())
              .andExpect(jsonPath("$.accessToken").value(NEW_ACCESS_TOKEN));
    }

    @Test
    @DisplayName("access token 갱신 - refresh token이 유효하지 않은 경우")
    void refreshAccessTokenWithInvalidRefreshToken() throws Exception {
        // given
        doThrow(new AuthorizationException("refresh token이 유효하지 않습니다."))
                .when(authService).refreshAccessToken(anyString(), any());

        // when
        ResultActions result = this.failRestDocsMockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(userToken())
                .param("refreshToken", REFRESH_TOKEN));

        // then
        result.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그아웃 - refresh token 삭제")
    void logout() throws Exception {
        // given
        doNothing().when(authService).logout(ACCESS_TOKEN);

        // when
        ResultActions result = this.restDocsMockMvc.perform(post("/logout")
                .with(userToken()));

        // then
        result.andExpect(status().isNoContent());
    }
}
