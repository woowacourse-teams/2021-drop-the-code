package com.wootech.dropthecode.dto.response;

import java.time.LocalDateTime;

import com.wootech.dropthecode.domain.chatting.Chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatResponse {
    /**
     * 송신자 아이디
     */
    private Long senderId;

    /**
     * 수신자 아이디
     */
    private Long receiverId;

    /**
     * 메시지 내용
     */
    private String message;

    /**
     * 메시지 생성 시간
     */
    private LocalDateTime createdAt;

    @Builder
    public ChatResponse(Long senderId, Long receiverId, String message, LocalDateTime createdAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.createdAt = createdAt;
    }


    public static ChatResponse from(Chat chat) {
        return ChatResponse.builder()
                           .senderId(chat.getSender().getId())
                           .receiverId(chat.getReceiver().getId())
                           .message(chat.getContent())
                           .createdAt(chat.getCreatedAt()).build();
    }
}
