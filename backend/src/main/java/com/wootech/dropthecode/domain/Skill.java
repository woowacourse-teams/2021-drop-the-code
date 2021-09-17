package com.wootech.dropthecode.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.wootech.dropthecode.domain.bridge.LanguageSkill;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
        region = "skill"
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Skill extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
    private List<TeacherSkill> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
    private List<LanguageSkill> languages = new ArrayList<>();

    @Builder
    public Skill(String name, List<TeacherSkill> teachers, List<LanguageSkill> languages) {
        this.name = name;
        this.teachers = teachers;
        this.languages = languages;
    }
}
