package com.wootech.dropthecode.domain.bridge;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLanguageSkill is a Querydsl query type for LanguageSkill
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLanguageSkill extends EntityPathBase<LanguageSkill> {

    private static final long serialVersionUID = 838656497L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLanguageSkill languageSkill = new QLanguageSkill("languageSkill");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.wootech.dropthecode.domain.QLanguage language;

    public final com.wootech.dropthecode.domain.QSkill skill;

    public QLanguageSkill(String variable) {
        this(LanguageSkill.class, forVariable(variable), INITS);
    }

    public QLanguageSkill(Path<? extends LanguageSkill> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLanguageSkill(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLanguageSkill(PathMetadata metadata, PathInits inits) {
        this(LanguageSkill.class, metadata, inits);
    }

    public QLanguageSkill(Class<? extends LanguageSkill> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new com.wootech.dropthecode.domain.QLanguage(forProperty("language")) : null;
        this.skill = inits.isInitialized("skill") ? new com.wootech.dropthecode.domain.QSkill(forProperty("skill")) : null;
    }

}

