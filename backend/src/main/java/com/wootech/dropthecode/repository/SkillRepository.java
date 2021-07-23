package com.wootech.dropthecode.repository;

import java.util.List;

import com.wootech.dropthecode.domain.Skill;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    @EntityGraph(attributePaths = "languages.language", type = EntityGraph.EntityGraphType.LOAD)
    List<Skill> findAll();
}
