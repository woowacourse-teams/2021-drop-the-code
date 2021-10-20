package com.wootech.dropthecode.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationsResponse {
    /**
     * 로그인 한 유저의 모든 알림
     */
    private List<NotificationResponse> notificationResponses;

    /**
     * 로그인 한 유저가 읽지 않은 알림 수
     */
    private long unreadCount;

    @Builder
    public NotificationsResponse(List<NotificationResponse> notificationResponses, long unreadCount) {
        this.notificationResponses = notificationResponses;
        this.unreadCount = unreadCount;
    }

    public static NotificationsResponse of(List<NotificationResponse> notificationResponses, long count) {
        return NotificationsResponse.builder()
                                    .notificationResponses(notificationResponses)
                                    .unreadCount(count)
                                    .build();
    }
}
