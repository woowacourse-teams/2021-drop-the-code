package com.wootech.dropthecode.repository;

import java.util.List;

import com.wootech.dropthecode.domain.TeacherProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long> {

    Page<TeacherProfile> findAllByLanguagesLanguageNameAndSkillsSkillNameInAndCareerGreaterThanEqual(Pageable pageable, String languageName, List<String> skills, Integer career);

    Page<TeacherProfile> findAllByLanguagesLanguageNameAndCareerGreaterThanEqual(Pageable pageable, String languageName, Integer career);
}
