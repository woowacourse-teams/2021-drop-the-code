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
    @JoinColumn(unique = true, foreignKey = @ForeignKey(name = "fk_teacherProfile_to_member"))
    private Member member;

    @OneToMany(mappedBy = "teacherProfile", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<TeacherLanguage> languages = new HashSet<>();

    @OneToMany(mappedBy = "teacherProfile", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<TeacherSkill> skills = new HashSet<>();

    private Integer sumReviewCount = 0;

    private Double averageReviewTime = (double) 0;

    protected TeacherProfile() {
    }

    public TeacherProfile(String title, String content, int career, Member member) {
        this.title = title;
        this.content = content;
        this.career = career;
        this.member = member;
    }

    public TeacherProfile(String title, String content, int career, int sumReviewCount, Double averageReviewTime, Member member) {
        this.title = title;
        this.content = content;
        this.career = career;
        this.sumReviewCount = sumReviewCount;
        this.averageReviewTime = averageReviewTime;
        this.member = member;
    }

    public void updateReviewCountAndTime(Long newReviewTime) {
        double newAverageReviewTime = (newReviewTime + averageReviewTime * sumReviewCount * 24) / 24 / (sumReviewCount + 1);
        sumReviewCount++;
        averageReviewTime = Math.round(newAverageReviewTime * 10) / 10.0;
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
