package com.wootech.dropthecode.controller;

import java.util.ArrayList;
import java.util.List;

import com.wootech.dropthecode.dto.response.NotificationResponse;
import com.wootech.dropthecode.service.NotificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long id) {
        return notificationService.subscribe(id);
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<List<NotificationResponse>> notifications(@PathVariable Long id) {
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        return ResponseEntity.ok().body(notificationResponses);
    }
}
