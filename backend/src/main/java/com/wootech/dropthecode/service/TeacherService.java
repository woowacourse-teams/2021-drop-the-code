package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.dto.request.TeacherFilterRequest;
import com.wootech.dropthecode.dto.response.TeacherPaginationResponse;
import com.wootech.dropthecode.dto.response.TeacherProfileResponse;
import com.wootech.dropthecode.repository.TeacherProfileRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private final TeacherProfileRepository teacherProfileRepository;
    private final List<String> defaultSkills;

    public TeacherService(TeacherProfileRepository teacherProfileRepository, List<String> defaultSkills) {
        this.teacherProfileRepository = teacherProfileRepository;
        this.defaultSkills = defaultSkills;
    }

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
