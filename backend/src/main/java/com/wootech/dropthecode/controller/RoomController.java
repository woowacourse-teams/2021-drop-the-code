package com.wootech.dropthecode.controller;

import javax.validation.Valid;

import com.wootech.dropthecode.dto.request.ChatRequest;
import com.wootech.dropthecode.dto.request.RoomRequest;
import com.wootech.dropthecode.service.ChattingService;
import com.wootech.dropthecode.service.RoomService;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {
    private final RoomService roomService;
    private final ChattingService chattingService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public RoomController(RoomService roomService, ChattingService chattingService, SimpMessagingTemplate simpMessagingTemplate) {
        this.roomService = roomService;
        this.chattingService = chattingService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/rooms/{roomId}")
    public void publish(@DestinationVariable Long roomId, @RequestBody @Valid ChatRequest chatRequest) {
        chattingService.save(roomId, chatRequest);
        simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + roomId, chatRequest.getMessage());
    }

    @GetMapping("/rooms")
    public ResponseEntity<Long> createOrGet(@ModelAttribute @Valid RoomRequest roomRequest) {
        Long id = roomService.getOrCreate(roomRequest);
        return ResponseEntity.ok(id);
    }
}
