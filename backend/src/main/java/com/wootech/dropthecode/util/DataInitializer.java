package com.wootech.dropthecode.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.*;
import com.wootech.dropthecode.domain.bridge.LanguageSkill;
import com.wootech.dropthecode.domain.bridge.TeacherLanguage;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;
import com.wootech.dropthecode.repository.*;
import com.wootech.dropthecode.repository.bridge.LanguageSkillRepository;
import com.wootech.dropthecode.repository.bridge.TeacherLanguageRepository;
import com.wootech.dropthecode.repository.bridge.TeacherSkillRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile({"prod-init"})
@Component
public class DataInitializer implements ApplicationRunner {

    public static final int REPEAT_COUNT = 10;

    private final MemberRepository memberRepository;
    private final TeacherProfileRepository teacherProfileRepository;
    private final LanguageRepository languageRepository;
    private final SkillRepository skillRepository;
    private final ReviewRepository reviewRepository;

    private final TeacherLanguageRepository teacherLanguageRepository;
    private final TeacherSkillRepository teacherSkillRepository;
    private final LanguageSkillRepository languageSkillRepository;

    public DataInitializer(MemberRepository memberRepository, TeacherProfileRepository teacherProfileRepository, LanguageRepository languageRepository, SkillRepository skillRepository, ReviewRepository reviewRepository, TeacherLanguageRepository teacherLanguageRepository, TeacherSkillRepository teacherSkillRepository, LanguageSkillRepository languageSkillRepository) {
        this.memberRepository = memberRepository;
        this.teacherProfileRepository = teacherProfileRepository;
        this.languageRepository = languageRepository;
        this.skillRepository = skillRepository;
        this.reviewRepository = reviewRepository;
        this.teacherLanguageRepository = teacherLanguageRepository;
        this.teacherSkillRepository = teacherSkillRepository;
        this.languageSkillRepository = languageSkillRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Map<Long, Language> languageMap = insertLanguage()
                .stream()
                .collect(Collectors.toMap(Language::getId, Function.identity()));

        Map<Long, Skill> skillMap = insertSkill()
                .stream()
                .collect(Collectors.toMap(Skill::getId, Function.identity()));

        insertLanguageSkill(languageMap, skillMap);

        for (long i = 0; i < REPEAT_COUNT; i++) {
            Map<Long, Member> memberMap = insertMember()
                    .stream()
                    .collect(Collectors.toMap(Member::getId, Function.identity()));

            int memberCount = memberMap.keySet().size();

            Map<Long, TeacherProfile> teacherMap = insertTeacherProfile(i * memberCount, memberMap)
                    .stream()
                    .collect(Collectors.toMap(TeacherProfile::getId, Function.identity()));

            insertTeacherLanguage(languageMap, i * memberCount, teacherMap);
            insertTeacherSkill(skillMap, i * memberCount, teacherMap);

            insertReview(i * memberCount, memberMap);
        }
    }

    private List<Language> insertLanguage() {
        List<Language> languages = Arrays.asList(
                new Language("java"),
                new Language("javascript"),
                new Language("python"),
                new Language("c++"),
                new Language("c")
        );
        return languageRepository.saveAll(languages);
    }

    private List<Skill> insertSkill() {
        List<Skill> skills = Arrays.asList(
                new Skill("spring"),
                new Skill("vue"),
                new Skill("react"),
                new Skill("unity"),
                new Skill("django")
        );
        return skillRepository.saveAll(skills);
    }

