package com.wootech.dropthecode.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import com.wootech.dropthecode.dto.TechSpec;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    @Builder
    public TeacherFilterRequest(TechSpec techSpec, Integer career) {
        this.techSpec = techSpec;
        this.career = career;
    }
}
