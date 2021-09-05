package com.wootech.dropthecode.controller;

import com.wootech.dropthecode.ControllerTest;

import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import org.junit.jupiter.api.extension.ExtendWith;

import static capital.scalable.restdocs.misc.AuthorizationSnippet.documentAuthorization;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class RestApiDocumentTest extends ControllerTest {
    protected static final String CODE = "XfWjrPh7lFumDNFpd3K5";
    protected static final String GITHUB = "github";
    protected static final String NAME = "air";
    protected static final String EMAIL = "air.junseo@gmail.com";
    protected static final String IMAGE_URL = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";
    protected static final String GITHUB_URL = "https://github.com/";
    protected static final String STUDENT_ROLE = "STUDENT";
    protected static final String BEARER = "Bearer";
    protected static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    protected static final String REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    protected static final String NEW_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNjI2OTIyNjkyLCJleHAiOjE2MjY5MjYyOTJ9.dsb5uqMS__VcYToB8QrQFVGOkONeDtMyMv4tMXTUuhY";

    protected MockMvc restDocsMockMvc;
    protected MockMvc failRestDocsMockMvc;

    protected RequestPostProcessor userToken() {
        return request -> {
            request.addHeader("Authorization", BEARER + " " + ACCESS_TOKEN);
            return documentAuthorization(request, "User jwt token required.");
        };
    }
}
