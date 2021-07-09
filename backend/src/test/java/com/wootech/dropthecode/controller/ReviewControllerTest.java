package com.wootech.dropthecode.controller;

import com.wootech.dropthecode.dto.request.ReviewCreateRequest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static capital.scalable.restdocs.misc.AuthorizationSnippet.documentAuthorization;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerTest extends RestApiDocumentTest {
    private static final String JWT_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("새로운 리뷰 등록")
    void newReview() throws Exception {
        // given
        String body = objectMapper.writeValueAsString(
                new ReviewCreateRequest(1L, 2L, "title1", "content1", "https://github.com/KJunseo"));

        // when
        ResultActions result = this.restDocsMockMvc.perform(
                post("/reviews")
                        .with(userToken())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(header().string("Location", "/reviews/1"))
              .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("내가 요청한 리뷰 목록")
    void studentReviews() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(
                get("/reviews/student/{id}", 1)
                        .with(userToken()));

        // then
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("내가 리뷰한 목록")
    void teacherReviews() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(
                get("/reviews/teacher/{id}", 1));

        // then
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("리뷰 상세 페이지")
    void reviewDetail() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(
                get("/reviews/{id}", 1));

        // then
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("리뷰 상태 변경")
    void changeReviewProgress() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(patch("/reviews/{id}", 1)
                .with(userToken()));

        // then
        result.andExpect(status().isNoContent());
    }

    protected RequestPostProcessor userToken() {
        return request -> {
            request.addHeader("Authorization", JWT_TOKEN);
            return documentAuthorization(request, "User jwt token required.");
        };
    }
}
