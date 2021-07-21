package com.wootech.dropthecode.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;

import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.Skill;
import com.wootech.dropthecode.domain.bridge.LanguageSkill;

public class TechSpec {

    /**
     * 선생님 프로그래밍 언어
     */
    @NotBlank
    private String language;

    /**
     * 선생님 기술 스택
     */
    private List<String> skills = new ArrayList<>();

    public TechSpec() {
    }

    public TechSpec(String language, List<String> skills) {
        this.language = language;
        this.skills = skills;
    }

    public void validateSkillsInLanguage(Language language) {
        List<String> collect = language.getSkills()
                                       .stream()
                                       .map(LanguageSkill::getSkill)
                                       .map(Skill::getName)
                                       .collect(Collectors.toList());

        if (!collect.containsAll(skills)) {
            throw new IllegalArgumentException("언어에 포함되지 않는 기술이 있습니다.");
        }
    }

    public String getLanguage() {
        return language;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
