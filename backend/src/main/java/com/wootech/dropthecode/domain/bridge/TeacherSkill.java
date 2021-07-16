package com.wootech.dropthecode.domain.bridge;

import javax.persistence.*;

import com.wootech.dropthecode.domain.Skill;
import com.wootech.dropthecode.domain.TeacherProfile;

@Entity
public class TeacherSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_teacherSkill_to_teacherProfile"))
    private TeacherProfile teacherProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_teacherSkill_to_skill"))
    private Skill skill;

    protected TeacherSkill() {
    }

    public Long getId() {
        return id;
    }

    public TeacherProfile getTeacherProfile() {
        return teacherProfile;
    }

    public Skill getSkill() {
        return skill;
    }
}
