package com.wootech.dropthecode.repository;

import java.util.List;
import javax.persistence.EntityManager;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.QMember;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.domain.ReviewSummary;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;
import com.wootech.dropthecode.repository.support.Querydsl4RepositorySupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.wootech.dropthecode.domain.QMember.member;
import static com.wootech.dropthecode.domain.QReview.review;
import static org.springframework.util.ObjectUtils.isEmpty;

public class ReviewRepositoryImpl extends Querydsl4RepositorySupport implements ReviewRepositoryCustom {

    public ReviewRepositoryImpl(EntityManager entityManager) {
        super(Review.class);
    }

    @Override
    public Page<ReviewSummary> searchPageByStudentId(Long id, ReviewSearchCondition condition, Pageable pageable) {
        QMember memberSub = new QMember("memberSub");

        return applyPagination(pageable, contentQuery -> contentQuery
                .select(
                        Projections.constructor(ReviewSummary.class,
                                review.id, review.title, review.content, review.progress,
                                member.id, member.name, member.imageUrl,
                                memberSub.id, memberSub.name, memberSub.imageUrl,
                                review.prUrl, review.createdAt))
                .from(review)
                .join(review.teacher, member)
                .join(review.student, memberSub)
                .where(
                        studentIdEq(id),
                        teacherNameEq(condition.getName()),
                        progressEq(condition.getProgress())
                )
        );
    }

    private BooleanExpression studentIdEq(Long id) {
        return review.student.id.eq(id);
    }

    private BooleanExpression teacherNameEq(String name) {
        if (isEmpty(name)) {
            return null;
        }
        return member.name.eq(name);
    }

    private BooleanExpression progressEq(List<Progress> progress) {
        if (progress.isEmpty()) {
            return null;
        }
        return review.progress.in(progress);
    }
}
