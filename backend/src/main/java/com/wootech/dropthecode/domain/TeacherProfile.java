package com.wootech.dropthecode.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.wootech.dropthecode.domain.bridge.TeacherLanguage;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;

@Entity
public class TeacherProfile extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int career;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_teacherProfile_to_member"))
    private Member member;

    @OneToMany(mappedBy = "teacherProfile", fetch = FetchType.LAZY)
    private final List<TeacherLanguage> languages = new ArrayList<>();

    @OneToMany(mappedBy = "teacherProfile", fetch = FetchType.LAZY)
    private final List<TeacherSkill> skills = new ArrayList<>();

    private final Integer sumReviewCount = 0;

    private final Double averageReviewTime = (double) 0;

    protected TeacherProfile() {
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getCareer() {
        return career;
    }

    public Member getMember() {
        return member;
    }

    public List<TeacherLanguage> getLanguages() {
        return languages;
    }

    public List<TeacherSkill> getSkills() {
        return skills;
    }

    public Integer getSumReviewCount() {
        return sumReviewCount;
    }

    public Double getAverageReviewTime() {
        return averageReviewTime;
    }
}
