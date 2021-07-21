package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.TeacherProfile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long> {

    Boolean existsTeacherProfileByMember(Member member);
}
