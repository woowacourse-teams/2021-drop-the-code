package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Skill;

public class SkillResponse {
    private final Long id;

    private final String name;

    public SkillResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SkillResponse from(Skill skill) {
        return new SkillResponse(skill.getId(), skill.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
