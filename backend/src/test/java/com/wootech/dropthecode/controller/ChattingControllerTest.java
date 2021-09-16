package com.wootech.dropthecode.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityNotFoundException;

import com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils;
import com.wootech.dropthecode.dto.response.ChatResponse;
import com.wootech.dropthecode.dto.response.LatestChatResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.ResultActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.controller.util.RestDocsMockMvcUtils.OBJECT_MAPPER;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ChattingControllerTest extends RestApiDocumentTest {

    @Autowired
    ChattingController chattingController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.restDocsMockMvc = RestDocsMockMvcUtils.successRestDocsMockMvc(provider, chattingController);
        this.failRestDocsMockMvc = RestDocsMockMvcUtils.failRestDocsMockMvc(provider, chattingController);
    }

    @Test
    @DisplayName("유저의 최근 메시지들을 가져온다. - 성공")
    void findLatestChats() throws Exception {
        // given
        LatestChatResponse latestChatResponse = LatestChatResponse.builder()
                                                                  .id(1L)
                                                                  .name("fafi")
                                                                  .imageUrl("https://avatars.githubusercontent.com/u/50273712?v=44")
                                                                  .latestMessage("hihi")
                                                                  .createdAt(LocalDateTime.now())
                                                                  .build();

        List<LatestChatResponse> latestChatResponses = Collections.singletonList(latestChatResponse);

        given(chattingService.findAllLatestChats(isA(Long.class))).willReturn(latestChatResponses);

        // when
        ResultActions resultActions = this.restDocsMockMvc.perform(get("/messages/1")
                .contentType(MediaType.APPLICATION_JSON));


        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(latestChatResponses)))
                     .andDo(print());
    }

    @Test
    @DisplayName("유저의 최근 메시지들을 가져온다. - 존재하지 않는 유저로 인한 실패")
    void findLatestChatsFailure() throws Exception {
        // given
        given(chattingService.findAllLatestChats(isA(Long.class))).willThrow(new EntityNotFoundException("존재하지 않는 멤버입니다."));

        // when
        ResultActions resultActions = this.failRestDocsMockMvc.perform(get("/messages/1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isBadRequest())
                     .andDo(print());
    }

    @Test
    @DisplayName("채팅방 id로 하나의 메시지함을 전체 조회한다. - 성공")
    void findAllChats() throws Exception {
        ChatResponse chatResponse = ChatResponse.builder()
                                                .senderId(1L)
                                                .senderName("fafi")
                                                .senderImageUrl("https://avatars.githubusercontent.com/u/50273712?v=44")
                                                .receiverId(2L)
                                                .receiverName("seed")
                                                .receiverImageUrl("https://avatars.githubusercontent.com/u/50273712?v=44")
                                                .message("hi!")
                                                .createdAt(LocalDateTime.now())
                                                .build();

        List<ChatResponse> chatResponses = Collections.singletonList(chatResponse);

        given(chattingService.findAllChats(isA(Long.class))).willReturn(chatResponses);

        // when
        ResultActions resultActions = this.restDocsMockMvc.perform(get("/messages?roomId=1")
                .contentType(MediaType.APPLICATION_JSON));


        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(chatResponses)))
                     .andDo(print());
    }

    @Test
    @DisplayName("채팅방 id로 하나의 메시지함을 전체 조회한다. - 존재하지 않는 채팅방으로 인한 실패")
    void findAllChatsFailure() throws Exception {
        // given
        given(chattingService.findAllChats(isA(Long.class))).willThrow(new EntityNotFoundException("존재하지 않는 방입니다."));

        // when
        ResultActions resultActions = this.failRestDocsMockMvc.perform(get("/messages?roomId=1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isBadRequest())
                     .andDo(print());
    }
}
