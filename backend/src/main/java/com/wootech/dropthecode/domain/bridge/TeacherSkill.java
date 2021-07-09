package com.wootech.dropthecode.domain.bridge;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wootech.dropthecode.domain.BaseEntity;
import com.wootech.dropthecode.domain.Skill;
import com.wootech.dropthecode.domain.TeacherProfile;

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
