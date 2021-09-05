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
     * 송신자 닉네임
     */
    private String senderName;

    /**
     * 송신자 프로필 이미지
     */
    private String senderImageUrl;

    /**
     * 수신자 아이디
     */
    private Long receiverId;

    /**
     * 수신자 닉네임
     */
    private String receiverName;

    /**
     * 수신자 프로필 이미지
     */
    private String receiverImageUrl;

    /**
     * 메시지 내용
     */
    private String message;

    /**
     * 메시지 생성 시간
     */
    private LocalDateTime createdAt;

    @Builder
    public ChatResponse(Long senderId, String senderName, String senderImageUrl,
                        Long receiverId, String receiverName, String receiverImageUrl,
                        String message, LocalDateTime createdAt) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderImageUrl = senderImageUrl;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.receiverImageUrl = receiverImageUrl;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static ChatResponse from(Chat chat) {
        return ChatResponse.builder()
                           .senderId(chat.getSender().getId())
                           .senderName(chat.getSender().getName())
                           .senderImageUrl(chat.getSender().getImageUrl())
                           .receiverId(chat.getReceiver().getId())
                           .receiverName(chat.getReceiver().getName())
                           .receiverImageUrl(chat.getReceiver().getImageUrl())
                           .message(chat.getContent())
                           .createdAt(chat.getCreatedAt()).build();
    }
}
