package com.wootech.dropthecode.dto.request;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.dto.TechSpec;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeacherRegistrationRequest {

    /**
     * 선생님 자기 소개 제목
     */
    @NotBlank
    private String title;

    /**
     * 선생님 자기 소개 내용
     */
    @NotBlank
    private String content;

    /**
     * 선생님 연차
     */
    @NotNull
    @PositiveOrZero
    private Integer career;

    /**
     * 선생님 테크 스펙
     */
    @NotEmpty
    private List<TechSpec> techSpecs;

    @Builder
    public TeacherRegistrationRequest(@NotBlank String title, @NotBlank String content, @NotNull @PositiveOrZero Integer career, @NotEmpty List<TechSpec> techSpecs) {
        this.title = title;
        this.content = content;
        this.career = career;
        this.techSpecs = techSpecs;
    }

    public TeacherProfile toTeacherProfileWithMember(Member member) {
        return TeacherProfile.builder()
                             .title(title)
                             .content(content)
                             .career(career)
                             .member(member)
                             .build();
    }

    public void validateSkillsInLanguage(Map<String, Language> languageMap) {
        techSpecs.forEach(techSpec -> techSpec.validateSkillsInLanguage(languageMap.get(techSpec.getLanguage())));
    }
}


