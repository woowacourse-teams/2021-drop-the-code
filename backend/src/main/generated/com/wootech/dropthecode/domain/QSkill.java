package com.wootech.dropthecode.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSkill is a Querydsl query type for Skill
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSkill extends EntityPathBase<Skill> {

    private static final long serialVersionUID = 513958388L;

    public static final QSkill skill = new QSkill("skill");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<com.wootech.dropthecode.domain.bridge.LanguageSkill, com.wootech.dropthecode.domain.bridge.QLanguageSkill> languages = this.<com.wootech.dropthecode.domain.bridge.LanguageSkill, com.wootech.dropthecode.domain.bridge.QLanguageSkill>createList("languages", com.wootech.dropthecode.domain.bridge.LanguageSkill.class, com.wootech.dropthecode.domain.bridge.QLanguageSkill.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final ListPath<com.wootech.dropthecode.domain.bridge.TeacherSkill, com.wootech.dropthecode.domain.bridge.QTeacherSkill> teachers = this.<com.wootech.dropthecode.domain.bridge.TeacherSkill, com.wootech.dropthecode.domain.bridge.QTeacherSkill>createList("teachers", com.wootech.dropthecode.domain.bridge.TeacherSkill.class, com.wootech.dropthecode.domain.bridge.QTeacherSkill.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSkill(String variable) {
        super(Skill.class, forVariable(variable));
    }

    public QSkill(Path<? extends Skill> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSkill(PathMetadata metadata) {
        super(Skill.class, metadata);
    }

}

