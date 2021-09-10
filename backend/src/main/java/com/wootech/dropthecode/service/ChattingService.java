package com.wootech.dropthecode.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.chatting.Chat;
import com.wootech.dropthecode.domain.chatting.Room;
import com.wootech.dropthecode.dto.request.ChatRequest;
import com.wootech.dropthecode.dto.response.ChatResponse;
import com.wootech.dropthecode.dto.response.LatestChatResponse;
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
    public void save(Long roomId, ChatRequest chatRequest) {
        Member sender = memberService.findById(chatRequest.getSenderId());
        Member receiver = memberService.findById(chatRequest.getReceiverId());
        Room room = roomService.findById(roomId);
        Chat chat = new Chat(room, sender, receiver, chatRequest.getMessage());
        chatRepository.save(chat);
    }

    @Transactional(readOnly = true)
    public List<LatestChatResponse> findAllLatestChats(Long id) {
        Member member = memberService.findById(id);
        List<Room> rooms = roomService.findAllByMemberId(member.getId());

        return rooms.stream()
                    .filter(Room::hasMessage)
                    .map(room -> LatestChatResponse.of(room.getPartner(id), room.getLatestChat()))
                    .sorted(Comparator.comparing(LatestChatResponse::getCreatedAt).reversed())
                    .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatResponse> findAllChats(Long roomId) {
        Room room = roomService.findById(roomId);
        List<Chat> chats = room.getChats();

        return chats.stream()
                    .map(chat -> ChatResponse.from(chat))
                    .collect(Collectors.toList());
    }
}
