package com.wootech.dropthecode.repository;

import java.util.List;
import javax.persistence.EntityManager;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wootech.dropthecode.domain.QMember;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.domain.ReviewSummary;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import static com.wootech.dropthecode.domain.QMember.member;
import static com.wootech.dropthecode.domain.QReview.review;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<ReviewSummary> searchPageByStudentId(Long id, ReviewSearchCondition condition, Pageable pageable) {
        QMember memberSub = new QMember("memberSub");

        List<ReviewSummary> content =
                queryFactory.select(
                        Projections.constructor(ReviewSummary.class,
                                review.id, review.title, review.content, review.progress,
                                member.id, member.name, member.imageUrl,
                                memberSub.id, memberSub.name, memberSub.imageUrl,
                                review.prUrl, review.createdAt))
                            .from(review)
                            .join(review.teacher, member)
                            .join(review.student, memberSub)
                            .where(review.student.id.eq(id))
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetch();

        JPAQuery<Review> countQuery = queryFactory.select(review)
                                                  .from(review)
                                                  .join(review.teacher, member)
                                                  .join(review.student, memberSub);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}
