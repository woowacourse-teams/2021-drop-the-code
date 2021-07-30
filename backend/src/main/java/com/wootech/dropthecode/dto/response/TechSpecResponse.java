package com.wootech.dropthecode.dto.response;

import java.util.List;

public class TechSpecResponse {

    /**
     * 언어 목록
     */
    private final List<LanguageResponse> languages;

    /**
     * 기술 목록
     */
    private final List<SkillResponse> skills;

    public TechSpecResponse(List<LanguageResponse> languages, List<SkillResponse> skills) {
        this.languages = languages;
        this.skills = skills;
    }

    public List<LanguageResponse> getLanguages() {
        return languages;
    }

    public List<SkillResponse> getSkills() {
        return skills;
    }
}
