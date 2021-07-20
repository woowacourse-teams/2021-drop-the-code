package com.wootech.dropthecode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wootech.dropthecode.config.auth.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import org.junit.jupiter.api.extension.ExtendWith;

import static capital.scalable.restdocs.misc.AuthorizationSnippet.documentAuthorization;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class RestApiDocumentTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    protected MockMvc restDocsMockMvc;
    protected MockMvc failRestDocsMockMvc;

    protected RequestPostProcessor userToken() {
        return request -> {
            request.addHeader("Authorization", "Bearer aaa.bbb.ccc");
            return documentAuthorization(request, "User jwt token required.");
        };
    }
}
