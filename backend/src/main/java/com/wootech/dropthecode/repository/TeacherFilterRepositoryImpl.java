package com.wootech.dropthecode.repository;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.wootech.dropthecode.domain.Language;
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
    public Page<TeacherProfile> findAll(List<Language> languages, List<Skill> skills, int career, Pageable pageable) {
        final Page<TeacherProfile> queryResults = findTeacherProfileIdsByPageable(languages, skills, career, pageable);
        final List<TeacherProfile> teacherProfiles = findAllByIds(queryResults, pageable);
        return new PageImpl<>(teacherProfiles, pageable, queryResults.getTotalElements());
    }

    private List<TeacherProfile> findAllByIds(Page<TeacherProfile> teacherProfiles, Pageable pageable) {
        final JPAQuery<TeacherProfile> query = getQueryFactory().select(teacherProfile)
                                                                .from(teacherProfile).distinct()
                                                                .innerJoin(teacherProfile.languages, teacherLanguage).fetchJoin()
                                                                .innerJoin(teacherLanguage.language, language).fetchJoin()
                                                                .innerJoin(teacherProfile.skills, teacherSkill).fetchJoin()
                                                                .innerJoin(teacherSkill.skill, skill).fetchJoin()
                                                                .innerJoin(teacherProfile.member).fetchJoin()
                                                                .where(teacherProfile.in(teacherProfiles.getContent()));

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder<Object> orderByExpression = new PathBuilder<>(Object.class, "teacherProfile");

            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, orderByExpression.get(o.getProperty())));
        }

        return query.fetch();
    }


    private Page<TeacherProfile> findTeacherProfileIdsByPageable(List<Language> languages, List<Skill> skills, int career, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(language.in(languages));
        if (!skills.isEmpty()) {
            builder.and(skill.in(skills));
        }
        builder.and(teacherProfile.career.goe(career));

        return applyPagination(pageable, contentQuery -> query(builder));
    }

    private JPAQuery<TeacherProfile> query(BooleanBuilder builder) {
        return getQueryFactory().select(teacherProfile).distinct()
                                .from(teacherProfile)
                                .innerJoin(teacherProfile.languages, teacherLanguage)
                                .innerJoin(teacherLanguage.language, language)
                                .innerJoin(teacherProfile.skills, teacherSkill)
                                .innerJoin(teacherSkill.skill, skill)
                                .where(builder);
    }
}
