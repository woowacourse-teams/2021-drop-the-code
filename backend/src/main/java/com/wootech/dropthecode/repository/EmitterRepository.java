package com.wootech.dropthecode.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepository {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(Long userId, SseEmitter sseEmitter) {
        emitters.put(userId, sseEmitter);
        return sseEmitter;
    }

    public Optional<SseEmitter> findById(Long id) {
        return Optional.ofNullable(emitters.get(id));
    }

    public void deleteById(Long userId) {
        emitters.remove(userId);
    }
}
