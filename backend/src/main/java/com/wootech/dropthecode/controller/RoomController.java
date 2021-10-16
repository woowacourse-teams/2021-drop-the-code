package com.wootech.dropthecode.controller;

import javax.validation.Valid;

import com.wootech.dropthecode.dto.request.ChatRequest;
import com.wootech.dropthecode.dto.request.RoomRequest;
import com.wootech.dropthecode.dto.response.RoomIdResponse;
import com.wootech.dropthecode.service.chat.RedisChat;
import com.wootech.dropthecode.service.chat.RedisPublisher;
import com.wootech.dropthecode.service.ChattingService;
import com.wootech.dropthecode.service.RoomService;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {
    private final RoomService roomService;
    private final ChattingService chattingService;
    private final RedisPublisher redisPublisher;

    public RoomController(RoomService roomService, ChattingService chattingService, RedisPublisher redisPublisher) {
        this.roomService = roomService;
        this.chattingService = chattingService;
        this.redisPublisher = redisPublisher;
    }

    @MessageMapping("/rooms/{roomId}")
    public void publish(@DestinationVariable Long roomId, @RequestBody @Valid ChatRequest chatRequest) {
        chattingService.save(roomId, chatRequest);
        ChannelTopic channelTopic = new ChannelTopic("/rooms/" + roomId);
        redisPublisher.publishChattingMessage(channelTopic, new RedisChat(roomId, chatRequest.getSenderId(), chatRequest.getReceiverId(), chatRequest.getMessage()));
    }

    @GetMapping("/rooms")
    public ResponseEntity<RoomIdResponse> createOrGet(@ModelAttribute @Valid RoomRequest roomRequest) {
        RoomIdResponse roomIdResponse = roomService.getOrCreate(roomRequest);
        return ResponseEntity.ok(roomIdResponse);
    }
}
