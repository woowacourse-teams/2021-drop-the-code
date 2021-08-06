package com.wootech.dropthecode.dto.response;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.domain.bridge.TeacherLanguage;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TeacherProfileResponse {
    /**
     * ID
     */
    private final Long id;

    /**
     * Email
     */
    private final String email;

    /**
     * 이름
     */
    private final String name;

    /**
     * Image Url
     */
    private final String imageUrl;

    /**
     * Github Url
     */
    private final String githubUrl;

    /**
     * 자기소개 제목
     */
    private final String title;

    /**
     * 자기소개 내용
     */
    private final String content;

    /**
     * 연차
     */
    private final Integer career;

    /**
     * 총 리뷰한 개수
     */
    private final Integer sumReviewCount;

    /**
     * 평균 리뷰 응답 시간
     */
    private final Double averageReviewTime;

    /**
     * 기술 및 언어 목록
     */
    private final TechSpecResponse techSpec;

    @Builder
    public TeacherProfileResponse(Long id, String email, String name, String imageUrl, String githubUrl, String title, String content, Integer career, Integer sumReviewCount, Double averageReviewTime, TechSpecResponse techSpec) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.githubUrl = githubUrl;
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
                teacherProfile.getMember().getGithubUrl(),
                teacherProfile.getTitle(),
                teacherProfile.getContent(),
                teacherProfile.getCareer(),
                teacherProfile.getSumReviewCount(),
                teacherProfile.getAverageReviewTime(),
                techSpecResponse);
    }
}
