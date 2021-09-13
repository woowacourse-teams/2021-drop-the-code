package com.wootech.dropthecode.dto.response;

import java.time.LocalDateTime;

import com.wootech.dropthecode.domain.Notification;
import com.wootech.dropthecode.util.LocalDateTimeToArray;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationResponse {
    private String content;
    private String url;
    private Integer[] createdAt;
    private boolean isRead;

    @Builder
    public NotificationResponse(String content, String url, LocalDateTime createdAt, boolean isRead) {
        this.content = content;
        this.url = url;
        this.createdAt = LocalDateTimeToArray.convert(createdAt);
        this.isRead = isRead;
    }

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                                   .content(notification.getContent())
                                   .url(notification.getUrl())
                                   .createdAt(notification.getCreatedAt())
                                   .isRead(notification.isRead())
                                   .build();
    }
}
