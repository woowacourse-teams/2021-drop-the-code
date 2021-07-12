package com.wootech.dropthecode.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class TechSpec {

    @NotBlank
    private String language;

    @NotEmpty
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
