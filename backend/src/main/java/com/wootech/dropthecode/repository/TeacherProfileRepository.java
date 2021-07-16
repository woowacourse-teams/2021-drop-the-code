package com.wootech.dropthecode.repository;

import java.util.List;

import com.wootech.dropthecode.domain.TeacherProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long> {

    Page<TeacherProfile> findDistinctAllByLanguagesLanguageNameAndSkillsSkillNameInAndCareerGreaterThanEqual(Pageable pageable, String languageName, List<String> skills, Integer career);
}
