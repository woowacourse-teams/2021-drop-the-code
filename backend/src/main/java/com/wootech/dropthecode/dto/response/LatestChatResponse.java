package com.wootech.dropthecode.dto.response;

import java.time.LocalDateTime;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.chatting.Chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LatestChatResponse {
    /**
     * 채팅 상대방 id
     */
    private Long id;

    /**
     * 채팅 상대방 이름
     */
    private String name;

    /**
     * 채팅 상대방 이미지 url
     */
    private String imageUrl;

    /**
     * 가장 최근 메시지 내용
     */
    private String latestMessage;

    /**
     * 가장 최근 메시지의 생성 시간
     */
    private LocalDateTime createdAt;

    @Builder
    public LatestChatResponse(Long id, String name, String imageUrl, String latestMessage, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.latestMessage = latestMessage;
        this.createdAt = createdAt;
    }

    public static LatestChatResponse of(Member partner, Chat latestChat) {
        return LatestChatResponse.builder()
                                 .id(partner.getId())
                                 .name(partner.getName())
                                 .imageUrl(partner.getImageUrl())
                                 .latestMessage(latestChat.getContent())
                                 .createdAt(latestChat.getCreatedAt())
                                 .build();
    }
}
