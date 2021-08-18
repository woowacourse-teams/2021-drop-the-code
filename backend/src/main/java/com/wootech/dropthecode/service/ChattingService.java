package com.wootech.dropthecode.service;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.chatting.Chat;
import com.wootech.dropthecode.domain.chatting.Room;
import com.wootech.dropthecode.dto.request.ChatRequest;
import com.wootech.dropthecode.repository.ChatRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChattingService {
    private final MemberService memberService;
    private final RoomService roomService;
    private final ChatRepository chatRepository;

    public ChattingService(MemberService memberService, RoomService roomService, ChatRepository chatRepository) {
        this.memberService = memberService;
        this.roomService = roomService;
        this.chatRepository = chatRepository;
    }

    @Transactional
    public void save(ChatRequest chatRequest) {
        Member sender = memberService.findById(chatRequest.getSenderId());
        Member receiver = memberService.findById(chatRequest.getReceiverId());
        Room room = roomService.findById(chatRequest.getRoomId());
        Chat chat = new Chat(room, sender, receiver, chatRequest.getMessage());
        chatRepository.save(chat);
    }
}
