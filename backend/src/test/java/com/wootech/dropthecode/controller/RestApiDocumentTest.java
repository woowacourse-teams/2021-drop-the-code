package com.wootech.dropthecode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wootech.dropthecode.config.auth.controller.OauthController;
import com.wootech.dropthecode.config.auth.service.OauthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(controllers = {
        MemberController.class,
        ReviewController.class,
        OauthController.class
})
public abstract class RestApiDocumentTest {

    protected MockMvc restDocsMockMvc;
    protected MockMvc failRestDocsMockMvc;

    @MockBean
    protected OauthService oauthService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private MemberController memberController;

    @Autowired
    private ReviewController reviewController;

    @Autowired
    private OauthController oauthController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, memberController, reviewController, oauthController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, memberController, reviewController, oauthController);
    }
}
