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

        // 동적 쿼리를 위해 RepositoryImpl 에서 구현
        // fetch join 과 pagination 을 같이 쓸 때 발생하는 메모리 과부하를 방지하기 위해,
        // fetch join 쿼리와 pagination 쿼리를 따로 날린다.
        // 아래는 TeacherProfile 의 Id 만을 구하고, Pagination 을 처리하는 코드
        final JPQLQuery<Long> query = from(teacherProfile).select(teacherLanguage.id).distinct()
                                                          .innerJoin(teacherProfile.languages, teacherLanguage)
                                                          .innerJoin(teacherLanguage.language, language)
                                                          .innerJoin(teacherProfile.skills, teacherSkill)
                                                          .innerJoin(teacherSkill.skill, skill)
                                                          .where(builder);

        final QueryResults<Long> teacherProfileIds = Objects.requireNonNull(getQuerydsl())
                                                            .applyPagination(pageable, query)
                                                            .fetchResults();

        // InnerJoin 및 FetchJoin 을 사용하기 위해 RepositoryImpl 에서 구현
        // EntityGraph 는 기본적으로 LeftJoin
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
