package com.wootech.dropthecode.repository.bridge;

import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.domain.bridge.TeacherLanguage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherLanguageRepository extends JpaRepository<TeacherLanguage, Long> {
    void deleteByTeacherProfile(TeacherProfile teacherProfile);
}
