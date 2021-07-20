package com.wootech.dropthecode.domain.bridge;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeacherSkill is a Querydsl query type for TeacherSkill
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeacherSkill extends EntityPathBase<TeacherSkill> {

    private static final long serialVersionUID = 1058931831L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeacherSkill teacherSkill = new QTeacherSkill("teacherSkill");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.wootech.dropthecode.domain.QSkill skill;

    public final com.wootech.dropthecode.domain.QTeacherProfile teacherProfile;

    public QTeacherSkill(String variable) {
        this(TeacherSkill.class, forVariable(variable), INITS);
    }

    public QTeacherSkill(Path<? extends TeacherSkill> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeacherSkill(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeacherSkill(PathMetadata metadata, PathInits inits) {
        this(TeacherSkill.class, metadata, inits);
    }

    public QTeacherSkill(Class<? extends TeacherSkill> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.skill = inits.isInitialized("skill") ? new com.wootech.dropthecode.domain.QSkill(forProperty("skill")) : null;
        this.teacherProfile = inits.isInitialized("teacherProfile") ? new com.wootech.dropthecode.domain.QTeacherProfile(forProperty("teacherProfile"), inits.get("teacherProfile")) : null;
    }

}

