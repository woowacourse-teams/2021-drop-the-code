package com.wootech.dropthecode.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

import com.wootech.dropthecode.domain.*;
import com.wootech.dropthecode.domain.bridge.LanguageSkill;
import com.wootech.dropthecode.domain.bridge.TeacherLanguage;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;
import com.wootech.dropthecode.domain.review.Review;
import com.wootech.dropthecode.repository.*;
import com.wootech.dropthecode.repository.bridge.LanguageSkillRepository;
import com.wootech.dropthecode.repository.bridge.TeacherLanguageRepository;
import com.wootech.dropthecode.repository.bridge.TeacherSkillRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Profile("prod-init")
@Component
public class DataInitializerPT implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializerPT.class);

    private static final int START = 1;
    private static final int REPEATED_COUNT = 200000;
    private static final Random random = new Random();

    private final MemberRepository memberRepository;
    private final TeacherProfileRepository teacherProfileRepository;
    private final LanguageRepository languageRepository;
    private final SkillRepository skillRepository;
    private final ReviewRepository reviewRepository;

    private final TeacherLanguageRepository teacherLanguageRepository;
    private final TeacherSkillRepository teacherSkillRepository;
    private final LanguageSkillRepository languageSkillRepository;

    private final Environment environment;
    private final EntityManager entityManager;

    public DataInitializerPT
            (MemberRepository memberRepository, TeacherProfileRepository teacherProfileRepository, LanguageRepository languageRepository, SkillRepository skillRepository, ReviewRepository reviewRepository, TeacherLanguageRepository teacherLanguageRepository, TeacherSkillRepository teacherSkillRepository, LanguageSkillRepository languageSkillRepository, Environment environment, EntityManager entityManager) {
        this.memberRepository = memberRepository;
        this.teacherProfileRepository = teacherProfileRepository;
        this.languageRepository = languageRepository;
        this.skillRepository = skillRepository;
        this.reviewRepository = reviewRepository;
        this.teacherLanguageRepository = teacherLanguageRepository;
        this.teacherSkillRepository = teacherSkillRepository;
        this.languageSkillRepository = languageSkillRepository;
        this.environment = environment;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Map<Long, Language> languageMap = insertLanguage()
                .stream()
                .collect(Collectors.toMap(Language::getId, Function.identity()));
        entityManager.flush();
        log.info("******complete insert language");

        Map<Long, Skill> skillMap = insertSkill()
                .stream()
                .collect(Collectors.toMap(Skill::getId, Function.identity()));
        entityManager.flush();
        log.info("******complete insert skill");

        insertLanguageSkill(languageMap, skillMap);
        entityManager.flush();
        log.info("******complete insert language skill");

        Map<Long, Member> memberMap = insertMember()
                .stream()
                .collect(Collectors.toMap(Member::getId, Function.identity()));
        entityManager.flush();
        log.info("******complete insert member");

        Map<Long, TeacherProfile> teacherMap = insertTeacherProfile(memberMap)
                .stream()
                .collect(Collectors.toMap(TeacherProfile::getId, Function.identity()));
        entityManager.flush();
        log.info("******complete insert teacher profile");

        insertTeacherLanguage(languageMap, teacherMap);
        entityManager.flush();
        log.info("******complete insert teacher language");

        insertTeacherSkill(skillMap, teacherMap);
        entityManager.flush();
        log.info("******complete insert teacher skill");

        insertReview(teacherMap, memberMap);
        entityManager.flush();
        log.info("******complete insert review");
    }

    private List<Language> insertLanguage() {
        List<Language> languages = Arrays.asList(
                Language.builder().name("java").build(),
                Language.builder().name("javascript").build(),
                Language.builder().name("python").build(),
                Language.builder().name("kotlin").build(),
                Language.builder().name("c").build()
        );
        return languageRepository.saveAll(languages);
    }

    private List<Skill> insertSkill() {
        List<Skill> skills = Arrays.asList(
                Skill.builder().name("spring").build(),
                Skill.builder().name("vue").build(),
                Skill.builder().name("react").build(),
                Skill.builder().name("angular").build(),
                Skill.builder().name("django").build()
        );
        return skillRepository.saveAll(skills);
    }

    private void insertLanguageSkill(Map<Long, Language> languageMap, Map<Long, Skill> skillMap) {
        List<LanguageSkill> languageSkills = Arrays.asList(
                new LanguageSkill(languageMap.get(1L), skillMap.get(1L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(2L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(3L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(4L)),
                new LanguageSkill(languageMap.get(3L), skillMap.get(5L)),
                new LanguageSkill(languageMap.get(4L), skillMap.get(1L))
        );
        languageSkillRepository.saveAll(languageSkills);
    }

    private List<Member> insertMember() {
        List<Member> members = new ArrayList<>();
        Member iu = realMember("TEST1", "iu@email.com", "iu", "https://www.allkpop.com/upload/2018/10/af_org/27105129/IU.jpg", "https://github.com", Role.TEACHER);
        members.add(iu);

        for (int i = START + 1; i < REPEATED_COUNT + 1; i++) {
            String name = "name" + i;
            Role role = Role.TEACHER;
            if (i % 10 == 0) {
                role = Role.STUDENT;
            }
            Member member = realMember("TEST" + i, name + "@email.com", name, "https://source.unsplash.com/random", "https://github.com", role);
            members.add(member);
            log.info("******insert member {}", i);
            if (i % 10000 == 0) {
                entityManager.flush();
                log.info("******flush");
            }
        }

        return memberRepository.saveAll(members);
    }

    private Member realMember(String oauthId, String email, String name, String imageUrl, String githubUrl, Role role) {
        return Member.builder()
                     .oauthId(oauthId)
                     .email(email)
                     .name(name)
                     .imageUrl(imageUrl)
                     .githubUrl(githubUrl)
                     .role(role)
                     .build();
    }

    private List<TeacherProfile> insertTeacherProfile(Map<Long, Member> memberMap) {
        List<TeacherProfile> teacherProfiles = new ArrayList<>();
        int i = 0;
        for (Long id : memberMap.keySet()) {
            Member member = memberMap.get(id);
            if (member.hasRole(Role.TEACHER)) {
                TeacherProfile teacherProfile = dummyTeacherProfile("title" + id, "content" + id,
                        random.nextInt(20) + 1, random.nextInt(100) + 1,
                        Math.round((random.nextDouble() * 7 + 1) * 10) / 10.0, member);
                teacherProfiles.add(teacherProfile);
                log.info("******insert teacher profile {}", id);
            }
            i++;
            if (i % 10000 == 0) {
                entityManager.flush();
                log.info("******flush");
            }
        }
        return teacherProfileRepository.saveAll(teacherProfiles);
    }

    private TeacherProfile dummyTeacherProfile(String title, String content, Integer career, Integer sumReviewCount, Double averageReviewTime, Member member) {
        return TeacherProfile.builder()
                             .title(title)
                             .content(content)
                             .career(career)
                             .sumReviewCount(sumReviewCount)
                             .averageReviewTime(averageReviewTime)
                             .member(member)
                             .build();
    }

    private void insertTeacherLanguage(Map<Long, Language> languageMap, Map<Long, TeacherProfile> teacherMap) {
        List<TeacherLanguage> teacherLanguages = new ArrayList<>();

        int i = 0;
        for (Long id : teacherMap.keySet()) {
            for (Long languageId : languageMap.keySet()) {
                TeacherLanguage teacherLanguage = new TeacherLanguage(teacherMap.get(id), languageMap.get(languageId));
                teacherLanguages.add(teacherLanguage);
                log.info("******insert teacher language {} {}", id, languageId);
                i++;
                if (i % 10000 == 0) {
                    entityManager.flush();
                    log.info("******flush");
                }
                if (i == REPEATED_COUNT) {
                    teacherLanguageRepository.saveAll(teacherLanguages);
                    return;
                }
            }
        }
    }

    private void insertTeacherSkill(Map<Long, Skill> skillMap, Map<Long, TeacherProfile> teacherMap) {
        List<TeacherSkill> teacherSkills = new ArrayList<>();
        int i = 0;
        for (Long id : teacherMap.keySet()) {
            for (Long skillId : skillMap.keySet()) {
                TeacherSkill teacherSkill = new TeacherSkill(teacherMap.get(id), skillMap.get(skillId));
                teacherSkills.add(teacherSkill);
                log.info("******insert teacher skill {} {}", id, skillId);
                i++;
                if (i % 10000 == 0) {
                    entityManager.flush();
                    log.info("******flush");
                }
                if (i == REPEATED_COUNT) {
                    teacherSkillRepository.saveAll(teacherSkills);
                    return;
                }
            }
        }
    }

    private void insertReview(Map<Long, TeacherProfile> teacherMap, Map<Long, Member> memberMap) {
        List<Review> reviews = new ArrayList<>();

        List<Long> teacherKeys = new ArrayList<>(teacherMap.keySet());
        List<Long> memberKeys = new ArrayList<>(memberMap.keySet());
        for (int i = START; i < REPEATED_COUNT + 1; i++) {
            Member teacher = memberMap.get(teacherKeys.get(random.nextInt(teacherKeys.size())));
            Member student = memberMap.get(memberKeys.get(random.nextInt(memberKeys.size())));

            while (teacher.getId().equals(student.getId())) {
                student = memberMap.get(memberKeys.get(random.nextInt(memberKeys.size())));
            }

            Review review = dummyReview(teacher, student, "title" + i, "content" + i, "https://github.com/woowacourse-teams/2021-drop-the-code/pull/" + i, (long) (i % 5), Progress.ON_GOING);
            reviews.add(review);
            log.info("******insert review {}", i);
            if (i % 10000 == 0) {
                entityManager.flush();
                log.info("******flush");
            }
        }

        reviewRepository.saveAll(reviews);
    }

    private Review dummyReview(Member teacher, Member student, String title, String content, String prUrl, Long elapsedTime, Progress progress) {
        return Review.builder()
                     .teacher(teacher)
                     .student(student)
                     .title(title)
                     .content(content)
                     .prUrl(prUrl)
                     .elapsedTime(elapsedTime)
                     .progress(progress)
                     .build();
    }
}
