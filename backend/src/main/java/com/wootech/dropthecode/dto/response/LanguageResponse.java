package com.wootech.dropthecode.dto.response;

public class LanguageResponse {
    private final Long id;

    private final String name;

    public LanguageResponse(Long id, String name) {
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
