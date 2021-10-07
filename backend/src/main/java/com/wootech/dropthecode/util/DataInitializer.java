package com.wootech.dropthecode.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local-init")
@Component
public class DataInitializer implements ApplicationRunner {
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

    public DataInitializer(MemberRepository memberRepository, TeacherProfileRepository teacherProfileRepository, LanguageRepository languageRepository, SkillRepository skillRepository, ReviewRepository reviewRepository, TeacherLanguageRepository teacherLanguageRepository, TeacherSkillRepository teacherSkillRepository, LanguageSkillRepository languageSkillRepository, Environment environment) {
        this.memberRepository = memberRepository;
        this.teacherProfileRepository = teacherProfileRepository;
        this.languageRepository = languageRepository;
        this.skillRepository = skillRepository;
        this.reviewRepository = reviewRepository;
        this.teacherLanguageRepository = teacherLanguageRepository;
        this.teacherSkillRepository = teacherSkillRepository;
        this.languageSkillRepository = languageSkillRepository;
        this.environment = environment;
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

        if (!environment.acceptsProfiles(Profiles.of("local-init"))) {
            return;
        }

        Map<Long, Member> memberMap = insertMember()
                .stream()
                .collect(Collectors.toMap(Member::getId, Function.identity()));

        Map<Long, TeacherProfile> teacherMap = insertTeacherProfile(memberMap)
                .stream()
                .collect(Collectors.toMap(TeacherProfile::getId, Function.identity()));

        insertTeacherLanguage(languageMap, teacherMap);
        insertTeacherSkill(skillMap, teacherMap);

        insertReview(memberMap);
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
        List<Member> members = Arrays.asList(
                realMember("67591151", "shinse@gmail.com", "Shinsehantan", "https://avatars.githubusercontent.com/u/50273712?v=44", "https://github.com/shinsehantan", Role.STUDENT),

                realMember("45876793", "air@gmail.com", "Air", "https://avatars.githubusercontent.com/u/45876793?v=4", "https://github.com/KJunseo", Role.TEACHER),
                realMember("56301069", "seed@gmail.com", "Seed", "https://avatars.githubusercontent.com/u/56301069?v=4", "https://github.com/hsik0225", Role.TEACHER),
                realMember("32974201", "allie@gmail.com", "Allie", "https://avatars.githubusercontent.com/u/32974201?v=4", "https://github.com/jh8579", Role.TEACHER),
                realMember("52202474", "bran@gmail.com", "Bran", "https://avatars.githubusercontent.com/u/52202474?v=4", "https://github.com/seojihwan", Role.TEACHER),
                realMember("50273712", "fafi@gmail.com", "Fafi", "https://avatars.githubusercontent.com/u/50273712?v=44", "https://github.com/TaewanKimmmm", Role.TEACHER),
                dummyMember("calvin0627@naver.com", "Calvin", "https://avatars.githubusercontent.com/u/29232608?v=4", "https://github.com/calvin0627", Role.TEACHER),

                dummyMember("syndra9106@naver.com", "Syndra", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Syndra_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("anivia0627@naver.com", "Anivia", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Anivia_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("faker96@gmail.com", "Faker", "https://file.newswire.co.kr/data/datafile2/thumb_640/2021/06/1981906469_20210610110159_5423614022.jpg", "https://github.com", Role.TEACHER),
                dummyMember("hotcurry@gmail.com", "Curry", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxO9huSTvjzYvTKMdAhbm53dnrlNyLFzBnyw&usqp=CAU", "https://github.com", Role.TEACHER),
                dummyMember("fiora119@gmail.com", "Fiora", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Fiora_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("mushroom@gmail.com", "Teemo", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Teemo_0.jpg", "https://github.com", Role.TEACHER),

                dummyMember("talonkillyou@naver.com", "Talon", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Talon_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("lasthit@naver.com", "Karthus", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Karthus_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("airplane33@gmail.com", "Corki", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Corki_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("icearrow@gmail.com", "Ashe", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Ashe_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("gonggipang@gmail.com", "Orianna", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Orianna_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("prison@gmail.com", "Thresh", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Thresh_0.jpg", "https://github.com", Role.TEACHER),

                dummyMember("harp33@naver.com", "Sona", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Sona_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("mosquito@naver.com", "Vladimir", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Vladimir_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("laser@gmail.com", "Viktor", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Viktor_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("beinchung@gmail.com", "Vayne", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Vayne_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("madlife@gmail.com", "Blitzcrank", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Blitzcrank_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("manhgomanheun@gmail.com", "Ezreal", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Ezreal_0.jpg", "https://github.com", Role.TEACHER),

                dummyMember("gumiho99@naver.com", "Ahri", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Ahri_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("ninja1@naver.com", "Akali", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Akali_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("turnofflight@gmail.com", "Nocturne", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Nocturne_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("harrypotter1111@gmail.com", "LeBlanc", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Leblanc_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("moon33@gmail.com", "Diana", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Diana_0.jpg", "https://github.com", Role.TEACHER),
                dummyMember("drmundo@gmail.com", "Mundo", "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/DrMundo_0.jpg", "https://github.com", Role.TEACHER)
        );

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

    private Member dummyMember(String email, String name, String imageUrl, String githubUrl, Role role) {
        return Member.builder()
                     .email(email)
                     .name(name)
                     .imageUrl(imageUrl)
                     .githubUrl(githubUrl)
                     .role(role)
                     .build();
    }

    private List<TeacherProfile> insertTeacherProfile(Map<Long, Member> memberMap) {
        List<TeacherProfile> teacherProfiles = Arrays.asList(
                dummyTeacherProfile("우아한형제들 백엔드 개발자", "객체 지향, 클린 코드를 지향합니다.\n" + "주입식이 아닌 생각할 수 있는 코드리뷰를 제공해드립니다.", random
                        .nextInt(20) + 1, random.nextInt(100) + 1, Math.round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap
                        .get(2L)),
                dummyTeacherProfile("우아한형제들 백엔드 개발자", "코드 한 줄도 놓치지 않고 꼼꼼하게 봐드립니다.\n" + "명쾌한 리뷰 해드립니다.", random.nextInt(20) + 1, random
                        .nextInt(100) + 1, Math.round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(3L)),
                dummyTeacherProfile("우아한형제들 인프라 담당자", "코드의 변화를 직접 느껴보세요.\n" + "여러 강의나 멘토링도 제공하고 있습니다.", random.nextInt(20) + 1, random
                        .nextInt(100) + 1, Math.round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(4L)),
                dummyTeacherProfile("우아한형제들 프론트엔드 개발자", "노하우를 전수해드립니다.\n" + "지식 공유를 좋아합니다.\n" + "후회하지 않으실 거에요.", random.nextInt(20) + 1, random
                        .nextInt(100) + 1, Math.round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(5L)),
                dummyTeacherProfile("우아한형제들 백엔드 개발자", "당신의 코드를 레벨업 시켜드립니다!\n" + "여러 분야에 대한 경험이 많습니다.", random.nextInt(20) + 1, random
                        .nextInt(100) + 1, Math.round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(6L)),
                dummyTeacherProfile("네이버 쇼핑 프론트 개발자", "후회하지 않으실겁니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(7L)),

                dummyTeacherProfile("카카오 엔터프라이즈 개발 팀장", "카카오 1타 강사. 열심히 가르쳐드리겠습니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(8L)),
                dummyTeacherProfile("쿠팡 이츠 앱 개발자", "소통을 통해 성장시켜드립니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(9L)),
                dummyTeacherProfile("ios 마스터", "ios의 모든 것을 가르쳐드립니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(10L)),
                dummyTeacherProfile("네이버 웹툰 개발 팀장", "더 좋은 리뷰로 찾아뵙겠습니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(11L)),
                dummyTeacherProfile("Amazon 서버 개발자", "생각하는 힘을 길러드립니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(12L)),
                dummyTeacherProfile("라이엇 챔피언 개발자", "코딩의 재미를 느끼게 해드릴게요.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(13L)),

                dummyTeacherProfile("당근마켓 프론트 개발자", "당근마켓 1타 강사. 열심히 가르쳐드리겠습니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(14L)),
                dummyTeacherProfile("토스 인프라 관리자", "하나하나 빡세게 가르쳐드리겠습니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(15L)),
                dummyTeacherProfile("라인 게임 플랫폼 개발자", "제대로 해드립니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math.round((random
                        .nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(16L)),
                dummyTeacherProfile("네이버 클라우드 개발자", "경험해보지 못한 코드리뷰.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(17L)),
                dummyTeacherProfile("구글 백엔드 개발자", "영어도 가능합니다ㅎㅎ", random.nextInt(20) + 1, random.nextInt(100) + 1, Math.round((random
                        .nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(18L)),
                dummyTeacherProfile("페이스북 프론트엔드 개발자", "최신 기술도 가르쳐 드려요", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(19L)),

                dummyTeacherProfile("카카오톡 개발자", "카카오톡 개발자입니다. 열심히 가르쳐드리겠습니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(20L)),
                dummyTeacherProfile("카카오 안드로이드 개발자", "안드로이드 코드리뷰 받아보셨나요? 제가 해드립니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(21L)),
                dummyTeacherProfile("쿠팡 웹 서비스 개발자", "함께 성장해가는 코드리뷰", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(22L)),
                dummyTeacherProfile("카카오 보이스톡 개발자", "후회없게 해드립니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math.round((random
                        .nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(23L)),
                dummyTeacherProfile("실리콘 밸리 출신 백엔드 개발자", "실리콘밸리 출신 백엔드 개발자. 영어도 가능합니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(24L)),
                dummyTeacherProfile("opgg 서버 개발자", "후회하신다면 환불해드립니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(25L)),

                dummyTeacherProfile("업비트 서버 개발자", "최선을 다하겠습니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math.round((random
                        .nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(26L)),
                dummyTeacherProfile("카카오 웹툰 ios 개발자", "지식을 나눌 때 기쁨을 느낍니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(27L)),
                dummyTeacherProfile("안드로이드 마스터", "안드로이드의 모든 것 제가 가르쳐드립니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(28L)),
                dummyTeacherProfile("자바스크립트의 아버지", "자바스크립트 그렇게 쓰는 거 아닌데", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(29L)),
                dummyTeacherProfile("인프런 서버 개발자", "효율적인 코드를 짤 수 있게 해드립니다.", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(30L)),
                dummyTeacherProfile("뱅크샐러드 프론트엔드 개발자", "화이팅 넘치는 코드리뷰!", random.nextInt(20) + 1, random.nextInt(100) + 1, Math
                        .round((random.nextDouble() * 7 + 1) * 10) / 10.0, memberMap.get(31L))
        );
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
        List<TeacherLanguage> teacherLanguages = Arrays.asList(
                new TeacherLanguage(teacherMap.get(2L), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(2L), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(2L), languageMap.get(5L)),

                new TeacherLanguage(teacherMap.get(3L), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(3L), languageMap.get(4L)),
                new TeacherLanguage(teacherMap.get(3L), languageMap.get(5L)),

                new TeacherLanguage(teacherMap.get(4L), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(4L), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(4L), languageMap.get(4L)),

                new TeacherLanguage(teacherMap.get(5L), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(5L), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(5L), languageMap.get(4L)),

                new TeacherLanguage(teacherMap.get(6L), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(6L), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(6L), languageMap.get(3L)),

                new TeacherLanguage(teacherMap.get(7L), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(7L), languageMap.get(2L)),

                new TeacherLanguage(teacherMap.get(8L), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(8L), languageMap.get(4L)),

                new TeacherLanguage(teacherMap.get(9L), languageMap.get(4L)),
                new TeacherLanguage(teacherMap.get(9L), languageMap.get(5L)),

                new TeacherLanguage(teacherMap.get(10L), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(10L), languageMap.get(5L)),

                new TeacherLanguage(teacherMap.get(11L), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(11L), languageMap.get(4L)),

                new TeacherLanguage(teacherMap.get(12L), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(12L), languageMap.get(4L)),

                new TeacherLanguage(teacherMap.get(13L), languageMap.get(4L)),
                new TeacherLanguage(teacherMap.get(13L), languageMap.get(5L)),

                new TeacherLanguage(teacherMap.get(14L), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(14L), languageMap.get(3L)),

                new TeacherLanguage(teacherMap.get(15L), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(15L), languageMap.get(4L)),

                new TeacherLanguage(teacherMap.get(16L), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(17L), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(18L), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(19L), languageMap.get(4L)),
                new TeacherLanguage(teacherMap.get(20L), languageMap.get(5L)),

                new TeacherLanguage(teacherMap.get(21L), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(22L), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(23L), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(24L), languageMap.get(4L)),
                new TeacherLanguage(teacherMap.get(25L), languageMap.get(5L)),

                new TeacherLanguage(teacherMap.get(26L), languageMap.get(1L)),
                new TeacherLanguage(teacherMap.get(27L), languageMap.get(2L)),
                new TeacherLanguage(teacherMap.get(28L), languageMap.get(3L)),
                new TeacherLanguage(teacherMap.get(29L), languageMap.get(4L)),
                new TeacherLanguage(teacherMap.get(30L), languageMap.get(5L)),

                new TeacherLanguage(teacherMap.get(31L), languageMap.get(1L))
        );
        teacherLanguageRepository.saveAll(teacherLanguages);
    }

    private void insertTeacherSkill(Map<Long, Skill> skillMap, Map<Long, TeacherProfile> teacherMap) {
        List<TeacherSkill> teacherSkills = Arrays.asList(
                new TeacherSkill(teacherMap.get(2L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(2L), skillMap.get(2L)),
                new TeacherSkill(teacherMap.get(2L), skillMap.get(3L)),

                new TeacherSkill(teacherMap.get(3L), skillMap.get(1L)),

                new TeacherSkill(teacherMap.get(4L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(4L), skillMap.get(5L)),

                new TeacherSkill(teacherMap.get(5L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(5L), skillMap.get(3L)),
                new TeacherSkill(teacherMap.get(5L), skillMap.get(5L)),

                new TeacherSkill(teacherMap.get(6L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(6L), skillMap.get(2L)),
                new TeacherSkill(teacherMap.get(6L), skillMap.get(3L)),
                new TeacherSkill(teacherMap.get(6L), skillMap.get(5L)),

                new TeacherSkill(teacherMap.get(7L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(7L), skillMap.get(2L)),

                new TeacherSkill(teacherMap.get(8L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(8L), skillMap.get(5L)),

                new TeacherSkill(teacherMap.get(9L), skillMap.get(1L)),

                new TeacherSkill(teacherMap.get(10L), skillMap.get(1L)),

                new TeacherSkill(teacherMap.get(11L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(11L), skillMap.get(5L)),

                new TeacherSkill(teacherMap.get(12L), skillMap.get(1L)),

                new TeacherSkill(teacherMap.get(13L), skillMap.get(1L)),

                new TeacherSkill(teacherMap.get(14L), skillMap.get(2L)),
                new TeacherSkill(teacherMap.get(14L), skillMap.get(5L)),

                new TeacherSkill(teacherMap.get(15L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(15L), skillMap.get(5L)),

                new TeacherSkill(teacherMap.get(16L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(17L), skillMap.get(2L)),
                new TeacherSkill(teacherMap.get(18L), skillMap.get(5L)),
                new TeacherSkill(teacherMap.get(19L), skillMap.get(1L)),

                new TeacherSkill(teacherMap.get(21L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(22L), skillMap.get(2L)),
                new TeacherSkill(teacherMap.get(23L), skillMap.get(5L)),
                new TeacherSkill(teacherMap.get(24L), skillMap.get(1L)),

                new TeacherSkill(teacherMap.get(26L), skillMap.get(1L)),
                new TeacherSkill(teacherMap.get(27L), skillMap.get(2L)),
                new TeacherSkill(teacherMap.get(28L), skillMap.get(5L)),
                new TeacherSkill(teacherMap.get(29L), skillMap.get(1L)),

                new TeacherSkill(teacherMap.get(31L), skillMap.get(1L))
        );
        teacherSkillRepository.saveAll(teacherSkills);
    }

    private void insertReview(Map<Long, Member> memberMap) {
        List<Review> reviews = Arrays.asList(
                dummyReview(memberMap.get(3L), memberMap.get(2L), "[1단계 - 자동차 경주 구현] 에어(김준서) 미션 제출합니다.", "안녕하세요! 에어라고 해요! 많이 부족하겠지만 잘 부탁드려요!!\n" +
                        "코드 리뷰를 한 번도 받아본 적 없어서 많이 떨리지만 기대도 많이 되네요 ㅎㅎ", "https://github.com/woowacourse/java-racingcar/pull/159", 3L, Progress.ON_GOING),

                dummyReview(memberMap.get(4L), memberMap.get(2L), "[1단계 - 로또 구현] 에어(김준서) 미션 제출합니다.", "안녕하세요! 에어라고해요! 반갑습니다\n" +
                        "코드 리뷰 잘 부탁드릴게요 ㅎㅎ\n" +
                        "\n" +
                        "이번에 난생 처음으로 테스트코드부터 구현해봤어요. 처음이라 조금 어색한 느낌도 들고 맞게 한지는 모르겠지만 열심히 해봤습니다!\n" +
                        "이번 코드 리뷰를 통해서도 많이 성장할 수 있을 것 같아서 기대되네요 ㅎㅎ\n" +
                        "많은 피드백 부탁드려요", "https://github.com/woowacourse/java-lotto/pull/268", 2L, Progress.ON_GOING),

                dummyReview(memberMap.get(5L), memberMap.get(2L), "[2단계 - todo list] 에어(김준서) 미션 제출합니다.", "안녕하세요! 에어입니다!\n" +
                        "자바스크립트 정말 알듯말듯 하네요.", "https://github.com/woowacourse/js-todo-list-step2/pull/6", 1L, Progress.ON_GOING),

                dummyReview(memberMap.get(6L), memberMap.get(2L), "[1단계 - 블랙잭 구현] 에어(김준서) 미션 제출합니다. ", "안녕하세요!!  에어라고 해요! 잘 부탁드립니다\n스스로 고칠 것이 너무 많이 보이는 것 같아요ㅠㅠ\n" +
                        "이번 코드 리뷰를 통해서도 많이 배울 것을 기대하고 있습니다! ㅎㅎ 많은 피드백 부탁드려요~~\n" +
                        "리뷰를 통해 좋은 코드로 변화시켜보고 싶습니다. 많은 힌트와 리뷰 부탁드릴게요!!\n", "https://github.com/woowacourse/java-blackjack/pull/141", 3L, Progress.TEACHER_COMPLETED),

                dummyReview(memberMap.get(7L), memberMap.get(2L), "[1, 2, 3단계 - 체스] 에어(김준서) 미션 제출합니다. ", "안녕하세요! 에어입니다! 잘 부탁드려요\n" +
                        "많이 어렵고 막막하더라고요\n" +
                        "도움을 받으면서 고쳐나가려고 해요!! \n" +
                        "많이 피드백 해주세요!", "https://github.com/woowacourse/java-chess/pull/190", 2L, Progress.TEACHER_COMPLETED),

                dummyReview(memberMap.get(8L), memberMap.get(2L), "[1, 2단계 - Spring 적용하기] 에어(김준서) 미션 제출합니다.", "안녕하세요! 이번 미션을 함께하게 된 에어라고해요!!\n잘 부탁드립니다!\n" +
                        "예전에 스프링을 사용해보긴 했는데 제대로 이해하면서 사용했다기보단 그냥 검색해보면서 돌아가게만 했던 것 같아요.\n이번 기회에 제대로 다시 공부해서 사용하고 싶네요!! 많은 피드백 부탁드려요!!", "https://github.com/woowacourse/jwp-chess/pull/233", 2L, Progress.FINISHED),

                dummyReview(memberMap.get(9L), memberMap.get(2L), "[Spring 지하철 노선도 관리 - 1, 2단계] 에어(김준서) 미션 제출합니다. ", "안녕하세요! 에어라고 해요! 피드백 잘 부탁드립니다\n" +
                        "테스트 코드도 적용해봤는데, 테스트 코드는 아직 감이 잘 안오네요.. 테스트끼리 뭔가 의존적인 느낌도 들고 제대로 한지 잘 모르겠어요. 테스트 부분도 잘 봐주시면 감사하겠습니다!!", "https://github.com/woowacourse/atdd-subway-map/pull/71", 3L, Progress.FINISHED),

                dummyReview(memberMap.get(10L), memberMap.get(2L), "[Spring 지하철 경로 조회 - 1,2단계] 에어(김준서) 미션 제출합니다. ", "안녕하세요! 에어입니다!\n" +
                        "JWT를 처음 적용해봐서 제대로 한지 잘 모르겠네요ㅠㅠ\n" +
                        "피드백 잘 부탁드려요!!", "https://github.com/woowacourse/atdd-subway-path/pull/71", 3L, Progress.FINISHED),

                dummyReview(memberMap.get(11L), memberMap.get(2L), "[스프링 - 협업 미션] 에어(김준서) 미션 제출합니다.", "안녕하세요!! 에어라고 합니다! 처음 뵙네요!\n" +
                        "대단히 반갑습니다! 리뷰 잘 부탁드려요!!\n", "https://github.com/woowacourse/atdd-subway-fare/pull/29", 2L, Progress.FINISHED),

                dummyReview(memberMap.get(12L), memberMap.get(2L), "내가 받은 리뷰 목록 조회 기능 구현", "내가 받은 리뷰 목록 조회 기능 구현\n" +
                        "\n" +
                        "query dsl을 적용하여 조회 기능 구현\n" +
                        "\n" +
                        "페이지네이션, sort를 쉽게 적용하기 위해 QueryDsl 지원 클래스 만들어 적용\n" +
                        "where절 파라미터로 필터 기능 구현\n" +
                        "기본 페이징 기능과, 선생님 이름으로 리뷰 필터링, 리뷰 상태에 따른 필터링(복수의 상태 선택 가능)", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/153", 1L, Progress.FINISHED),

                dummyReview(memberMap.get(2L), memberMap.get(4L), "리뷰 상태 업데이트 기능 구현", "기능 구현\n" +
                        "리뷰 상태 업데이트\n" +
                        "변경 사항\n" +
                        "도메인에서 상태 업데이트가 필요한데 final로 정의된 부분 모두 수정\n" +
                        "고려 사항\n" +
                        "TeacherProfile ID와 Member ID가 통합된 경우 바뀔 수 있는 부분이 있는 지 확인해야함", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/157", 2L, Progress.ON_GOING),

                dummyReview(memberMap.get(2L), memberMap.get(6L), "리뷰 생성 기능 구현", "놓친 부분 있으면 마구마구 말씀해주십쇼!!!!!!!\n" +
                        "\n" +
                        "고려해야 할 부분\n" +
                        "리뷰 생성 시 추가적인 예외 처리가 필요한 부분이 있는지 확인해야 할 것 같음", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/163", 1L, Progress.ON_GOING),

                dummyReview(memberMap.get(2L), memberMap.get(4L), "TeacherProfilie DB에 Member ID 적용되지 않는 버그 수정", "변경사항\n" +
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

                dummyReview(memberMap.get(2L), memberMap.get(4L), "review 데이터 초기화 코드 추가", "기능 구현\n" +
                        "Review 더미 데이터 초기화하는 코드 추가", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/150", 3L, Progress.ON_GOING),

                dummyReview(memberMap.get(2L), memberMap.get(6L), "ReviewController 응답 헤더의 URI 수정", "ReviewController 응답 헤더의 URI 수정", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/171", 3L, Progress.TEACHER_COMPLETED),

                dummyReview(memberMap.get(2L), memberMap.get(6L), "members/me, 리뷰어 상세 조회, 리뷰어 전체 조회 시 응답에 github url 필드 추가", "추가했습니다~", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/198", 1L, Progress.TEACHER_COMPLETED),

                dummyReview(memberMap.get(2L), memberMap.get(3L), "API 문서화", "rest docs 적용", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/37", 2L, Progress.FINISHED),

                dummyReview(memberMap.get(2L), memberMap.get(3L), "리뷰어 단일 조회", "리뷰어 단일 조회 기능 구현", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/164", 2L, Progress.FINISHED),

                dummyReview(memberMap.get(2L), memberMap.get(3L), "리뷰어 목록 조회 예외 및 테스트 코드 작성", "목록 조회 시 발생 가능한 예외 처리\n" +
                        "필수 필드값(ex. langauge)이 없는 경우\n" +
                        "DB에 없는 언어 혹은 기술을 입력한 경우\n" +
                        "허용하지 않은 정렬 조건, 즉 TeacherProfile이 필드로 갖고 있지 않은 값으로 정렬을 하려고 할 경우\n" +
                        "@MapsId를 이용하여 TeacherID와 MemberID를 하나의 키로 결합", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/156", 3L, Progress.FINISHED),

                dummyReview(memberMap.get(2L), memberMap.get(3L), "Logback으로 Error 로그, Info 로그 남기기", "로깅 적용!", "https://github.com/woowacourse-teams/2021-drop-the-code/pull/182", 3L, Progress.FINISHED)
        );
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
