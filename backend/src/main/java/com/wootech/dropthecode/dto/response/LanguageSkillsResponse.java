package com.wootech.dropthecode.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LanguageSkillsResponse {

    /**
     * 프로그래밍 언어
     */
    private LanguageResponse language;

    /**
     * 프로그래밍 기술
     */
    private List<SkillResponse> skills;

    public LanguageSkillsResponse(LanguageResponse language, List<SkillResponse> skills) {
        this.language = language;
        this.skills = skills;
    }
}
