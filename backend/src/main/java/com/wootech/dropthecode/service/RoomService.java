package com.wootech.dropthecode.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;

import com.wootech.dropthecode.domain.chatting.Room;
import com.wootech.dropthecode.dto.request.RoomRequest;
import com.wootech.dropthecode.repository.RoomRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {
    private final MemberService memberService;
    private final RoomRepository roomRepository;

    public RoomService(MemberService memberService, RoomRepository roomRepository) {
        this.memberService = memberService;
        this.roomRepository = roomRepository;
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public Room findById(Long roomId) {
        return roomRepository.findById(roomId)
                             .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 방입니다."));
    }

    @Transactional(readOnly = true)
    public List<Room> findAllByMemberId(Long id) {
        return roomRepository.findByTeacherIdOrStudentId(id, id);
    }
}
