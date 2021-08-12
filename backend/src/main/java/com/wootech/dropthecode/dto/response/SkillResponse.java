package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Skill;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SkillResponse {
    /**
     * 프로그래밍 기술 Id
     */
    private Long id;

    /**
     * 프로그래밍 기술 이름
     */
    private String name;

    public SkillResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SkillResponse from(Skill skill) {
        return new SkillResponse(skill.getId(), skill.getName());
    }
}
