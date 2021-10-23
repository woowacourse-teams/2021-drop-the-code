package com.wootech.dropthecode.repository;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.QLanguage;
import com.wootech.dropthecode.domain.Skill;
import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.repository.support.Querydsl4RepositorySupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import static com.wootech.dropthecode.domain.QLanguage.language;
import static com.wootech.dropthecode.domain.QSkill.skill;
import static com.wootech.dropthecode.domain.QTeacherProfile.teacherProfile;
import static com.wootech.dropthecode.domain.bridge.QTeacherLanguage.teacherLanguage;
import static com.wootech.dropthecode.domain.bridge.QTeacherSkill.teacherSkill;


@Repository
public class TeacherFilterRepositoryImpl extends Querydsl4RepositorySupport implements TeacherFilterRepository {

    public TeacherFilterRepositoryImpl() {
        super(TeacherProfile.class);
    }

    @Override
    public Page<TeacherProfile> findAll(Language language, List<Skill> skills, int career, Pageable pageable) {
        final Page<Long> teacherIds = findTeacherProfileIdsByPageable(language, skills, career, pageable);
        final List<TeacherProfile> teacherProfiles = findAllByIds(teacherIds, pageable);
        return new PageImpl<>(teacherProfiles, pageable, teacherIds.getTotalElements());
    }

    private List<TeacherProfile> findAllByIds(Page<Long> teacherIds, Pageable pageable) {
        final JPAQuery<TeacherProfile> query = getQueryFactory().select(teacherProfile)
                                                                .from(teacherProfile).distinct()
                                                                .innerJoin(teacherProfile.languages, teacherLanguage).fetchJoin()
                                                                .innerJoin(teacherLanguage.language, language).fetchJoin()
                                                                .leftJoin(teacherProfile.skills, teacherSkill).fetchJoin()
                                                                .leftJoin(teacherSkill.skill, skill).fetchJoin()
                                                                .innerJoin(teacherProfile.member).fetchJoin()
                                                                .where(teacherProfile.id.in(teacherIds.getContent()));

        for (Sort.Order order : pageable.getSort()) {
            PathBuilder<TeacherProfile> orderByExpression = new PathBuilder<>(TeacherProfile.class, "teacherProfile");
            query.orderBy(new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC, orderByExpression.get(order.getProperty())));
        }

        return query.fetch();
    }

    private Page<Long> findTeacherProfileIdsByPageable(Language language, List<Skill> skills, int career, Pageable pageable) {
        final JPAQuery<Long> query = getQueryFactory().select(teacherProfile.id)
                                                      .from(teacherProfile)
                                                      .innerJoin(teacherProfile.languages, teacherLanguage)
                                                      .innerJoin(teacherLanguage.language, QLanguage.language);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QLanguage.language.eq(language));
        if (!skills.isEmpty()) {
            query.leftJoin(teacherProfile.skills, teacherSkill)
                 .leftJoin(teacherSkill.skill, skill);
            builder.and(skill.in(skills));
        }
        builder.and(teacherProfile.career.goe(career));

        query.where(builder);

        return applyPagination(pageable, query);
    }
}
