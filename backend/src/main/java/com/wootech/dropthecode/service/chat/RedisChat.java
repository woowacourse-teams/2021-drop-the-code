package com.wootech.dropthecode.service.chat;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RedisChat {

    @NotNull
    private Long roomId;

    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotBlank
    private String message;

    public RedisChat(@NotNull Long roomId, @NotNull Long senderId, @NotNull Long receiverId, @NotBlank String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }
}
