package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.dto.request.TeacherFilterRequest;
import com.wootech.dropthecode.dto.response.TeacherPaginationResponse;
import com.wootech.dropthecode.dto.response.TeacherProfileResponse;
import com.wootech.dropthecode.repository.TeacherFilterRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private final TeacherFilterRepository teacherFilterRepository;

    public TeacherService(TeacherFilterRepository teacherFilterRepository) {
        this.teacherFilterRepository = teacherFilterRepository;
    }

    public TeacherPaginationResponse findAll(TeacherFilterRequest teacherFilterRequest, Pageable pageable) {
        Page<TeacherProfile> teacherProfilePage = teacherFilterRepository.findAll(
                teacherFilterRequest.getTechSpec().getLanguage(),
                teacherFilterRequest.getTechSpec().getSkills(),
                teacherFilterRequest.getCareer(),
                pageable
        );

        final List<TeacherProfileResponse> teacherProfiles = teacherProfilePage.stream()
                                                                               .map(TeacherProfileResponse::from)
                                                                               .collect(Collectors.toList());

        return new TeacherPaginationResponse(teacherProfiles, teacherProfilePage.getTotalPages());
    }
}

