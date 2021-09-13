package com.wootech.dropthecode.service;

import java.io.IOException;

import com.wootech.dropthecode.dto.response.NotificationResponse;
import com.wootech.dropthecode.repository.EmitterRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = -1L;

    private final EmitterRepository emitterRepository;

    public NotificationService(EmitterRepository emitterRepository) {
        this.emitterRepository = emitterRepository;
    }

    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = emitterRepository.save(userId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(userId));
        emitter.onTimeout(() -> emitterRepository.deleteById(userId));
        return emitter;
    }

    public void send(Long userId, NotificationResponse response) {
        emitterRepository.findById(userId)
                         .ifPresent(emitter -> {
                                     try {
                                         emitter.send(SseEmitter.event().data(response));
                                     } catch (IOException exception) {
                                         emitterRepository.deleteById(userId);
                                     }
                                 }
                         );
    }
}
