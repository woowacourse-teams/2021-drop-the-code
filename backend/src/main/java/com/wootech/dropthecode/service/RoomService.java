package com.wootech.dropthecode.service;

import com.wootech.dropthecode.domain.chatting.Room;
import com.wootech.dropthecode.dto.request.RoomRequest;
import com.wootech.dropthecode.repository.RoomRepository;

import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private final MemberService memberService;
    private final RoomRepository roomRepository;

    public RoomService(MemberService memberService, RoomRepository roomRepository) {
        this.memberService = memberService;
        this.roomRepository = roomRepository;
    }

    public Long getOrCreate(RoomRequest roomRequest) {
        Room room = roomRepository.findByTeacherIdAndStudentId(roomRequest.getTeacherId(), roomRequest.getStudentId())
                                  .orElse(
                                          new Room(
                                                  memberService.findById(roomRequest.getTeacherId()),
                                                  memberService.findById(roomRequest.getStudentId())
                                          )
                                  );
        Room savedRoom = roomRepository.save(room);
        return savedRoom.getId();
    }
}
