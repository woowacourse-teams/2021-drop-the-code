package com.wootech.dropthecode.domain.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 1363096465L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.wootech.dropthecode.domain.QBaseEntity _super = new com.wootech.dropthecode.domain.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> elapsedTime = createNumber("elapsedTime", Long.class);

    public final com.wootech.dropthecode.domain.QFeedback feedback;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final SetPath<com.wootech.dropthecode.domain.Notification, com.wootech.dropthecode.domain.QNotification> notifications = this.<com.wootech.dropthecode.domain.Notification, com.wootech.dropthecode.domain.QNotification>createSet("notifications", com.wootech.dropthecode.domain.Notification.class, com.wootech.dropthecode.domain.QNotification.class, PathInits.DIRECT2);

    public final EnumPath<com.wootech.dropthecode.domain.Progress> progress = createEnum("progress", com.wootech.dropthecode.domain.Progress.class);

    public final StringPath prUrl = createString("prUrl");

    public final com.wootech.dropthecode.domain.QMember student;

    public final com.wootech.dropthecode.domain.QMember teacher;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.feedback = inits.isInitialized("feedback") ? new com.wootech.dropthecode.domain.QFeedback(forProperty("feedback"), inits.get("feedback")) : null;
        this.student = inits.isInitialized("student") ? new com.wootech.dropthecode.domain.QMember(forProperty("student"), inits.get("student")) : null;
        this.teacher = inits.isInitialized("teacher") ? new com.wootech.dropthecode.domain.QMember(forProperty("teacher"), inits.get("teacher")) : null;
    }

}

