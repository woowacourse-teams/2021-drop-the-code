package com.wootech.dropthecode.service;

import java.util.*;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.*;
import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherFilterRequest;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.dto.response.TeacherPaginationResponse;
import com.wootech.dropthecode.dto.response.TeacherProfileResponse;
import com.wootech.dropthecode.exception.TeacherException;
import com.wootech.dropthecode.repository.TeacherProfileRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService {

    private final MemberService memberService;
    private final LanguageService languageService;
    private final SkillService skillService;
    private final TeacherLanguageService teacherLanguageService;
    private final TeacherSkillService teacherSkillService;
    private final TeacherProfileRepository teacherProfileRepository;

    public TeacherService(MemberService memberService, LanguageService languageService,
                          SkillService skillService,
                          TeacherLanguageService teacherLanguageService,
                          TeacherSkillService teacherSkillService,
                          TeacherProfileRepository teacherProfileRepository) {
        this.memberService = memberService;
        this.languageService = languageService;
        this.skillService = skillService;
        this.teacherLanguageService = teacherLanguageService;
        this.teacherSkillService = teacherSkillService;
        this.teacherProfileRepository = teacherProfileRepository;
    }

    @Transactional
    public TeacherProfile save(TeacherProfile teacherProfile) {
        return teacherProfileRepository.save(teacherProfile);
    }

    @Transactional
    public void registerTeacher(LoginMember loginMember, TeacherRegistrationRequest teacherRegistrationRequest) {
        Member member = memberService.findById(loginMember.getId());

        if (member.hasRole(Role.TEACHER)) {
            throw new TeacherException("이미 리뷰어로 등록된 사용자입니다.");
        }

        Map<String, Language> languageMap = languageService.findAllToMap();
        List<Language> languages = validateLanguageNamesExists(teacherRegistrationRequest.getTechSpecs(), languageMap);
        List<Skill> skills = validateSkillNamesExists(teacherRegistrationRequest.getTechSpecs(), skillService.findAllToMap());
        teacherRegistrationRequest.validateSkillsInLanguage(languageMap);

        TeacherProfile teacher = save(teacherRegistrationRequest.toTeacherProfileWithMember(member));
        teacherLanguageService.saveAllWithTeacher(languages, teacher);
        teacherSkillService.saveAllWithTeacher(skills, teacher);
        member.setRole(Role.TEACHER);
        memberService.save(member);
    }

    private List<Language> validateLanguageNamesExists(List<TechSpec> techSpecs, Map<String, Language> languageMap) {
        return techSpecs.stream()
                        .map(TechSpec::getLanguage)
                        .map(languageName -> Optional.ofNullable(languageMap.get(languageName)))
                        .map(languageOptional -> languageOptional.orElseThrow(() -> new TeacherException("존재하지 않는 언어입니다.")))
                        .collect(Collectors.toList());
    }

    private List<Skill> validateSkillNamesExists(List<TechSpec> techSpecs, Map<String, Skill> skillMap) {
        return techSpecs.stream()
                        .map(TechSpec::getSkills)
                        .flatMap(Collection::stream)
                        .map(skillName -> Optional.ofNullable(skillMap.get(skillName)))
                        .map(skill -> skill.orElseThrow(() -> new TeacherException("존재하지 않는 기술입니다.")))
                        .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeacherPaginationResponse findAll(TeacherFilterRequest teacherFilterRequest, Pageable pageable) {
        List<Language> languages = validateLanguageNamesExists(Collections.singletonList(teacherFilterRequest.getTechSpec()), languageService.findAllToMap());
        List<Skill> skills = validateSkillNamesExists(Collections.singletonList(teacherFilterRequest.getTechSpec()), skillService.findAllToMap());

        Page<TeacherProfile> teacherProfilePage = teacherProfileRepository.findAll(
                languages,
                skills,
                teacherFilterRequest.getCareer(),
                pageable
        );

        final List<TeacherProfileResponse> teacherProfileResponses = teacherProfilePage.stream()
                                                                                       .map(TeacherProfileResponse::from)
                                                                                       .collect(Collectors.toList());

        return new TeacherPaginationResponse(teacherProfileResponses, teacherProfilePage.getTotalPages());
    }

    public TeacherProfileResponse findTeacherResponseById(Long id) {
        return TeacherProfileResponse.from(findEntityById(id));
    }

    private TeacherProfile findEntityById(Long id) {
        return teacherProfileRepository.findById(id).orElseThrow(() -> new TeacherException("존재하지 않는 리뷰어의 ID 입니다."));
    }
}

