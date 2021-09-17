package com.wootech.dropthecode.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.wootech.dropthecode.domain.bridge.LanguageSkill;
import com.wootech.dropthecode.domain.bridge.TeacherLanguage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
        region = "language"
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Language extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "language", fetch = FetchType.LAZY)
    private List<LanguageSkill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "language", fetch = FetchType.LAZY)
    private List<TeacherLanguage> teachers = new ArrayList<>();

    @Builder
    public Language(String name, List<LanguageSkill> skills, List<TeacherLanguage> teachers) {
        this.name = name;
        this.skills = skills;
        this.teachers = teachers;
    }
}
