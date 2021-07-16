package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Language;

public class LanguageResponse {
    private final Long id;

    private final String name;

    public LanguageResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static LanguageResponse from(Language language) {
        return new LanguageResponse(language.getId(), language.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
