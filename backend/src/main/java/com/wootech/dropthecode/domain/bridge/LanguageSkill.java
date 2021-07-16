package com.wootech.dropthecode.domain.bridge;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wootech.dropthecode.domain.BaseEntity;
import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.Skill;

@Entity
public class LanguageSkill extends BaseEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_languageSkill_to_skill"))
    private Skill skill;

    @ManyToOne
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
