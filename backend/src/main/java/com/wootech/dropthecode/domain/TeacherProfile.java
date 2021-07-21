package com.wootech.dropthecode.domain;

import java.util.HashSet;
import java.util.Set;
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
    private final Set<TeacherLanguage> languages = new HashSet<>();

    @OneToMany(mappedBy = "teacherProfile", fetch = FetchType.LAZY)
    private final Set<TeacherSkill> skills = new HashSet<>();

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

    public Set<TeacherLanguage> getLanguages() {
        return languages;
    }

    public Set<TeacherSkill> getSkills() {
        return skills;
    }

    public Integer getSumReviewCount() {
        return sumReviewCount;
    }

    public Double getAverageReviewTime() {
        return averageReviewTime;
    }
}
