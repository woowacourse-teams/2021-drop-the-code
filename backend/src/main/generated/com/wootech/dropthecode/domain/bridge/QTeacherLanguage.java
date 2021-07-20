package com.wootech.dropthecode.domain.bridge;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeacherLanguage is a Querydsl query type for TeacherLanguage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeacherLanguage extends EntityPathBase<TeacherLanguage> {

    private static final long serialVersionUID = 642408306L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeacherLanguage teacherLanguage = new QTeacherLanguage("teacherLanguage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.wootech.dropthecode.domain.QLanguage language;

    public final com.wootech.dropthecode.domain.QTeacherProfile teacherProfile;

    public QTeacherLanguage(String variable) {
        this(TeacherLanguage.class, forVariable(variable), INITS);
    }

    public QTeacherLanguage(Path<? extends TeacherLanguage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeacherLanguage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeacherLanguage(PathMetadata metadata, PathInits inits) {
        this(TeacherLanguage.class, metadata, inits);
    }

    public QTeacherLanguage(Class<? extends TeacherLanguage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new com.wootech.dropthecode.domain.QLanguage(forProperty("language")) : null;
        this.teacherProfile = inits.isInitialized("teacherProfile") ? new com.wootech.dropthecode.domain.QTeacherProfile(forProperty("teacherProfile"), inits.get("teacherProfile")) : null;
    }

}

