package com.wootech.dropthecode.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomIdResponse {
    /**
     * 채팅방 아이디
     */
    private Long roomId;

    public RoomIdResponse(Long roomId) {
        this.roomId = roomId;
    }
}
