package com.wootech.dropthecode.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class TechSpec {

    /**
     * 선생님 프로그래밍 언어
     */
    @NotBlank
    private String language;

    /**
     * 선생님 기술 스택
     */
    @NotEmpty
    private List<String> skills = new ArrayList<>();

    public TechSpec() {
    }

    public TechSpec(String language, List<String> skills) {
        this.language = language;
        this.skills = skills;
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

    @Override
    public String toString() {
        return "TechSpec{" +
                "language='" + language + '\'' +
                ", skills=" + skills +
                '}';
    }
}
