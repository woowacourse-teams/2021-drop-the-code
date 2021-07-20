package com.wootech.dropthecode.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.*;
import com.wootech.dropthecode.domain.bridge.LanguageSkill;
import com.wootech.dropthecode.domain.bridge.TeacherLanguage;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;
import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherFilterRequest;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.dto.response.TeacherPaginationResponse;
import com.wootech.dropthecode.dto.response.TeacherProfileResponse;
import com.wootech.dropthecode.repository.LanguageRepository;
import com.wootech.dropthecode.repository.MemberRepository;
import com.wootech.dropthecode.repository.SkillRepository;
import com.wootech.dropthecode.repository.TeacherProfileRepository;
import com.wootech.dropthecode.repository.bridge.TeacherLanguageRepository;
import com.wootech.dropthecode.repository.bridge.TeacherSkillRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService {

    private final TeacherProfileRepository teacherProfileRepository;
    private final LanguageRepository languageRepository;
    private final MemberRepository memberRepository;

    private final TeacherLanguageRepository teacherLanguageRepository;
    private final TeacherSkillRepository teacherSkillRepository;

    private final List<String> defaultSkills;

    public TeacherService(TeacherProfileRepository teacherProfileRepository, LanguageRepository languageRepository, SkillRepository skillRepository, MemberRepository memberRepository, TeacherLanguageRepository teacherLanguageRepository, TeacherSkillRepository teacherSkillRepository, List<String> defaultSkills) {
        this.teacherProfileRepository = teacherProfileRepository;
        this.languageRepository = languageRepository;
        this.memberRepository = memberRepository;

        this.teacherLanguageRepository = teacherLanguageRepository;
        this.teacherSkillRepository = teacherSkillRepository;
        this.defaultSkills = defaultSkills;
    }

    @Transactional
    public void registerTeacher(LoginMember loginMember, TeacherRegistrationRequest teacherRegistrationRequest) {
        // member 정보 가져오기
        Member member = memberRepository.findById(loginMember.getId())
                                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        // 선생님 등록을 요청한 member 가 선생님으로 이미 등록된 사용자인지 확인
        if (member.getRole() == Role.TEACHER) {
            throw new IllegalArgumentException("이미 리뷰어로 등록된 사용자입니다.");
        }

        Map<String, Language> languageMap = languageRepository.findAll()
                                                              .stream()
                                                              .collect(Collectors.toMap(Language::getName, Function.identity()));
        Map<String, Skill> skillMap = languageMap.values()
                                                 .stream()
                                                 .map(Language::getSkills)
                                                 .flatMap(Collection::stream)
                                                 .map(LanguageSkill::getSkill)
                                                 .distinct()
                                                 .collect(Collectors.toMap(Skill::getName, Function.identity()));
        // 각각의 언어/기술 조회 및 존재 유무 검증
        List<Language> languages = teacherRegistrationRequest.getTechSpecs()
                                                             .stream()
                                                             .map(TechSpec::getLanguage)
                                                             .map(languageName -> Optional.ofNullable(languageMap.get(languageName)))
                                                             .map(languageOptional -> languageOptional.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 언어입니다.")))
                                                             .collect(Collectors.toList());

        List<Skill> skills = teacherRegistrationRequest.getTechSpecs()
                                                       .stream()
                                                       .map(TechSpec::getSkills)
                                                       .flatMap(Collection::stream)
                                                       .map(skillName -> Optional.ofNullable(skillMap.get(skillName)))
                                                       .map(skill -> skill.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기술입니다.")))
                                                       .collect(Collectors.toList());

        // 각 기술들이 언어에 포함되는 기술인지 검증
        teacherRegistrationRequest.getTechSpecs()
                                  .forEach(techSpec -> techSpec.validateSkillsInLanguage(languageMap.get(techSpec.getLanguage())));

        // member 역할 변경
        member.setRole(Role.TEACHER);
        memberRepository.save(member);

        // TeacherProfile 저장
        TeacherProfile teacherProfile = new TeacherProfile(teacherRegistrationRequest.getTitle(), teacherRegistrationRequest
                .getContent(), teacherRegistrationRequest.getCareer(), member);
        TeacherProfile savedTeacherProfile = teacherProfileRepository.save(teacherProfile);

        // 선생님 언어/기술 관계 저장
        List<TeacherLanguage> teacherLanguages = languages.stream()
                                                          .map(language -> new TeacherLanguage(savedTeacherProfile, language))
                                                          .collect(Collectors.toList());
        teacherLanguageRepository.saveAll(teacherLanguages);

        List<TeacherSkill> teacherSkills = skills.stream()
                                                 .map(skill -> new TeacherSkill(savedTeacherProfile, skill))
                                                 .collect(Collectors.toList());
        teacherSkillRepository.saveAll(teacherSkills);
    }

    @Transactional(readOnly = true)
    public TeacherPaginationResponse findAll(TeacherFilterRequest teacherFilterRequest, Pageable pageable) {
        List<String> skills = teacherFilterRequest.getTechSpec().getSkills();
        if (skills.isEmpty()) {
            skills = defaultSkills;
        }

        Page<TeacherProfile> teacherProfilePage =
                teacherProfileRepository.findDistinctAllByLanguagesLanguageNameAndSkillsSkillNameInAndCareerGreaterThanEqual(
                        pageable,
                        teacherFilterRequest.getTechSpec().getLanguage(),
                        skills,
                        teacherFilterRequest.getCareer());

        final List<TeacherProfileResponse> teacherProfiles = teacherProfilePage.stream()
                                                                               .map(TeacherProfileResponse::from)
                                                                               .collect(Collectors.toList());
        return new TeacherPaginationResponse(teacherProfiles, teacherProfilePage.getTotalPages());
    }
}
