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

    @Column(nullable = false)
    @Lob
    private String content;

    @Column(nullable = false)
    private int career;

    @OneToOne(mappedBy = "teacherProfile")
    private Member member;

    @OneToMany(mappedBy = "teacherProfile")
    private List<TeacherLanguage> languages = new ArrayList<>();

    @OneToMany(mappedBy = "teacherProfile")
    private final List<TeacherSkill> skills = new ArrayList<>();

    protected TeacherProfile() {
    }
}
