package com.wootech.dropthecode.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wootech.dropthecode.controller.auth.AuthenticationInterceptor;
import com.wootech.dropthecode.controller.auth.GetAuthenticationInterceptor;
import com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.dto.request.FeedbackRequest;
import com.wootech.dropthecode.dto.request.ReviewRequest;
import com.wootech.dropthecode.dto.response.ProfileResponse;
import com.wootech.dropthecode.dto.response.ReviewResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.exception.AuthenticationException;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.exception.GlobalExceptionHandler;
import com.wootech.dropthecode.exception.ReviewException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson;
import static com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils.OBJECT_MAPPER;
import static com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils.restDocumentation;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends RestApiDocumentTest {

    @Autowired
    private ReviewController reviewController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, reviewController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, reviewController);
    }

    @Test
    @DisplayName("????????? ?????? ??????")
    void newReview() throws Exception {
        // given
        ReviewRequest reviewRequest = new ReviewRequest(1L, 2L, "title1", "content1", "https://github.com/KJunseo");
        String body = objectMapper.writeValueAsString(reviewRequest);
        given(reviewService.create(any(), any())).willReturn(1L);

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

    @DisplayName("????????? ?????? ?????? - ????????? null ??? ?????? ??????")
    @Test
    void newReviewFailIfFieldIsNullTest() throws Exception {
        // given
        String body = objectMapper.writeValueAsString(
                new ReviewRequest(null, null, " ", "content1", "https://github.com/KJunseo"));

        // when
        final ResultActions result = this.failRestDocsMockMvc
                .perform(post("/reviews").contentType(MediaType.APPLICATION_JSON).content(body));

        // then
        result.andExpect(status().isBadRequest())
              .andDo(print());
    }

    //    @DisplayName("????????? ?????? ?????? - id(student or teacher)??? DB??? ???????????? ?????? ?????? ?????? ??????")
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
    @DisplayName("?????? ????????? ?????? ??????")
    void studentReviews() throws Exception {
        // given
        ProfileResponse firstTeacher = new ProfileResponse(1L, "user1", "image1");
        ProfileResponse firstStudent = new ProfileResponse(2L, "user2", "image2");

        ProfileResponse secondTeacher = new ProfileResponse(3L, "user3", "image3");
        ProfileResponse secondStudent = new ProfileResponse(2L, "user2", "image2");

        ReviewResponse firstReviewResponse = new ReviewResponse(1L, "title1", "content1", Progress.ON_GOING,
                firstTeacher, firstStudent, "prUrl1", LocalDateTime.now());
        ReviewResponse secondReviewResponse = new ReviewResponse(2L, "title2", "content2", Progress.ON_GOING,
                secondTeacher, secondStudent, "prUrl2", LocalDateTime.now());

        List<ReviewResponse> data = new ArrayList<>();
        data.add(firstReviewResponse);
        data.add(secondReviewResponse);

        doNothing().when(authService).validatesAccessToken(ACCESS_TOKEN);
        given(reviewService.findStudentReview(any(), anyLong(), any(), any())).willReturn(new ReviewsResponse(data, 2));

        // when
        ResultActions result = restDocsMockMvc.perform(
                get("/reviews/student/2")
                        .with(userToken()));

        // then
        result.andExpect(status().isOk());
    }

    @DisplayName("?????? ????????? ?????? ?????? ?????? - Authorization Header ??? ?????? ?????? ??????")
    @Test
    void studentReviewsFailIfAuthorizationHeaderNotExists(RestDocumentationContextProvider provider) throws Exception {
        // given
        doThrow(new AuthenticationException("access token??? ???????????? ????????????."))
                .when(authService).validatesAccessToken(any());
        this.failRestDocsMockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                                                  .addFilters(new CharacterEncodingFilter("UTF-8", true))
                                                  .addInterceptors(new GetAuthenticationInterceptor(authService))
                                                  .setControllerAdvice(new GlobalExceptionHandler())
                                                  .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                                  .alwaysDo(prepareJackson(OBJECT_MAPPER))
                                                  .alwaysDo(restDocumentation())
                                                  .apply(documentationConfiguration(provider)
                                                          .uris()
                                                          .withScheme("http")
                                                          .withHost("localhost")
                                                          .withPort(8080)
                                                          .and()
                                                          .snippets()
                                                          .withDefaults(
                                                                  HttpDocumentation.httpResponse()
                                                          ))
                                                  .build();
        // when
        final ResultActions result = this.failRestDocsMockMvc
                .perform(get("/reviews/student/{id}", 1));

        // then
        result.andExpect(status().isUnauthorized())
              .andDo(print());
    }

    //    @DisplayName("?????? ????????? ?????? ?????? ?????? - ID??? ???????????? ???????????? ?????? ?????? ??????")
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
    @DisplayName("?????? ????????? ??????")
    void teacherReviews() throws Exception {
        // given
        ProfileResponse firstTeacher = new ProfileResponse(1L, "user1", "image1");
        ProfileResponse firstStudent = new ProfileResponse(2L, "user2", "image2");

        ProfileResponse secondTeacher = new ProfileResponse(1L, "user1", "image1");
        ProfileResponse secondStudent = new ProfileResponse(3L, "user3", "image3");

        ReviewResponse firstReviewResponse = new ReviewResponse(1L, "title1", "content1", Progress.ON_GOING,
                firstTeacher, firstStudent, "prUrl1", LocalDateTime.now());
        ReviewResponse secondReviewResponse = new ReviewResponse(2L, "title2", "content2", Progress.ON_GOING,
                secondTeacher, secondStudent, "prUrl2", LocalDateTime.now());

        List<ReviewResponse> data = new ArrayList<>();
        data.add(firstReviewResponse);
        data.add(secondReviewResponse);

        given(reviewService.findTeacherReview(anyLong(), any(), any())).willReturn(new ReviewsResponse(data, 2));

        // when
        ResultActions result = restDocsMockMvc.perform(
                get("/reviews/teacher/1"));

        // then
        result.andExpect(status().isOk());
    }

    //     TODO ID??? ???????????? ???????????? ?????? ??????

    @Test
    @DisplayName("?????? ?????? ?????????")
    void reviewDetail() throws Exception {
        // given
        ProfileResponse teacher = new ProfileResponse(1L, "user1", "image1");
        ProfileResponse student = new ProfileResponse(2L, "user2", "image2");
        ReviewResponse review = new ReviewResponse(1L, "title1", "content1", Progress.ON_GOING,
                teacher, student, "prUrl1", LocalDateTime.now());

        given(reviewService.findReviewSummaryById(1L)).willReturn(review);

        // when
        ResultActions result = restDocsMockMvc.perform(
                get("/reviews/{id}", 1));

        // then
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("?????? ?????? ?????? (PENDING -> DENIED)")
    void denyReview() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(patch("/reviews/1/deny")
                .with(userToken()));

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("?????? ?????? ?????? (PENDING -> ON_GOING)")
    void acceptReview() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(patch("/reviews/1/accept")
                .with(userToken()));

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("?????? ?????? ?????? (ON_GOING -> TEACHER_COMPLETE)")
    void updateReviewToComplete() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(patch("/reviews/1/complete")
                .with(userToken()));

        // then
        result.andExpect(status().isNoContent());
    }

    @DisplayName("?????? ?????? ?????? (ON_GOING -> TEACHER_COMPLETE) - Authorization Header ??? ?????? ?????? ??????")
    @Test
    void updateReviewToCompleteFailIfAuthorizationHeaderNotExists(RestDocumentationContextProvider provider) throws Exception {
        // given
        doThrow(new AuthenticationException("access token??? ???????????? ????????????."))
                .when(authService).validatesAccessToken(any());
        this.failRestDocsMockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                                                  .addFilters(new CharacterEncodingFilter("UTF-8", true))
                                                  .addInterceptors(new AuthenticationInterceptor(authService))
                                                  .setControllerAdvice(new GlobalExceptionHandler())
                                                  .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                                  .alwaysDo(prepareJackson(OBJECT_MAPPER))
                                                  .alwaysDo(restDocumentation())
                                                  .apply(documentationConfiguration(provider)
                                                          .uris()
                                                          .withScheme("http")
                                                          .withHost("localhost")
                                                          .withPort(8080)
                                                          .and()
                                                          .snippets()
                                                          .withDefaults(
                                                                  HttpDocumentation.httpResponse()
                                                          ))
                                                  .build();

        // when
        final ResultActions result = this.failRestDocsMockMvc
                .perform(patch("/reviews/1/complete"));

        // then
        result.andExpect(status().isUnauthorized())
              .andDo(print());
    }

    @Test
    @DisplayName("?????? ?????? ?????? (TEACHER_COMPLETE -> FINISHED)")
    void updateReviewToFinish() throws Exception {
        FeedbackRequest feedbackRequest = new FeedbackRequest(5, "good");
        // when
        ResultActions result = restDocsMockMvc.perform(
                patch("/reviews/1/finish")
                        .with(userToken())
                        .content(OBJECT_MAPPER.writeValueAsString(feedbackRequest))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
    }

    @DisplayName("?????? ?????? ?????? (TEACHER_COMPLETE -> FINISHED) - Authorization Header ??? ?????? ?????? ??????")
    @Test
    void updateReviewToFinishFailIfAuthorizationHeaderNotExists(RestDocumentationContextProvider provider) throws Exception {
        // given
        doThrow(new AuthenticationException("access token??? ???????????? ????????????."))
                .when(authService).validatesAccessToken(any());
        this.failRestDocsMockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                                                  .addFilters(new CharacterEncodingFilter("UTF-8", true))
                                                  .addInterceptors(new AuthenticationInterceptor(authService))
                                                  .setControllerAdvice(new GlobalExceptionHandler())
                                                  .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                                  .alwaysDo(prepareJackson(OBJECT_MAPPER))
                                                  .alwaysDo(restDocumentation())
                                                  .apply(documentationConfiguration(provider)
                                                          .uris()
                                                          .withScheme("http")
                                                          .withHost("localhost")
                                                          .withPort(8080)
                                                          .and()
                                                          .snippets()
                                                          .withDefaults(
                                                                  HttpDocumentation.httpResponse()
                                                          ))
                                                  .build();

        // when
        final ResultActions result = this.failRestDocsMockMvc
                .perform(patch("/reviews/1/finish"));

        // then
        result.andExpect(status().isUnauthorized())
              .andDo(print());
    }


    @Test
    @DisplayName("?????? ?????? ??????")
    void updateReview() throws Exception {
        // given
        ReviewRequest reviewRequest = new ReviewRequest(1L, 2L, "new title", "new content", "new pr link");
        String body = objectMapper.writeValueAsString(reviewRequest);
        doNothing().when(reviewService).updateReview(any(), anyLong(), any());

        // when
        ResultActions result = restDocsMockMvc.perform(patch("/reviews/1")
                .with(userToken())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("?????? ?????? ?????? - ????????? ?????? ??????")
    void updateReviewNoAuthorization() throws Exception {
        // given
        ReviewRequest reviewRequest = new ReviewRequest(1L, 2L, "new title", "new content", "new pr link");
        String body = objectMapper.writeValueAsString(reviewRequest);
        doThrow(new AuthorizationException("????????? ????????? ????????? ????????????!"))
                .when(reviewService).updateReview(any(), anyLong(), any());

        // when
        ResultActions result = failRestDocsMockMvc.perform(patch("/reviews/1")
                .with(userToken())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("?????? ?????? ?????? - Pending")
    void cancelReview() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(delete("/reviews/1")
                .with(userToken()));

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("?????? ?????? ?????? - Pending??? ?????? ??????")
    void cancelReviewNoPending() throws Exception {
        // given
        doThrow(new ReviewException("????????? ??? ?????? ???????????????!"))
                .when(reviewService).cancelRequest(any(), anyLong());

        // when
        ResultActions result = failRestDocsMockMvc.perform(delete("/reviews/1")
                .with(userToken()));

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("?????? ?????? ?????? - ????????? ?????? ??????")
    void cancelReviewNoOwner() throws Exception {
        // given
        doThrow(new AuthorizationException("????????? ????????? ????????? ????????????!"))
                .when(reviewService).cancelRequest(any(), anyLong());

        // when
        ResultActions result = failRestDocsMockMvc.perform(delete("/reviews/1")
                .with(userToken()));

        // then
        result.andExpect(status().isForbidden());
    }
}
