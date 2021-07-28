package com.wootech.dropthecode.repository;

import java.util.List;
import java.util.function.Function;
import javax.persistence.EntityManager;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.QMember;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.dto.ReviewSummary;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;
import com.wootech.dropthecode.repository.support.Querydsl4RepositorySupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.wootech.dropthecode.domain.QMember.member;
import static com.wootech.dropthecode.domain.QReview.review;
import static org.springframework.util.ObjectUtils.isEmpty;

public class ReviewRepositoryCustomImpl extends Querydsl4RepositorySupport implements ReviewRepositoryCustom {
    public static final QMember memberSub = new QMember("memberSub");

    public ReviewRepositoryCustomImpl(EntityManager entityManager) {
        super(Review.class);
    }

    @Override
    public Page<ReviewSummary> searchPageByStudentId(Long id, ReviewSearchCondition condition, Pageable pageable) {
        return applyPagination(pageable, getPageReviewContentQuery(studentReviewCondition(id, condition)));
    }

    @Override
    public Page<ReviewSummary> searchPageByTeacherId(Long id, ReviewSearchCondition condition, Pageable pageable) {
        return applyPagination(pageable, getPageReviewContentQuery(teacherReviewCondition(id, condition)));
    }

    private Function<JPAQueryFactory, JPAQuery<ReviewSummary>> getPageReviewContentQuery(BooleanExpression condition) {
        return contentQuery -> contentQuery
                .select(
                        Projections.constructor(ReviewSummary.class,
                                review.id, review.title, review.content, review.progress,
                                member.id, member.name, member.imageUrl,
                                memberSub.id, memberSub.name, memberSub.imageUrl,
                                review.prUrl, review.createdAt))
                .from(review)
                .join(review.teacher, member)
                .join(review.student, memberSub)
                .where(condition);
    }

    private BooleanExpression studentReviewCondition(Long id, ReviewSearchCondition condition) {
        return studentIdEq(id)
                .and(memberNameEq(condition.getName(), member))
                .and(progressEq(condition.getProgress()));
    }

    private BooleanExpression teacherReviewCondition(Long id, ReviewSearchCondition condition) {
        return teacherIdEq(id)
                .and(memberNameEq(condition.getName(), memberSub))
                .and(progressEq(condition.getProgress()));
    }

    private BooleanExpression studentIdEq(Long id) {
        return review.student.id.eq(id);
    }

    private BooleanExpression teacherIdEq(Long id) {
        return review.teacher.id.eq(id);
    }

    private BooleanExpression memberNameEq(String name, QMember qMember) {
        if (isEmpty(name)) {
            return null;
        }
        return qMember.name.eq(name);
    }

    private BooleanExpression progressEq(List<Progress> progress) {
        if (progress.isEmpty()) {
            return null;
        }
        return review.progress.in(progress);
    }
}
