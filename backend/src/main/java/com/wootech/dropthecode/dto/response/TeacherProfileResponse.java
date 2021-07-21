package com.wootech.dropthecode.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.domain.bridge.TeacherLanguage;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;

public class TeacherProfileResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final String imageUrl;
    private final String title;
    private final String content;
    private final Integer career;
    private final Integer sumReviewCount;
    private final Double averageReviewTime;
    private final TechSpecResponse techSpec;

    public TeacherProfileResponse(Long id, String email, String name, String imageUrl, String title, String content, Integer career, Integer sumReviewCount, Double averageReviewTime, TechSpecResponse techSpec) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.title = title;
        this.content = content;
        this.career = career;
        this.sumReviewCount = sumReviewCount;
        this.averageReviewTime = averageReviewTime;
        this.techSpec = techSpec;
    }

    public static TeacherProfileResponse from(TeacherProfile teacherProfile) {
        final List<LanguageResponse> languageResponses = teacherProfile.getLanguages()
                                                                       .stream()
                                                                       .map(TeacherLanguage::getLanguage)
                                                                       .map(lang -> new LanguageResponse(lang.getId(), lang
                                                                               .getName()))
                                                                       .collect(Collectors.toList());

        final List<SkillResponse> skillsResponse = teacherProfile.getSkills()
                                                                 .stream()
                                                                 .map(TeacherSkill::getSkill)
                                                                 .map(skill -> new SkillResponse(skill.getId(), skill.getName()))
                                                                 .collect(Collectors.toList());

        final TechSpecResponse techSpecResponse = new TechSpecResponse(languageResponses, skillsResponse);
        return new TeacherProfileResponse(
                teacherProfile.getId(),
                teacherProfile.getMember().getEmail(),
                teacherProfile.getMember().getName(),
                teacherProfile.getMember().getImageUrl(),
                teacherProfile.getTitle(),
                teacherProfile.getContent(),
                teacherProfile.getCareer(),
                teacherProfile.getSumReviewCount(),
                teacherProfile.getAverageReviewTime(),
                techSpecResponse);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getCareer() {
        return career;
    }

    public Integer getSumReviewCount() {
        return sumReviewCount;
    }

    public Double getAverageReviewTime() {
        return averageReviewTime;
    }

    public TechSpecResponse getTechSpec() {
        return techSpec;
    }
}
