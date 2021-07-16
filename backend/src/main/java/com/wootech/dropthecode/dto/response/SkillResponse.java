package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Skill;

public class SkillResponse {

    /**
     * 프로그래밍 기술 Id
     */
    private final Long id;

    /**
     * 프로그래밍 기술 이름
     */
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
