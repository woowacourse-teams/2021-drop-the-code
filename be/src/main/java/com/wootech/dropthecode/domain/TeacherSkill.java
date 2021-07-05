package com.wootech.dropthecode.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TeacherSkill extends BaseEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_teacherSkill_to_teacherProfile"))
    private TeacherProfile teacherProfile;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_teacherSkill_to_skill"))
    private Skill skill;

    protected TeacherSkill() {
    }
}
