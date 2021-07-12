package com.wootech.dropthecode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(controllers = {
        MemberController.class,
        ReviewController.class
})
public abstract class RestApiDocumentTest {

    protected MockMvc restDocsMockMvc;
    protected MockMvc failRestDocsMockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private MemberController memberController;

    @Autowired
    private ReviewController reviewController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, memberController, reviewController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, memberController, reviewController);
    }
}
