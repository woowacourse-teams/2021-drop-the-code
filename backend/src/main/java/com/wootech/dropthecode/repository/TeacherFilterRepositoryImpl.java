package com.wootech.dropthecode.repository;

import java.util.List;
import java.util.Objects;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.wootech.dropthecode.domain.QLanguage;
import com.wootech.dropthecode.domain.QSkill;
import com.wootech.dropthecode.domain.QTeacherProfile;
import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.domain.bridge.QTeacherLanguage;
import com.wootech.dropthecode.domain.bridge.QTeacherSkill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherFilterRepositoryImpl extends QuerydslRepositorySupport implements TeacherFilterRepository {

    public TeacherFilterRepositoryImpl() {
        super(TeacherProfile.class);
    }

    @Override
    public Page<TeacherProfile> findAll(String languageName, List<String> skills, int career, Pageable pageable) {
        QTeacherProfile teacherProfile = QTeacherProfile.teacherProfile;
        QTeacherLanguage teacherLanguage = QTeacherLanguage.teacherLanguage;
        QLanguage language = QLanguage.language;
        QTeacherSkill teacherSkill = QTeacherSkill.teacherSkill;
        QSkill skill = QSkill.skill;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(language.name.eq(languageName));
        if (!skills.isEmpty()) {
            builder.and(skill.name.in(skills));
        }
        builder.and(teacherProfile.career.goe(career));

        final JPQLQuery<Long> query = from(teacherProfile).select(teacherLanguage.id).distinct()
                                                          .innerJoin(teacherProfile.languages, teacherLanguage)
                                                          .innerJoin(teacherLanguage.language, language)
                                                          .innerJoin(teacherProfile.skills, teacherSkill)
                                                          .innerJoin(teacherSkill.skill, skill)
                                                          .where(builder);

        final QueryResults<Long> teacherProfileIds = Objects.requireNonNull(getQuerydsl())
                                                            .applyPagination(pageable, query)
                                                            .fetchResults();

        final List<TeacherProfile> teacherProfiles = from(teacherProfile).distinct()
                                                                         .innerJoin(teacherProfile.languages, teacherLanguage).fetchJoin()
                                                                         .innerJoin(teacherLanguage.language, language).fetchJoin()
                                                                         .innerJoin(teacherProfile.skills, teacherSkill).fetchJoin()
                                                                         .innerJoin(teacherSkill.skill, skill).fetchJoin()
                                                                         .innerJoin(teacherProfile.member).fetchJoin()
                                                                         .where(teacherProfile.id.in(teacherProfileIds.getResults()))
                                                                         .fetch();

        return new PageImpl<>(teacherProfiles, pageable, teacherProfileIds.getTotal());
    }
}
