package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Language;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LanguageResponse {

    /**
     * 프로그래밍 언어 Id
     */
    private Long id;

    /**
     * 프로그래밍 언어 이름
     */
    private String name;

    public LanguageResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static LanguageResponse from(Language language) {
        return new LanguageResponse(language.getId(), language.getName());
    }
}
