package com.wootech.dropthecode.repository;

import java.util.Objects;
import javax.persistence.EntityManager;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.wootech.dropthecode.domain.Feedback;
import com.wootech.dropthecode.dto.request.FeedbackSearchCondition;
import com.wootech.dropthecode.repository.support.Querydsl4RepositorySupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.wootech.dropthecode.domain.QFeedback.feedback;
import static com.wootech.dropthecode.domain.QReview.review;

public class FeedbackRepositoryCustomImpl extends Querydsl4RepositorySupport implements FeedbackRepositoryCustom {

    public FeedbackRepositoryCustomImpl(EntityManager entityManager) {
        super(Feedback.class);
    }

    @Override
    public Page<Feedback> findAll(FeedbackSearchCondition condition, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> feedbackContentQuery(feedbackCondition(condition)));
    }

    private JPAQuery<Feedback> feedbackContentQuery(BooleanExpression condition) {
        return getQueryFactory()
                .select(feedback)
                .from(feedback)
                .join(feedback.review, review)
                .where(condition);
    }

    private BooleanExpression feedbackCondition(FeedbackSearchCondition condition) {
        return feedback()
                .and(teacherIdEq(condition))
                .and(studentIdEq(condition));
    }

    private BooleanExpression feedback() {
        return feedback.id.goe(0);
    }

    private BooleanExpression teacherIdEq(FeedbackSearchCondition condition) {
        if (Objects.isNull(condition.getTeacherId())) {
            return null;
        }
        return review.teacher.id.eq(condition.getTeacherId());
    }

    private BooleanExpression studentIdEq(FeedbackSearchCondition condition) {
        if (Objects.isNull(condition.getStudentId())) {
            return null;
        }
        return review.student.id.eq(condition.getStudentId());
    }
}
