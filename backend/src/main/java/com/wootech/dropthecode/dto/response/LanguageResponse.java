package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Language;

public class LanguageResponse {

    /**
     * 프로그래밍 언어 Id
     */
    private final Long id;

    /**
     * 프로그래밍 언어 이름
     */
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
