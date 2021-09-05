package com.wootech.dropthecode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wootech.dropthecode.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected OauthService oauthService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected TeacherService teacherService;

    @MockBean
    protected LanguageService languageService;

    @MockBean
    protected ReviewService reviewService;

    @MockBean
    protected FeedbackService feedbackService;
}
