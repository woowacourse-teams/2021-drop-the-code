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
import com.wootech.dropthecode.domain.review.Review;
import com.wootech.dropthecode.repository.*;
import com.wootech.dropthecode.repository.bridge.LanguageSkillRepository;
import com.wootech.dropthecode.repository.bridge.TeacherLanguageRepository;
import com.wootech.dropthecode.repository.bridge.TeacherSkillRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile({"pt-init"})
@Component
public class LargeDataInitializer {
    public static final Integer REPEAT_COUNT = 50_000;

    private final MemberRepository memberRepository;
    private final TeacherProfileRepository teacherProfileRepository;
    private final LanguageRepository languageRepository;
    private final SkillRepository skillRepository;
    private final ReviewRepository reviewRepository;

    private final TeacherLanguageRepository teacherLanguageRepository;
    private final TeacherSkillRepository teacherSkillRepository;
    private final LanguageSkillRepository languageSkillRepository;

    public LargeDataInitializer(MemberRepository memberRepository, TeacherProfileRepository teacherProfileRepository, LanguageRepository languageRepository, SkillRepository skillRepository, ReviewRepository reviewRepository, TeacherLanguageRepository teacherLanguageRepository, TeacherSkillRepository teacherSkillRepository, LanguageSkillRepository languageSkillRepository) {
        this.memberRepository = memberRepository;
        this.teacherProfileRepository = teacherProfileRepository;
        this.languageRepository = languageRepository;
        this.skillRepository = skillRepository;
        this.reviewRepository = reviewRepository;
        this.teacherLanguageRepository = teacherLanguageRepository;
        this.teacherSkillRepository = teacherSkillRepository;
        this.languageSkillRepository = languageSkillRepository;
    }

