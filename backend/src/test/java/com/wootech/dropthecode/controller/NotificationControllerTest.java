package com.wootech.dropthecode.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils;
import com.wootech.dropthecode.dto.response.NotificationResponse;
import com.wootech.dropthecode.dto.response.NotificationsResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest extends RestApiDocumentTest {

    @Autowired
    private NotificationController notificationController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, notificationController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, notificationController);
    }

    @Test
    @DisplayName("sse 연결")
    void sse() throws Exception {
        // given
        given(notificationService.subscribe(any(), anyString())).willReturn(new SseEmitter());

        // when
        ResultActions result = this.restDocsMockMvc.perform(
                get("/subscribe")
                        .with(userToken())
                        .accept(MediaType.TEXT_EVENT_STREAM_VALUE)
                        .header("Last-Event-ID", "1_1631593143664"));

        // then
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 한 사용자의 모든 알림 조회")
    void findAllNotification() throws Exception {
        // given
        List<NotificationResponse> notificationResponses = Arrays.asList(
                NotificationResponse.builder()
                                    .id(23L)
                                    .content("새로운 리뷰 요청이 도착했습니다!")
                                    .url("/reviews/3")
                                    .createdAt(LocalDateTime.now().plusDays(3))
                                    .isRead(false)
                                    .build(),
                NotificationResponse.builder()
                                    .id(15L)
                                    .content("리뷰 요청이 수락되었습니다.")
                                    .url("/reviews/2")
                                    .createdAt(LocalDateTime.now().plusDays(1))
                                    .isRead(false)
                                    .build(),
                NotificationResponse.builder()
                                    .id(10L)
                                    .content("리뷰가 완료되었습니다. 리뷰어에 대한 피드백을 입력해주세요.")
                                    .url("/reviews/1")
                                    .createdAt(LocalDateTime.now())
                                    .isRead(true)
                                    .build()
        );
        NotificationsResponse notificationsResponse = new NotificationsResponse(notificationResponses, 2L);
        given(notificationService.findAllById(any())).willReturn(notificationsResponse);

        // when
        ResultActions result = this.restDocsMockMvc.perform(
                get("/notifications")
                        .with(userToken()));

        // then
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("안 읽은 알림 클릭 시 읽음 상태로 변경")
    void readNotification() throws Exception {
        //given
        // when
        ResultActions result = this.restDocsMockMvc.perform(
                patch("/notifications/{id}", 1)
                        .with(userToken()));

        // then
        result.andExpect(status().isNoContent());
    }
}
