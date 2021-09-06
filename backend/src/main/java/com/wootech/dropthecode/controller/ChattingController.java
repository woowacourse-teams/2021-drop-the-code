package com.wootech.dropthecode.controller;

import java.util.List;

import com.wootech.dropthecode.dto.response.ChatResponse;
import com.wootech.dropthecode.dto.response.LatestChatResponse;
import com.wootech.dropthecode.service.ChattingService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChattingController {
    private final ChattingService chattingService;

    public ChattingController(ChattingService chattingService) {
        this.chattingService = chattingService;
    }

    @GetMapping("/messages/{userId}")
    public List<LatestChatResponse> findAllLatestChats(@PathVariable Long userId) {
        return chattingService.findAllLatestChats(userId);
    }

    @GetMapping("/messages")
    public List<ChatResponse> findAllChats(@RequestParam("roomId") Long roomId) {
        return chattingService.findAllChats(roomId);
    }
}
