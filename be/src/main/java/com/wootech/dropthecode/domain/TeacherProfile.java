package com.wootech.dropthecode.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<TeacherSkill> skills = new ArrayList<>();

    protected TeacherProfile() {
    }
}
