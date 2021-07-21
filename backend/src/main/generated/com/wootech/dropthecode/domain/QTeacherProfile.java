package com.wootech.dropthecode.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeacherProfile is a Querydsl query type for TeacherProfile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeacherProfile extends EntityPathBase<TeacherProfile> {

    private static final long serialVersionUID = -208280796L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeacherProfile teacherProfile = new QTeacherProfile("teacherProfile");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Double> averageReviewTime = createNumber("averageReviewTime", Double.class);

    public final NumberPath<Integer> career = createNumber("career", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final SetPath<com.wootech.dropthecode.domain.bridge.TeacherLanguage, com.wootech.dropthecode.domain.bridge.QTeacherLanguage> languages = this.<com.wootech.dropthecode.domain.bridge.TeacherLanguage, com.wootech.dropthecode.domain.bridge.QTeacherLanguage>createSet("languages", com.wootech.dropthecode.domain.bridge.TeacherLanguage.class, com.wootech.dropthecode.domain.bridge.QTeacherLanguage.class, PathInits.DIRECT2);

    public final QMember member;

    public final SetPath<com.wootech.dropthecode.domain.bridge.TeacherSkill, com.wootech.dropthecode.domain.bridge.QTeacherSkill> skills = this.<com.wootech.dropthecode.domain.bridge.TeacherSkill, com.wootech.dropthecode.domain.bridge.QTeacherSkill>createSet("skills", com.wootech.dropthecode.domain.bridge.TeacherSkill.class, com.wootech.dropthecode.domain.bridge.QTeacherSkill.class, PathInits.DIRECT2);

    public final NumberPath<Integer> sumReviewCount = createNumber("sumReviewCount", Integer.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTeacherProfile(String variable) {
        this(TeacherProfile.class, forVariable(variable), INITS);
    }

    public QTeacherProfile(Path<? extends TeacherProfile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeacherProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeacherProfile(PathMetadata metadata, PathInits inits) {
        this(TeacherProfile.class, metadata, inits);
    }

    public QTeacherProfile(Class<? extends TeacherProfile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

