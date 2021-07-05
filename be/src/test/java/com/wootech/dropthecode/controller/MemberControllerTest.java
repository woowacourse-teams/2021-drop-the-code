package com.wootech.dropthecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(MemberController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("")
    @Test
    void oauthTest() throws Exception {
        final String jwtCookie = ResponseCookie.from("jwt", "aaa.bbb.ccc").build().toString();
        final String nameCookie = ResponseCookie.from("name", "fafi").build().toString();
        final String emailCookie = ResponseCookie.from("email", "test@email.com").build().toString();
        final String imageUrlCookie = ResponseCookie.from("imageUrl", "s3://fafi.jpg").build().toString();

        this.mockMvc.perform(get("/login/oauth").param("code", "1234").param("redirectUrl", "/main"))
                    .andExpect(status().isFound())
                    .andExpect(header().stringValues(HttpHeaders.SET_COOKIE, jwtCookie, nameCookie, emailCookie, imageUrlCookie))
                    .andDo(document("member",
                            requestParameters(
                                    parameterWithName("code").description("인증 토큰을 받아 오기 위한 인증 코드"),
                                    parameterWithName("redirectUrl").description("인증 토큰을 받은 후 리다이렉트 할 URL")),
                            responseHeaders(
                                    headerWithName(HttpHeaders.SET_COOKIE).description("인증 토큰과 사용자 정보를 담은 쿠키")
                            )
                    ));
    }
}
