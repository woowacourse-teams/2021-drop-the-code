package com.wootech.dropthecode.repository;

import java.util.Optional;

import com.wootech.dropthecode.domain.chatting.Room;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByTeacherIdAndStudentId(Long studentId, Long teacherId);
}
