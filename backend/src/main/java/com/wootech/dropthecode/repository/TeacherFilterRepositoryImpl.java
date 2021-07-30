package com.wootech.dropthecode.repository;

import java.util.List;
import java.util.Objects;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.Skill;
import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.repository.support.Querydsl4RepositorySupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        final QueryResults<Long> teacherProfileIds = findTeacherProfileIdsByPageable(languages, skills, career, pageable);
        final List<TeacherProfile> teacherProfiles = findAllByIds(teacherProfileIds);
        return new PageImpl<>(teacherProfiles, pageable, teacherProfileIds.getTotal());
    }

    private List<TeacherProfile> findAllByIds(QueryResults<Long> teacherProfileIds) {
        return getQueryFactory().select(teacherProfile)
                                .from(teacherProfile).distinct()
                                .innerJoin(teacherProfile.languages, teacherLanguage).fetchJoin()
                                .innerJoin(teacherLanguage.language, language).fetchJoin()
                                .innerJoin(teacherProfile.skills, teacherSkill).fetchJoin()
                                .innerJoin(teacherSkill.skill, skill).fetchJoin()
                                .innerJoin(teacherProfile.member).fetchJoin()
                                .where(teacherProfile.id.in(teacherProfileIds.getResults()))
                                .fetch();
    }

    private QueryResults<Long> findTeacherProfileIdsByPageable(List<Language> languages, List<Skill> skills, int career, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(language.in(languages));
        if (!skills.isEmpty()) {
            builder.and(skill.in(skills));
        }
        builder.and(teacherProfile.career.goe(career));

        final JPQLQuery<Long> query = getQueryFactory().select(teacherProfile.id).distinct()
                                                       .from(teacherProfile)
                                                       .innerJoin(teacherProfile.languages, teacherLanguage)
                                                       .innerJoin(teacherLanguage.language, language)
                                                       .innerJoin(teacherProfile.skills, teacherSkill)
                                                       .innerJoin(teacherSkill.skill, skill)
                                                       .where(builder);

        return Objects.requireNonNull(getQuerydsl())
                      .applyPagination(pageable, query)
                      .fetchResults();
    }
}
