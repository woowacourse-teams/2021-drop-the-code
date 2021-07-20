package com.wootech.dropthecode.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.wootech.dropthecode.domain.bridge.LanguageSkill;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;

@Entity
public class Skill extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
    private final List<TeacherSkill> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
    private final List<LanguageSkill> languages = new ArrayList<>();

    protected Skill() {
    }

    public Skill(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<TeacherSkill> getTeachers() {
        return teachers;
    }

    public List<LanguageSkill> getLanguages() {
        return languages;
    }
}
