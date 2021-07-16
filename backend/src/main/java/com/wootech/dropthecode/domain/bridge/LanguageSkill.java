package com.wootech.dropthecode.domain.bridge;

import javax.persistence.*;

import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.Skill;

@Entity
public class LanguageSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_languageSkill_to_skill"))
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_languageSkill_to_language"))
    private Language language;

    protected LanguageSkill() {
    }

    public Skill getSkill() {
        return skill;
    }

    public Language getLanguage() {
        return language;
    }
}
