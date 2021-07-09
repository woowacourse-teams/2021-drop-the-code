package com.wootech.dropthecode.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.wootech.dropthecode.domain.bridge.LanguageSkill;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;

@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "skill")
    private final List<TeacherSkill> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "language")
    private List<LanguageSkill> language = new ArrayList<>();

    protected Skill() {
    }
}
