package com.wootech.dropthecode.repository;

import java.util.List;
import javax.persistence.QueryHint;

import com.wootech.dropthecode.domain.Skill;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    @QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
    @EntityGraph(attributePaths = "languages.language", type = EntityGraph.EntityGraphType.LOAD)
    List<Skill> findAll();
}
