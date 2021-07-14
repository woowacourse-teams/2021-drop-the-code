package com.wootech.dropthecode.domain.bridge;

import javax.persistence.*;

import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.Skill;

@Entity
public class LanguageSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_languageSkill_to_skill"))
    private Skill skill;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_languageSkill_to_language"))
    private Language language;

    protected LanguageSkill() {
    }
}
