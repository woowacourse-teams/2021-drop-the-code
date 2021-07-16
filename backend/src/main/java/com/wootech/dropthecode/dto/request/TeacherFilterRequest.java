package com.wootech.dropthecode.dto.request;

import com.wootech.dropthecode.dto.TechSpec;

public class TeacherFilterRequest {

    private TechSpec techSpec = new TechSpec();

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
