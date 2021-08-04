package com.wootech.dropthecode.repository.bridge;

import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherSkillRepository extends JpaRepository<TeacherSkill, Long> {
    void deleteByTeacherProfile(TeacherProfile teacher);
}