    @Transactional
    @Bean
    public void initialize() {
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

            Map<Long, TeacherProfile> teacherMap = insertTeacherProfile(i, memberMap)
                    .stream()
                    .collect(Collectors.toMap(TeacherProfile::getId, Function.identity()));

            insertTeacherLanguage(languageMap, i, teacherMap);

            List<TeacherSkill> teacherSkills = Arrays.asList(
                    new TeacherSkill(teacherMap.get(1L + i * 6), skillMap.get(2L)),
                    new TeacherSkill(teacherMap.get(1L + i * 6), skillMap.get(3L)),
                    new TeacherSkill(teacherMap.get(1L + i * 6), skillMap.get(5L)),

                    new TeacherSkill(teacherMap.get(2L + i * 6), skillMap.get(1L)),
                    new TeacherSkill(teacherMap.get(2L + i * 6), skillMap.get(2L)),

                    new TeacherSkill(teacherMap.get(3L + i * 6), skillMap.get(1L)),
                    new TeacherSkill(teacherMap.get(3L + i * 6), skillMap.get(4L)),

                    new TeacherSkill(teacherMap.get(4L + i * 6), skillMap.get(3L)),
                    new TeacherSkill(teacherMap.get(4L + i * 6), skillMap.get(4L)),
                    new TeacherSkill(teacherMap.get(4L + i * 6), skillMap.get(5L)),

                    new TeacherSkill(teacherMap.get(5L + i * 6), skillMap.get(4L)),
                    new TeacherSkill(teacherMap.get(5L + i * 6), skillMap.get(5L)),

                    new TeacherSkill(teacherMap.get(6L + i * 6), skillMap.get(4L)),
                    new TeacherSkill(teacherMap.get(6L + i * 6), skillMap.get(5L))
            );
            teacherSkillRepository.saveAll(teacherSkills);

            insertReview(memberMap, i);
        }
    }

    private void insertTeacherLanguage(Map<Long, Language> languageMap, long i, Map<Long, TeacherProfile> teacherMap) {
        List<TeacherLanguage> teacherLanguages = Arrays.asList(
                new TeacherLanguage(teacherMap.get(1L + 6 * i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(1L + 6 * i), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(1L + 6 * i), languageMap.get(3L)),

                new TeacherLanguage(teacherMap.get(2L + 6 * i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(2L + 6 * i), languageMap.get(5L)),

                new TeacherLanguage(teacherMap.get(3L + 6 * i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(3L + 6 * i), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(3L + 6 * i), languageMap.get(4L)),

                new TeacherLanguage(teacherMap.get(4L + 6 * i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(4L + 6 * i), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(4L + 6 * i), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(4L + 6 * i), languageMap.get(4L)),

                new TeacherLanguage(teacherMap.get(5L + 6 * i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(5L + 6 * i), languageMap.get(3L)),

                new TeacherLanguage(teacherMap.get(6L + 6 * i), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(6L + 6 * i), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(6L + 6 * i), languageMap.get(5L))
        );
        teacherLanguageRepository.saveAll(teacherLanguages);
    }

    private Member dummyMember(String email, String name, String imageUrl, String githubUrl, Role role) {
        return Member.builder()
                     .email(email)
                     .name(name)
                     .imageUrl(imageUrl)
                     .githubUrl(githubUrl)
                     .role(role)
                     .build();
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

    private List<TeacherProfile> insertTeacherProfile(long i, Map<Long, Member> memberMap) {
        List<TeacherProfile> teacherProfiles = Arrays.asList(
                dummyTeacherProfile("배민 출신 백엔드 개발자", "배민 1타 강사. 열심히 가르쳐드리겠습니다.", 5, 2, 1.3, memberMap.get(1L + i * 6)),
                dummyTeacherProfile("쿠팡 이츠 출신 백엔드 개발자", "빡세게 가르쳐드리겠습니다.", 4, 5, 1.2, memberMap.get(2L + i * 6)),
                dummyTeacherProfile("우테코 갓 졸업생", "열심히 하겠습니다.", 10, 10, 5.3, memberMap.get(3L + i * 6)),
                dummyTeacherProfile("배민 인프라 담당자", "화이팅", 2, 1, 10.1, memberMap.get(4L + i * 6)),
                dummyTeacherProfile("Amazon 서버 개발자", "실리콘밸리 출신 인프라 개발자. 영어로 수업합니다.", 7, 3, 2.9, memberMap.get(5L + i * 6)),
                dummyTeacherProfile("배민 프론트 개발자", "화이팅", 6, 1, 8.4, memberMap.get(6L + i * 6))
        );
        return teacherProfileRepository.saveAll(teacherProfiles);
    }

    private List<Member> insertMember() {
        List<Member> members = Arrays.asList(
                dummyMember("seed@gmail.com", "Seed", "https://avatars.githubusercontent.com/u/56301069?v=4", "https://github.com", Role.TEACHER),
                dummyMember("allie@gmail.com", "Allie", "https://avatars.githubusercontent.com/u/32974201?v=4", "https://github.com", Role.TEACHER),
                dummyMember("bran@gmail.com", "Bran", "https://avatars.githubusercontent.com/u/52202474?v=4", "https://github.com", Role.TEACHER),
                dummyMember("air@gmail.com", "Air", "https://avatars.githubusercontent.com/u/45876793?v=4", "https://github.com", Role.TEACHER),
                dummyMember("fafi@gmail.com", "Fafi", "https://avatars.githubusercontent.com/u/50273712?v=44", "https://github.com", Role.TEACHER),
                dummyMember("shinse@gmail.com", "Shinsehantan", "https://avatars.githubusercontent.com/u/50273712?v=44", "https://github.com", Role.TEACHER)
        );
        return memberRepository.saveAll(members);
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

    private void insertReview(Map<Long, Member> memberMap, long i) {
        List<Review> reviews = Arrays.asList(
                dummyReview(memberMap.get(3L + i * 6), memberMap.get(2L+ i * 6), "[1단계 - 자동차 경주 구현] 에어(김준서) 미션 제출합니다.", "안녕하세요! 에어라고 해요! 많이 부족하겠지만 잘 부탁드려요!!\n" +
                        "코드 리뷰를 한 번도 받아본 적 없어서 많이 떨리지만 기대도 많이 되네요 ㅎㅎ", "https://github.com/woowacourse/java-racingcar/pull/159", 3L, Progress.ON_GOING),

                dummyReview(memberMap.get(4L+ i * 6), memberMap.get(2L+ i * 6), "[1단계 - 로또 구현] 에어(김준서) 미션 제출합니다.", "안녕하세요! 에어라고해요! 반갑습니다\n" +
                        "코드 리뷰 잘 부탁드릴게요 ㅎㅎ\n" +
                        "\n" +
                        "이번에 난생 처음으로 테스트코드부터 구현해봤어요. 처음이라 조금 어색한 느낌도 들고 맞게 한지는 모르겠지만 열심히 해봤습니다!\n" +
                        "이번 코드 리뷰를 통해서도 많이 성장할 수 있을 것 같아서 기대되네요 ㅎㅎ\n" +
                        "많은 피드백 부탁드려요", "https://github.com/woowacourse/java-lotto/pull/268", 2L, Progress.ON_GOING),

                dummyReview(memberMap.get(5L+ i * 6), memberMap.get(2L+ i * 6), "[2단계 - todo list] 에어(김준서) 미션 제출합니다.", "안녕하세요! 에어입니다!\n" +
                        "자바스크립트 정말 알듯말듯 하네요.", "https://github.com/woowacourse/js-todo-list-step2/pull/6", 1L, Progress.ON_GOING),

                dummyReview(memberMap.get(6L+ i * 6), memberMap.get(2L+ i * 6), "[1단계 - 블랙잭 구현] 에어(김준서) 미션 제출합니다. ", "안녕하세요!!  에어라고 해요! 잘 부탁드립니다\n스스로 고칠 것이 너무 많이 보이는 것 같아요ㅠㅠ\n" +
                        "이번 코드 리뷰를 통해서도 많이 배울 것을 기대하고 있습니다! ㅎㅎ 많은 피드백 부탁드려요~~\n" +
                        "리뷰를 통해 좋은 코드로 변화시켜보고 싶습니다. 많은 힌트와 리뷰 부탁드릴게요!!\n", "https://github.com/woowacourse/java-blackjack/pull/141", 3L, Progress.TEACHER_COMPLETED),

                dummyReview(memberMap.get(2L+ i * 6), memberMap.get(4L+ i * 6), "리뷰 상태 업데이트 기능 구현", "기능 구현\n" +
                        "리뷰 상태 업데이트\n" +
                        "변경 사항\n" +
                        "도메인에서 상태 업데이트가 필요한데 final로 정의된 부분 모두 수정\n" +
                        "고려 사항\n" +
                        "TeacherProfile ID와 Member ID가 통합된 경우 바뀔 수 있는 부분이 있는 지 확인해야함", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/157", 2L, Progress.ON_GOING),

                dummyReview(memberMap.get(2L+ i * 6), memberMap.get(6L+ i * 6), "리뷰 생성 기능 구현", "놓친 부분 있으면 마구마구 말씀해주십쇼!!!!!!!\n" +
                        "\n" +
                        "고려해야 할 부분\n" +
                        "리뷰 생성 시 추가적인 예외 처리가 필요한 부분이 있는지 확인해야 할 것 같음", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/163", 1L, Progress.ON_GOING),

                dummyReview(memberMap.get(2L+ i * 6), memberMap.get(4L+ i * 6), "TeacherProfilie DB에 Member ID 적용되지 않는 버그 수정", "변경사항\n" +
                        "반복문으로 Member Id 지정해주는 코드 추가\n" +
                        "@TaewanKimmmm 파피 피드백 적용\n" +
                        "데이터 초기화 방법\n" +
                        "로컬에서 DB 초기화 방법\n" +
                        "local properties에서 ddl-auto create으로 변경\n" +
                        "DB DataInitializer ActiveProfiles에 \"local\" 추가\n" +
                        "실행하면 DB 초기화 됨\n" +
                        "코드 원상 복구\n" +
                        "배포 서버에서 DB 초기화 방법\n" +
                        "서버에 있는 Jenkins-init-deploy.sh 실행 -> ActiveProfiles가 \"prod-init\"으로 설정되어 있음\n" +
                        "실행하면 DB 초기화 됨\n" +
                        "다시 Jenkins-deploy.sh 실행", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/158", 2L, Progress.ON_GOING),

                dummyReview(memberMap.get(2L+ i * 6), memberMap.get(4L+ i * 6), "review 데이터 초기화 코드 추가", "기능 구현\n" +
                        "Review 더미 데이터 초기화하는 코드 추가", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/150", 3L, Progress.ON_GOING),

                dummyReview(memberMap.get(2L+ i * 6), memberMap.get(6L+ i * 6), "ReviewController 응답 헤더의 URI 수정", "ReviewController 응답 헤더의 URI 수정", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/171", 3L, Progress.TEACHER_COMPLETED),

                dummyReview(memberMap.get(2L+ i * 6), memberMap.get(6L+ i * 6), "members/me, 리뷰어 상세 조회, 리뷰어 전체 조회 시 응답에 github url 필드 추가", "추가했습니다~", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/198", 1L, Progress.TEACHER_COMPLETED),

                dummyReview(memberMap.get(2L+ i * 6), memberMap.get(3L+ i * 6), "API 문서화", "rest docs 적용", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/37", 2L, Progress.FINISHED),

                dummyReview(memberMap.get(2L+ i * 6), memberMap.get(3L+ i * 6), "리뷰어 단일 조회", "리뷰어 단일 조회 기능 구현", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/164", 2L, Progress.FINISHED),

                dummyReview(memberMap.get(2L+ i * 6), memberMap.get(3L+ i * 6), "리뷰어 목록 조회 예외 및 테스트 코드 작성", "목록 조회 시 발생 가능한 예외 처리\n" +
                        "필수 필드값(ex. langauge)이 없는 경우\n" +
                        "DB에 없는 언어 혹은 기술을 입력한 경우\n" +
                        "허용하지 않은 정렬 조건, 즉 TeacherProfile이 필드로 갖고 있지 않은 값으로 정렬을 하려고 할 경우\n" +
                        "@MapsId를 이용하여 TeacherID와 MemberID를 하나의 키로 결합", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/156", 3L, Progress.FINISHED),

                dummyReview(memberMap.get(2L+ i * 6), memberMap.get(3L+ i * 6), "Logback으로 Error 로그, Info 로그 남기기", "로깅 적용!", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/182", 3L, Progress.FINISHED)
        );
        reviewRepository.saveAll(reviews);
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
                new LanguageSkill(languageMap.get(3L), skillMap.get(5L)),
                new LanguageSkill(languageMap.get(4L), skillMap.get(4L)),
                new LanguageSkill(languageMap.get(5L), skillMap.get(4L))
        );
        languageSkillRepository.saveAll(languageSkills);
    }
}