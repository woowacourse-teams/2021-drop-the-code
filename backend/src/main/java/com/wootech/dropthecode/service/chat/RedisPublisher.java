package com.wootech.dropthecode.service.chat;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publishChattingMessage(ChannelTopic topic, RedisChat chat) {
        redisTemplate.convertAndSend(topic.getTopic(), chat);
    }
}
