package com.wootech.dropthecode.domain.bridge;

import javax.persistence.*;

import com.wootech.dropthecode.domain.BaseEntity;
import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.Skill;

@Entity
public class LanguageSkill extends BaseEntity {
    @ManyToOne
    private Skill skill;
    @ManyToOne
    private Language language;

}
