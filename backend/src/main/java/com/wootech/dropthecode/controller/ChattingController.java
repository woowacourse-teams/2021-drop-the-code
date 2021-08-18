package com.wootech.dropthecode.controller;

import java.util.List;

import com.wootech.dropthecode.dto.request.ChatRequest;
import com.wootech.dropthecode.dto.response.LatestChatsResponse;
import com.wootech.dropthecode.service.ChattingService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChattingController {
    private final ChattingService chattingService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChattingController(ChattingService chattingService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chattingService = chattingService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    // 클라이언트에서 /publish/messages로 보내면 여기서 처리됨. 채팅 방을 구독 중인 사람들한테 다 뿌려줌
    @MessageMapping("/messages")
    public void chat(ChatRequest chatRequest) {
        chattingService.save(chatRequest);
        simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + chatRequest.getRoomId(), chatRequest.getMessage());
    }

    @GetMapping("/messages/{id}")
    public List<LatestChatsResponse> findAllLatestChats(@PathVariable Long id) {
        return chattingService.findAllLatestChats(id);
    }
}
