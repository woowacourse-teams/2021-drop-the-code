package com.wootech.dropthecode.dto;

import java.util.List;

public class TechSpec {
    private String language;
    private List<String> skills;

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
}
