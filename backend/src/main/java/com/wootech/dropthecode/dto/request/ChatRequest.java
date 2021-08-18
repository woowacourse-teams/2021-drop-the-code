package com.wootech.dropthecode.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRequest {
    /**
     * 송신자 id
     */
    @NotNull
    private Long senderId;

    /**
     * 채팅방 id
     */
    @NotNull
    private Long roomId;

    /**
     * 메시지 내용
     */
    @NotBlank
    private String message;

    public ChatRequest(Long senderId, Long roomId, String message) {
        this.senderId = senderId;
        this.roomId = roomId;
        this.message = message;
    }
}
