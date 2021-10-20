package com.wootech.dropthecode.controller;

import java.util.ArrayList;
import java.util.List;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.oauth.Login;
import com.wootech.dropthecode.dto.response.NotificationResponse;
import com.wootech.dropthecode.dto.response.NotificationsResponse;
import com.wootech.dropthecode.service.NotificationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * @title 로그인 한 유저 sse 연결
     */
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@Login LoginMember loginMember,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        System.out.println("=======sse request");
//        LoginMember loginMember = new LoginMember(userId);
        System.out.println(loginMember.getId());
        return notificationService.subscribe(loginMember, lastEventId);
    }

    /**
     * @title 로그인 한 유저의 모든 알림 조회
     */
    @GetMapping("/notifications")
    public ResponseEntity<NotificationsResponse> notifications(@Login LoginMember loginMember) {
        return ResponseEntity.ok().body(notificationService.findAllById(loginMember));
    }

    /**
     * @title 알림 읽음 상태 변경
     */
    @PatchMapping("/notifications/{id}")
    public ResponseEntity<Void> readNotification(@PathVariable Long id) {
        notificationService.readNotification(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
