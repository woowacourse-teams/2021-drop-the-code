package com.wootech.dropthecode.domain.bridge;

import javax.persistence.*;

import com.wootech.dropthecode.domain.Skill;
import com.wootech.dropthecode.domain.TeacherProfile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public TeacherSkill(TeacherProfile teacherProfile, Skill skill) {
        this.teacherProfile = teacherProfile;
        this.skill = skill;
    }
}
