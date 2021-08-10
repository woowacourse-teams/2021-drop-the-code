package com.wootech.dropthecode.domain.bridge;

import javax.persistence.*;

import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.TeacherProfile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TeacherLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_teacherLanguage_to_teacherProfile"))
    private TeacherProfile teacherProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_teacherLanguage_to_language"))
    private Language language;

    public TeacherLanguage(TeacherProfile teacherProfile, Language language) {
        this.teacherProfile = teacherProfile;
        this.language = language;
    }
}
