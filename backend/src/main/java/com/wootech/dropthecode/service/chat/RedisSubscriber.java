package com.wootech.dropthecode.service.chat;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messageTemplate;

    public RedisSubscriber(ObjectMapper objectMapper, RedisTemplate redisTemplate, SimpMessageSendingOperations messageTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.messageTemplate = messageTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            RedisChat redisChat = objectMapper.readValue(publishMessage, RedisChat.class);
            messageTemplate.convertAndSend("/subscribe/rooms/" + redisChat.getRoomId(), redisChat.getMessage());
        } catch (Exception e) {
            throw new IllegalArgumentException("파싱 에러");
        }

    }
}
