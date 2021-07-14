package com.wootech.dropthecode.dto.response;

public class SkillResponse {
    private final Long id;

    private final String name;

    public SkillResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
