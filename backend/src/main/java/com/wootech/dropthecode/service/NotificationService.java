package com.wootech.dropthecode.service;

import java.io.IOException;

import com.wootech.dropthecode.domain.Notification;
import com.wootech.dropthecode.dto.response.NotificationResponse;
import com.wootech.dropthecode.repository.EmitterRepository;
import com.wootech.dropthecode.repository.NotificationRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000;

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    public NotificationService(EmitterRepository emitterRepository, NotificationRepository notificationRepository) {
        this.emitterRepository = emitterRepository;
        this.notificationRepository = notificationRepository;
    }

    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = emitterRepository.save(userId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(userId));
        emitter.onTimeout(() -> emitterRepository.deleteById(userId));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        try {
            emitter.send(SseEmitter.event()
                                   .id("${userId}_${System.currentTimeMillis()}")
                                   .name("sse")
                                   .data("EventStream Created. [userId=$userId]"));
        } catch (IOException exception) {
            throw new RuntimeException();
        }

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방

        return emitter;
    }

    public void send(Long userId, Notification notification) {
        emitterRepository.findById(userId)
                         .ifPresent(emitter -> {
                                     try {
                                         notificationRepository.save(notification);
                                         emitter.send(SseEmitter.event()
                                                                .id("")
                                                                .name("sse")
                                                                .data(NotificationResponse.from(notification)));
                                     } catch (IOException exception) {
                                         emitterRepository.deleteById(userId);
                                     }
                                 }
                         );
    }
}
