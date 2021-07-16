package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.dto.response.LanguageResponse;
import com.wootech.dropthecode.dto.response.LanguageSkillsResponse;
import com.wootech.dropthecode.dto.response.SkillResponse;
import com.wootech.dropthecode.repository.LanguageRepository;

import org.springframework.stereotype.Service;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<LanguageSkillsResponse> findAll() {
        List<Language> languages = languageRepository.findAll();
        return languages.stream()
                        .map(language -> {
                            List<SkillResponse> skills = language.getSkills().stream()
                                                                 .map(languageSkill -> SkillResponse.from(languageSkill.getSkill()))
                                                                 .collect(Collectors.toList());
                            return new LanguageSkillsResponse(LanguageResponse.from(language), skills);
                        }).collect(Collectors.toList());
    }
}
