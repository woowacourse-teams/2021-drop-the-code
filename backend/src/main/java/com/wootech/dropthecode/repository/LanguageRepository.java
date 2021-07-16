package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.domain.Language;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
