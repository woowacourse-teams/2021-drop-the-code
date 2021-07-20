package com.wootech.dropthecode.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.*;
import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherFilterRequest;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.dto.response.TeacherPaginationResponse;
import com.wootech.dropthecode.dto.response.TeacherProfileResponse;
import com.wootech.dropthecode.exception.AuthorizationException;
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

    private final List<String> defaultSkills;

    public TeacherService(MemberService memberService, LanguageService languageService, SkillService skillService, TeacherLanguageService teacherLanguageService, TeacherSkillService teacherSkillService, TeacherProfileRepository teacherProfileRepository, List<String> defaultSkills) {
        this.memberService = memberService;
        this.languageService = languageService;
        this.skillService = skillService;
        this.teacherLanguageService = teacherLanguageService;
        this.teacherSkillService = teacherSkillService;
        this.teacherProfileRepository = teacherProfileRepository;
        this.defaultSkills = defaultSkills;
    }

    public TeacherProfile save(TeacherProfile teacherProfile) {
        return teacherProfileRepository.save(teacherProfile);
    }

    @Transactional
    public void registerTeacher(LoginMember loginMember, TeacherRegistrationRequest teacherRegistrationRequest) {
        Member member = memberService.findById(loginMember.getId())
                                     .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        if (member.hasRole(Role.TEACHER)) {
            throw new IllegalArgumentException("이미 리뷰어로 등록된 사용자입니다.");
        }

        Map<String, Language> languageMap = languageService.findAllToMap();
        List<Language> languages = validateLanguageNamesExists(teacherRegistrationRequest, languageMap);
        List<Skill> skills = validateSkillNamesExists(teacherRegistrationRequest, skillService.findAllToMap());
        teacherRegistrationRequest.validateSkillsInLanguage(languageMap);

        TeacherProfile teacher = save(teacherRegistrationRequest.toTeacherProfileWithMember(member));
        teacherLanguageService.saveAllWithTeacher(languages, teacher);
        teacherSkillService.saveAllWithTeacher(skills, teacher);
        member.setRole(Role.TEACHER);
        memberService.save(member);
    }

    private List<Language> validateLanguageNamesExists(TeacherRegistrationRequest teacherRegistrationRequest, Map<String, Language> languageMap) {
        return teacherRegistrationRequest.getTechSpecs()
                                         .stream()
                                         .map(TechSpec::getLanguage)
                                         .map(languageName -> Optional.ofNullable(languageMap.get(languageName)))
                                         .map(languageOptional -> languageOptional.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 언어입니다.")))
                                         .collect(Collectors.toList());
    }

    private List<Skill> validateSkillNamesExists(TeacherRegistrationRequest teacherRegistrationRequest, Map<String, Skill> skillMap) {
        return teacherRegistrationRequest.getTechSpecs()
                                         .stream()
                                         .map(TechSpec::getSkills)
                                         .flatMap(Collection::stream)
                                         .map(skillName -> Optional.ofNullable(skillMap.get(skillName)))
                                         .map(skill -> skill.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기술입니다.")))
                                         .collect(Collectors.toList());
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
