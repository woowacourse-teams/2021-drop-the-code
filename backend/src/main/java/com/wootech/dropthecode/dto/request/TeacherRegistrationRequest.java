package com.wootech.dropthecode.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.wootech.dropthecode.dto.TechSpec;

public class TeacherRegistrationRequest {

    /**
     * 선생님 테크 스펙
     */
    @NotEmpty
    private List<TechSpec> techSpecs;

    /**
     * 선생님 연차
     */
    @NotNull
    @PositiveOrZero
    private Integer career;

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

    public TeacherRegistrationRequest() {
    }

    public TeacherRegistrationRequest(List<TechSpec> techSpecs, Integer career, String title, String content) {
        this.techSpecs = techSpecs;
        this.career = career;
        this.title = title;
        this.content = content;
    }

    public List<TechSpec> getTechSpecs() {
        return techSpecs;
    }

    public Integer getCareer() {
        return career;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}


