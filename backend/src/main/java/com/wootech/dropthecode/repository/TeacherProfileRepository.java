package com.wootech.dropthecode.repository;

import java.util.Optional;

import com.wootech.dropthecode.domain.TeacherProfile;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long>, TeacherFilterRepository {

    @EntityGraph(attributePaths = {"languages.language", "skills.skill"}, type = EntityGraph.EntityGraphType.LOAD)
    @Override
    Optional<TeacherProfile> findById(Long id);
}
