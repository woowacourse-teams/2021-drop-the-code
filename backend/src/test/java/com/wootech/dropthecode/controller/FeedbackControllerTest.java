package com.wootech.dropthecode.controller;

import java.util.Arrays;

import com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils;
import com.wootech.dropthecode.dto.request.FeedbackSearchCondition;
import com.wootech.dropthecode.dto.response.FeedbackPaginationResponse;
import com.wootech.dropthecode.dto.response.FeedbackResponse;
import com.wootech.dropthecode.dto.response.ProfileResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.RestDocumentationContextProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils.OBJECT_MAPPER;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FeedbackControllerTest extends RestApiDocumentTest {

    @Autowired
    FeedbackController feedbackController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, feedbackController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, feedbackController);
    }

    @DisplayName("피드백 목록 조회 테스트 - 성공")
    @Test
    void findAllFeedbackTest() throws Exception {
        ProfileResponse fafi = ProfileResponse.builder()
                                              .id(1L)
                                              .name("파피")
                                              .imageUrl("https://dropthecode.co.kr/fafi.jpg")
                                              .build();
        ProfileResponse allie = ProfileResponse.builder()
                                               .id(2L)
                                               .name("알리")
                                               .imageUrl("https://dropthecode.co.kr/allie.jpg")
                                               .build();

        FeedbackPaginationResponse response = new FeedbackPaginationResponse(
                Arrays.asList(
                        FeedbackResponse.builder()
                                        .id(1L)
                                        .star(5)
                                        .comment("리뷰가 구체적이어서 좋았습니다!")
                                        .studentProfile(fafi)
                                        .build(),
                        FeedbackResponse.builder()
                                        .id(2L)
                                        .star(1)
                                        .comment("리뷰가 좋았습니다!")
                                        .studentProfile(allie)
                                        .build()
                ),
                1
        );
        given(feedbackService.findAll(isA(FeedbackSearchCondition.class), isA(Pageable.class))).willReturn(response);

        this.restDocsMockMvc
                .perform(get("/feedbacks?teacherId=1&studentId=2&sort=star,desc"))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(response)))
                .andDo(print());
    }
}