    private void insertLanguageSkill(Map<Long, Language> languageMap, Map<Long, Skill> skillMap) {
        List<LanguageSkill> languageSkills = Arrays.asList(
                new LanguageSkill(languageMap.get(1L), skillMap.get(1L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(2L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(3L)),
                new LanguageSkill(languageMap.get(3L), skillMap.get(5L)),
                new LanguageSkill(languageMap.get(4L), skillMap.get(4L)),
                new LanguageSkill(languageMap.get(5L), skillMap.get(4L))
        );
        languageSkillRepository.saveAll(languageSkills);
    }

    private List<Member> insertMember() {
        List<Member> members = Arrays.asList(
                new Member("seed@gmail.com", "Seed", "https://avatars.githubusercontent.com/u/56301069?v=4", Role.TEACHER),
                new Member("allie@gmail.com", "Allie", "https://avatars.githubusercontent.com/u/32974201?v=4", Role.TEACHER),
                new Member("bran@gmail.com", "Bran", "https://avatars.githubusercontent.com/u/52202474?v=4", Role.TEACHER),
                new Member("air@gmail.com", "Air", "https://avatars.githubusercontent.com/u/45876793?v=4", Role.TEACHER),
                new Member("fafi@gmail.com", "Fafi", "https://avatars.githubusercontent.com/u/50273712?v=44", Role.TEACHER),
                new Member("shinse@gmail.com", "Shinsehantan", "https://avatars.githubusercontent.com/u/50273712?v=44", Role.TEACHER)
        );
        return memberRepository.saveAll(members);
    }

    private List<TeacherProfile> insertTeacherProfile(long i, Map<Long, Member> memberMap) {
        List<TeacherProfile> teacherProfiles = Arrays.asList(
                new TeacherProfile("배민 출신 백엔드 개발자", "배민 1타 강사. 열심히 가르쳐드리겠습니다.", 5, 2, 1.3, memberMap.get(1L + i)),
                new TeacherProfile("쿠팡 이츠 출신 백엔드 개발자", "빡세게 가르쳐드리겠습니다.", 4, 5, 1.2, memberMap.get(2L + i)),
                new TeacherProfile("우테코 갓 졸업생", "열심히 하겠습니다.", 10, 10, 5.3, memberMap.get(3L + i)),
                new TeacherProfile("배민 인프라 담당자", "화이팅", 2, 1, 10.1, memberMap.get(4L + i)),
                new TeacherProfile("Amazon 서버 개발자", "실리콘밸리 출신 인프라 개발자. 영어로 수업합니다.", 7, 3, 2.9, memberMap.get(5L + i)),
                new TeacherProfile("배민 프론트 개발자", "화이팅", 6, 1, 8.4, memberMap.get(6L + i))
        );
        return teacherProfileRepository.saveAll(teacherProfiles);
    }

    private void insertTeacherLanguage(Map<Long, Language> languageMap, long i, Map<Long, TeacherProfile> teacherMap) {
        List<TeacherLanguage> teacherLanguages = Arrays.asList(
                new TeacherLanguage(teacherMap.get(1L + i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(1L + i), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(1L + i), languageMap.get(3L)),

                new TeacherLanguage(teacherMap.get(2L + i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(2L + i), languageMap.get(5L)),

                new TeacherLanguage(teacherMap.get(3L + i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(3L + i), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(3L + i), languageMap.get(4L)),

                new TeacherLanguage(teacherMap.get(4L + i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(4L + i), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(4L + i), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(4L + i), languageMap.get(4L)),

                new TeacherLanguage(teacherMap.get(5L + i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(5L + i), languageMap.get(3L)),

                new TeacherLanguage(teacherMap.get(6L + i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(6L + i), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(6L + i), languageMap.get(5L))
        );
        teacherLanguageRepository.saveAll(teacherLanguages);
    }

    private void insertTeacherSkill(Map<Long, Skill> skillMap, long i, Map<Long, TeacherProfile> teacherMap) {
        List<TeacherSkill> teacherSkills = Arrays.asList(
                new TeacherSkill(teacherMap.get(1L + i), skillMap.get(2L)),
                new TeacherSkill(teacherMap.get(1L + i), skillMap.get(3L)),
                new TeacherSkill(teacherMap.get(1L + i), skillMap.get(5L)),

                new TeacherSkill(teacherMap.get(2L + i), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(2L + i), skillMap.get(2L)),

                new TeacherSkill(teacherMap.get(3L + i), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(3L + i), skillMap.get(4L)),

                new TeacherSkill(teacherMap.get(4L + i), skillMap.get(3L)),
                new TeacherSkill(teacherMap.get(4L + i), skillMap.get(4L)),
                new TeacherSkill(teacherMap.get(4L + i), skillMap.get(5L)),

                new TeacherSkill(teacherMap.get(5L + i), skillMap.get(4L)),
                new TeacherSkill(teacherMap.get(5L + i), skillMap.get(5L)),

                new TeacherSkill(teacherMap.get(6L + i), skillMap.get(4L)),
                new TeacherSkill(teacherMap.get(6L + i), skillMap.get(5L))
        );
        teacherSkillRepository.saveAll(teacherSkills);
    }

    private void insertReview(long i, Map<Long, Member> memberMap) {
        List<Review> reviews = Arrays.asList(
                new Review(memberMap.get(2L + i), memberMap.get(1L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(3L + i), memberMap.get(1L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(4L + i), memberMap.get(1L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),

                new Review(memberMap.get(1L + i), memberMap.get(2L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(3L + i), memberMap.get(2L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(5L + i), memberMap.get(2L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),

                new Review(memberMap.get(1L + i), memberMap.get(3L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(4L + i), memberMap.get(3L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(6L + i), memberMap.get(3L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),

                new Review(memberMap.get(1L + i), memberMap.get(4L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(2L + i), memberMap.get(4L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(3L + i), memberMap.get(4L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),

                new Review(memberMap.get(3L + i), memberMap.get(5L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(4L + i), memberMap.get(5L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(6L + i), memberMap.get(5L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),

                new Review(memberMap.get(1L + i), memberMap.get(6L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(3L + i), memberMap.get(6L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109"),
                new Review(memberMap.get(5L + i), memberMap.get(6L + i), "리뷰 요청합니다.", "Spark에서 Spring으로 마이그레이션", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/109")
        );
        reviewRepository.saveAll(reviews);
    }
}
