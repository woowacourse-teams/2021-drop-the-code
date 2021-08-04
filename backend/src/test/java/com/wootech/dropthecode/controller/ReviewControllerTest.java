package com.wootech.dropthecode.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wootech.dropthecode.controller.auth.AuthenticationInterceptor;
import com.wootech.dropthecode.controller.auth.GetAuthenticationInterceptor;
import com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.dto.request.ReviewRequest;
import com.wootech.dropthecode.dto.response.ProfileResponse;
import com.wootech.dropthecode.dto.response.ReviewResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.exception.GlobalExceptionHandler;
import com.wootech.dropthecode.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest extends RestApiDocumentTest {

    @Autowired
    private ReviewController reviewController;

    @MockBean
    private ReviewService reviewService;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, reviewController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, reviewController);
    }

    @Test
    @DisplayName("새로운 리뷰 등록")
    void newReview() throws Exception {
        // given
        ReviewRequest reviewRequest = new ReviewRequest(1L, 2L, "title1", "content1", "https://github.com/KJunseo");
        String body = objectMapper.writeValueAsString(reviewRequest);
        given(reviewService.create(any())).willReturn(1L);

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
                new ReviewRequest(null, null, " ", "content1", "https://github.com/KJunseo"));

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
        given(reviewService.findStudentReview(anyLong(), any(), any())).willReturn(new ReviewsResponse(data, 2));

        // when
        ResultActions result = restDocsMockMvc.perform(
                get("/reviews/student/2")
                        .with(userToken()));

        // then
        result.andExpect(status().isOk());
    }

    @DisplayName("내가 요청한 리뷰 목록 조회 - Authorization Header 가 없을 경우 실패")
    @Test
    void studentReviewsFailIfAuthorizationHeaderNotExists(RestDocumentationContextProvider provider) throws Exception {
        // given
        doThrow(new AuthorizationException("access token이 유효하지 않습니다."))
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

    //     TODO ID에 해당하는 리소스가 없는 경우

    @Test
    @DisplayName("리뷰 상세 페이지")
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
    @DisplayName("리뷰 상태 변경 (ON_GOING -> TEACHER_COMPLETE)")
    void updateReviewToComplete() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(patch("/reviews/1/complete")
                .with(userToken()));

        // then
        result.andExpect(status().isNoContent());
    }

    @DisplayName("리뷰 상태 변경 (ON_GOING -> TEACHER_COMPLETE) - Authorization Header 가 없을 경우 실패")
    @Test
    void updateReviewToCompleteFailIfAuthorizationHeaderNotExists(RestDocumentationContextProvider provider) throws Exception {
        // given
        doThrow(new AuthorizationException("access token이 유효하지 않습니다."))
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
    @DisplayName("리뷰 상태 변경 (TEACHER_COMPLETE -> FINISHED)")
    void updateReviewToFinish() throws Exception {
        // when
        ResultActions result = restDocsMockMvc.perform(patch("/reviews/1/finish")
                .with(userToken()));

        // then
        result.andExpect(status().isNoContent());
    }

    @DisplayName("리뷰 상태 변경 (TEACHER_COMPLETE -> FINISHED) - Authorization Header 가 없을 경우 실패")
    @Test
    void updateReviewToFinishFailIfAuthorizationHeaderNotExists(RestDocumentationContextProvider provider) throws Exception {
        // given
        doThrow(new AuthorizationException("access token이 유효하지 않습니다."))
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
    @DisplayName("리뷰 정보 수정")
    void updateReview() throws Exception {
        // given
        ReviewRequest reviewRequest = new ReviewRequest(1L, 2L, "new title", "new content", "new pr link");
        String body = objectMapper.writeValueAsString(reviewRequest);
        doNothing().when(reviewService).updateReview(anyLong(), any());

        // when
        ResultActions result = restDocsMockMvc.perform(patch("/reviews/1")
                .with(userToken())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
    }
}
