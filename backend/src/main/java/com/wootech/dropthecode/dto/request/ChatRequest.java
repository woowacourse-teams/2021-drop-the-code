package com.wootech.dropthecode.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRequest {
    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotNull
    private Long roomId;

    @NotBlank
    private String message;

    public ChatRequest(Long senderId, Long receiverId, Long roomId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.roomId = roomId;
        this.message = message;
    }
}
