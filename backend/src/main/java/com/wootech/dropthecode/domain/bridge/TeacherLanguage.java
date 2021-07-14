package com.wootech.dropthecode.domain.bridge;

import javax.persistence.*;

import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.TeacherProfile;

@Entity
public class TeacherLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_teacherLanguage_to_teacherProfile"))
    private TeacherProfile teacherProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_teacherLanguage_to_language"))
    private Language language;

    protected TeacherLanguage() {
    }

    public Long getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }

    public TeacherProfile getTeacherProfile() {
        return teacherProfile;
    }
}
