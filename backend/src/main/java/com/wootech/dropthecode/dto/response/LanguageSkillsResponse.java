package com.wootech.dropthecode.dto.response;

import java.util.List;

public class LanguageSkillsResponse {

    private final LanguageResponse language;

    private final List<SkillResponse> skills;

    public LanguageSkillsResponse(LanguageResponse language, List<SkillResponse> skills) {
        this.language = language;
        this.skills = skills;
    }

    public LanguageResponse getLanguage() {
        return language;
    }

    public List<SkillResponse> getSkills() {
        return skills;
    }
}
