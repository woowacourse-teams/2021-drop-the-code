package com.wootech.dropthecode.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.wootech.dropthecode.domain.bridge.LanguageSkill;
import com.wootech.dropthecode.domain.bridge.TeacherLanguage;

@Entity
public class Language extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "skill")
    private final List<LanguageSkill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "language")
    private final List<TeacherLanguage> teachers = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Language() {
    }

    public List<LanguageSkill> getSkills() {
        return skills;
    }

    public List<TeacherLanguage> getTeachers() {
        return teachers;
    }
}
