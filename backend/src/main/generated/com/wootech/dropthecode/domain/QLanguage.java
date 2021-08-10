package com.wootech.dropthecode.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLanguage is a Querydsl query type for Language
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLanguage extends EntityPathBase<Language> {

    private static final long serialVersionUID = 314946773L;

    public static final QLanguage language = new QLanguage("language");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final ListPath<com.wootech.dropthecode.domain.bridge.LanguageSkill, com.wootech.dropthecode.domain.bridge.QLanguageSkill> skills = this.<com.wootech.dropthecode.domain.bridge.LanguageSkill, com.wootech.dropthecode.domain.bridge.QLanguageSkill>createList("skills", com.wootech.dropthecode.domain.bridge.LanguageSkill.class, com.wootech.dropthecode.domain.bridge.QLanguageSkill.class, PathInits.DIRECT2);

    public final ListPath<com.wootech.dropthecode.domain.bridge.TeacherLanguage, com.wootech.dropthecode.domain.bridge.QTeacherLanguage> teachers = this.<com.wootech.dropthecode.domain.bridge.TeacherLanguage, com.wootech.dropthecode.domain.bridge.QTeacherLanguage>createList("teachers", com.wootech.dropthecode.domain.bridge.TeacherLanguage.class, com.wootech.dropthecode.domain.bridge.QTeacherLanguage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QLanguage(String variable) {
        super(Language.class, forVariable(variable));
    }

    public QLanguage(Path<? extends Language> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLanguage(PathMetadata metadata) {
        super(Language.class, metadata);
    }

}

