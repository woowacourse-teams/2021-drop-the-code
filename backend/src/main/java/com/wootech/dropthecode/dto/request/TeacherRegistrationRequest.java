package com.wootech.dropthecode.dto.request;

import java.util.List;
import javax.validation.constraints.*;

public class TeacherRegistrationRequest {

    /**
     * 선생님 기술 경력
     */
    @NotEmpty
    private List<String> skills;

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

    public TeacherRegistrationRequest(List<String> skills, int career, String title, String content) {
        this.skills = skills;
        this.career = career;
        this.title = title;
        this.content = content;
    }

    public List<String> getSkills() {
        return skills;
    }

    public int getCareer() {
        return career;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}


