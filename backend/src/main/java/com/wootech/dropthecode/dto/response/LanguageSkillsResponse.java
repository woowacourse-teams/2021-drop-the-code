package com.wootech.dropthecode.dto.response;

import java.util.List;

public class LanguageSkillsResponse {

    /**
     * 프로그래밍 언어
     */
    private final LanguageResponse language;

    /**
     * 프로그래밍 기술
     */
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
