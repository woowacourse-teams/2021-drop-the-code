package com.wootech.dropthecode.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import com.wootech.dropthecode.dto.TechSpec;

public class TeacherFilterRequest {

    /**
     * 선생님 기술 스펙
     */
    @Valid
    private TechSpec techSpec = new TechSpec();

    /**
     * 선생님 연차
     */
    @PositiveOrZero
    private Integer career;

    public TeacherFilterRequest() {
    }

    public TeacherFilterRequest(TechSpec techSpec, Integer career) {
        this.techSpec = techSpec;
        this.career = career;
    }

    public TechSpec getTechSpec() {
        return techSpec;
    }

    public Integer getCareer() {
        return career;
    }

    public void setTechSpec(TechSpec techSpec) {
        this.techSpec = techSpec;
    }

    public void setCareer(Integer career) {
        this.career = career;
    }
}
