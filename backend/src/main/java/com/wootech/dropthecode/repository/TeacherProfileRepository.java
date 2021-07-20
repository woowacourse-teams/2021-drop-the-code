package com.wootech.dropthecode.repository;

import com.querydsl.core.types.Predicate;
import com.wootech.dropthecode.domain.TeacherProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long>, QuerydslPredicateExecutor<TeacherProfile> {

    @EntityGraph(attributePaths = {"languages.language", "skills.skill", "member"})
    Page<TeacherProfile> findAll(Predicate predicate, Pageable pageable);
}
