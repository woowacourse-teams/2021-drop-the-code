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

    public TeacherService(TeacherProfileRepository teacherProfileRepository) {
        this.teacherProfileRepository = teacherProfileRepository;
    }

    public TeacherPaginationResponse findAll(TeacherFilterRequest teacherFilterRequest, Pageable pageable) {
        Page<TeacherProfile> teacherProfilePage;
        if (teacherFilterRequest.getTechSpec().getSkills().isEmpty()) {
            teacherProfilePage = teacherProfileRepository.findAllByLanguagesLanguageNameAndCareerGreaterThanEqual(pageable, teacherFilterRequest
                    .getTechSpec()
                    .getLanguage(), teacherFilterRequest.getCareer());
        } else {
            teacherProfilePage = teacherProfileRepository.findAllByLanguagesLanguageNameAndSkillsSkillNameInAndCareerGreaterThanEqual(pageable, teacherFilterRequest
                            .getTechSpec()
                            .getLanguage(),
                    teacherFilterRequest.getTechSpec().getSkills(), teacherFilterRequest.getCareer());
        }

        final List<TeacherProfileResponse> teacherProfiles = teacherProfilePage.stream()
                                                                               .map(TeacherProfileResponse::from)
                                                                               .collect(Collectors.toList());
        return new TeacherPaginationResponse(teacherProfiles, teacherProfilePage.getTotalPages());
    }
}
