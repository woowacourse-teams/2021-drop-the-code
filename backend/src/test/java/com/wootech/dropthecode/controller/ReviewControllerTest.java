package com.wootech.dropthecode.controller;

import com.wootech.dropthecode.config.auth.AuthenticationInterceptor;
import com.wootech.dropthecode.config.auth.GetAuthenticationInterceptor;
import com.wootech.dropthecode.dto.request.ReviewCreateRequest;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.exception.GlobalExceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest extends RestApiDocumentTest {

    @Autowired
    private ReviewController reviewController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, reviewController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, reviewController);
    }

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

    @DisplayName("새로운 리뷰 등록 - 필드가 null 인 경우 실패")
    @Test
    void newReviewFailIfFieldIsNullTest() throws Exception {
        // given
        String body = objectMapper.writeValueAsString(
                new ReviewCreateRequest(null, null, " ", "content1", "https://github.com/KJunseo"));

        // when
        final ResultActions result = this.failRestDocsMockMvc
                .perform(post("/reviews").contentType(MediaType.APPLICATION_JSON).content(body));

        // then
        result.andExpect(status().isBadRequest())
              .andDo(print());
    }

    //    @DisplayName("새로운 리뷰 등록 - id(student or teacher)가 DB에 저장되어 있지 않은 경우 실패")
    //    @Test
    //    void newReviewFailIfMemberIdNotExistsTest() throws Exception {
    //        // given
    //        String body = objectMapper.writeValueAsString(
    //                new ReviewCreateRequest(1L, 2L, " ", "content1", "https://github.com/KJunseo"));
    //
    //        // when
    //        final ResultActions result = this.failRestDocsMockMvc
    //                .perform(post("/reviews").contentType(MediaType.APPLICATION_JSON).content(body));
    //
    //        // then
    //        result.andExpect(status().isBadRequest())
    //              .andDo(print());
    //    }

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

    @DisplayName("내가 요청한 리뷰 목록 조회 - Authorization Header 가 없을 경우 실패")
    @Test
    void studentReviewsFailIfAuthorizationHeaderNotExists() throws Exception {
        // given
        doThrow(new AuthorizationException("access token이 유효하지 않습니다."))
                .when(authService).validatesAccessToken(any());
        this.failRestDocsMockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                                                  .addInterceptors(new GetAuthenticationInterceptor(authService))
                                                  .setControllerAdvice(new GlobalExceptionHandler())
                                                  .build();
        // when
        final ResultActions result = this.failRestDocsMockMvc
                .perform(get("/reviews/student/{id}", 1));

        // then
        result.andExpect(status().isUnauthorized())
              .andDo(print());
    }

    //    @DisplayName("내가 요청한 리뷰 목록 조회 - ID에 해당하는 리소스가 없는 경우 실패")
    //    @Test
    //    void studentReviewsFailIfResourceNotExists() throws Exception {
    //        // when
    //        final ResultActions result = this.failRestDocsMockMvc
    //                .perform(get("/reviews/student/{id}", 1));
    //
    //        // then
    //        result.andExpect(status().isNotFound())
    //              .andDo(print());
    //    }

    @Test
    @DisplayName("내가 리뷰한 목록")
    void teacherReviews() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(
                get("/reviews/teacher/{id}", 1));

        // then
        result.andExpect(status().isOk());
    }

    //     TODO ID에 해당하는 리소스가 없는 경우

    @Test
    @DisplayName("리뷰 상세 페이지")
    void reviewDetail() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(
                get("/reviews/{id}", 1));

        // then
        result.andExpect(status().isOk());
    }

    // TODO ID에 해당하는 리소스가 없는 경우

    @Test
    @DisplayName("리뷰 상태 변경")
    void changeReviewProgress() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(patch("/reviews/{id}", 1)
                .with(userToken()));

        // then
        result.andExpect(status().isNoContent());
    }

    @DisplayName("리뷰 상태 변경 - Authorization Header 가 없을 경우 실패")
    @Test
    void changeReviewProgressFailIfAuthorizationHeaderNotExists() throws Exception {
        // given
        doThrow(new AuthorizationException("access token이 유효하지 않습니다."))
                .when(authService).validatesAccessToken(any());
        this.failRestDocsMockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                                                  .addInterceptors(new AuthenticationInterceptor(authService))
                                                  .setControllerAdvice(new GlobalExceptionHandler())
                                                  .build();

        // when
        final ResultActions result = this.failRestDocsMockMvc
                .perform(patch("/reviews/{id}", 1));

        // then
        result.andExpect(status().isUnauthorized())
              .andDo(print());
    }

    // TODO ID에 해당하는 리소스가 없는 경우
}
