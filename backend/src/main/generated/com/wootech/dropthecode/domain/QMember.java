package com.wootech.dropthecode.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1424365737L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath githubUrl = createString("githubUrl");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath name = createString("name");

    public final StringPath oauthId = createString("oauthId");

    public final ListPath<com.wootech.dropthecode.domain.review.Review, com.wootech.dropthecode.domain.review.QReview> reviewsAsStudent = this.<com.wootech.dropthecode.domain.review.Review, com.wootech.dropthecode.domain.review.QReview>createList("reviewsAsStudent", com.wootech.dropthecode.domain.review.Review.class, com.wootech.dropthecode.domain.review.QReview.class, PathInits.DIRECT2);

    public final ListPath<com.wootech.dropthecode.domain.review.Review, com.wootech.dropthecode.domain.review.QReview> reviewsAsTeacher = this.<com.wootech.dropthecode.domain.review.Review, com.wootech.dropthecode.domain.review.QReview>createList("reviewsAsTeacher", com.wootech.dropthecode.domain.review.Review.class, com.wootech.dropthecode.domain.review.QReview.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final ListPath<com.wootech.dropthecode.domain.chatting.Room, com.wootech.dropthecode.domain.chatting.QRoom> roomAsStudent = this.<com.wootech.dropthecode.domain.chatting.Room, com.wootech.dropthecode.domain.chatting.QRoom>createList("roomAsStudent", com.wootech.dropthecode.domain.chatting.Room.class, com.wootech.dropthecode.domain.chatting.QRoom.class, PathInits.DIRECT2);

    public final ListPath<com.wootech.dropthecode.domain.chatting.Room, com.wootech.dropthecode.domain.chatting.QRoom> roomAsTeacher = this.<com.wootech.dropthecode.domain.chatting.Room, com.wootech.dropthecode.domain.chatting.QRoom>createList("roomAsTeacher", com.wootech.dropthecode.domain.chatting.Room.class, com.wootech.dropthecode.domain.chatting.QRoom.class, PathInits.DIRECT2);

    public final QTeacherProfile teacherProfile;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.teacherProfile = inits.isInitialized("teacherProfile") ? new QTeacherProfile(forProperty("teacherProfile"), inits.get("teacherProfile")) : null;
    }

}

