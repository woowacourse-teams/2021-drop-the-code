package com.wootech.dropthecode.dto.response;

import java.util.List;

public class TechSpecResponse {
    private final List<LanguageResponse> languages;

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
