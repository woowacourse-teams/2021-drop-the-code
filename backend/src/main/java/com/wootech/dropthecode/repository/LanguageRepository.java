package com.wootech.dropthecode.repository;

import java.util.List;

import com.wootech.dropthecode.domain.Language;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    @EntityGraph(attributePaths = "skills.skill", type = EntityGraph.EntityGraphType.LOAD)
    List<Language> findAll();
}
