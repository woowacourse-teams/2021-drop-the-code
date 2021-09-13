package com.wootech.dropthecode.repository;

import java.util.List;
import java.util.Optional;

import com.wootech.dropthecode.domain.chatting.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r " +
            "where (r.teacher.id = :teacherId and r.student.id = :studentId) " +
            "or (r.teacher.id = :studentId and r.student.id = :teacherId)")
    Optional<Room> findByTeacherIdAndStudentId(@Param("teacherId") Long teacherId, @Param("studentId") Long studentId);

    List<Room> findByTeacherIdOrStudentId(Long teacherId, Long studentId);
}
