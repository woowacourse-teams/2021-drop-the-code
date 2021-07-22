package com.wootech.dropthecode.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import com.wootech.dropthecode.domain.*;
import com.wootech.dropthecode.domain.bridge.TeacherLanguage;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;
import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherFilterRequest;
import com.wootech.dropthecode.dto.response.TeacherPaginationResponse;
import com.wootech.dropthecode.dto.response.TeacherProfileResponse;
import com.wootech.dropthecode.repository.TeacherFilterRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherFilterRepository teacherFilterRepository;

    @InjectMocks
    private TeacherService teacherService;

    private TeacherProfile airProfile;

    private TeacherProfile seedProfile;

    @BeforeEach
    void setUp() {
        airProfile = new TeacherProfile(
                "배민 개발자",
                "열심히 가르쳐드리겠습니다.",
                3,
                null,
                null,
                null
        );

        seedProfile = new TeacherProfile(
                "토스 개발자",
                "잘 가르치겠습니다.",
                5,
                null,
                null,
                null
        );

        final Member air = new Member(
                1L,
                "oauthId",
                "air",
                "air.junseo@gmail.com",
                "s3://image",
                Role.STUDENT,
                airProfile
        );


        final Member seed = new Member(
                1L,
                "oauthId",
                "seed",
                "seed.junseo@gmail.com",
                "s3://image",
                Role.STUDENT,
                seedProfile
        );

        final Language java = new Language("java");
        final Language javascript = new Language("javascript");

        final TeacherLanguage airLanguage = new TeacherLanguage(airProfile, java);
        final TeacherLanguage seedLanguage = new TeacherLanguage(seedProfile, javascript);

        final Skill spring = new Skill("spring");
        final Skill react = new Skill("react");

        final TeacherSkill airSkill = new TeacherSkill(airProfile, spring);
        final TeacherSkill seedSkill = new TeacherSkill(seedProfile, react);

        airProfile.setLanguages(new HashSet<>(Collections.singletonList(airLanguage)));
        airProfile.setSkills(new HashSet<>(Collections.singletonList(airSkill)));
        airProfile.setMember(air);

        seedProfile.setLanguages(new HashSet<>(Collections.singletonList(seedLanguage)));
        seedProfile.setSkills(new HashSet<>(Collections.singletonList(seedSkill)));
        seedProfile.setMember(seed);
    }

    @Test
    @DisplayName("기술, 언어, 연차 필터에 일치하는 리뷰어가 1명일 경우 목록 반환")
    void findOneTeacher() {

        // given
        TeacherFilterRequest request =
                new TeacherFilterRequest(new TechSpec("java", Collections.singletonList("sprint")), 3);

        final PageRequest pageable = PageRequest.of(0, 10);
        Page<TeacherProfile> response = new PageImpl<>(Collections.singletonList(airProfile), pageable, 1);

        given(teacherFilterRepository.findAll(
                request.getTechSpec().getLanguage(),
                request.getTechSpec().getSkills(),
                3,
                pageable)
        ).willReturn(response);

        // when
        final TeacherPaginationResponse teacherPaginationResponse = teacherService.findAll(request, pageable);

        // then
        TeacherPaginationResponse expectedResponse =
                new TeacherPaginationResponse(Collections.singletonList(TeacherProfileResponse.from(airProfile)), 1);

        assertThat(teacherPaginationResponse).usingRecursiveComparison()
                                             .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("기술, 언어, 연차 필터에 일치하는 리뷰어가 여러 명일 경우 목록 반환")
    void findAllTeacher() {

        // given
        seedProfile.setLanguages(airProfile.getLanguages());

        TeacherFilterRequest request =
                new TeacherFilterRequest(new TechSpec("java", new ArrayList<>()), 3);

        final PageRequest pageable = PageRequest.of(0, 1);
        Page<TeacherProfile> response = new PageImpl<>(Arrays.asList(airProfile, seedProfile), pageable, 2);

        given(teacherFilterRepository.findAll(
                request.getTechSpec().getLanguage(),
                request.getTechSpec().getSkills(),
                3,
                pageable)
        ).willReturn(response);

        // when
        final TeacherPaginationResponse teacherPaginationResponse = teacherService.findAll(request, pageable);

        // then
        TeacherPaginationResponse expectedResponse =
                new TeacherPaginationResponse(
                        Arrays.asList(
                                TeacherProfileResponse.from(airProfile),
                                TeacherProfileResponse.from(seedProfile)
                        ),
                        2
                );

        assertThat(teacherPaginationResponse).usingRecursiveComparison()
                                             .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("기술, 언어, 연차 필터에 일치하는 리뷰어가 존재하지 않을 경우 빈 목록 반환")
    void findNonTeacher() {

        // given
        TeacherFilterRequest request =
                new TeacherFilterRequest(new TechSpec("python", new ArrayList<>()), 3);

        final PageRequest pageable = PageRequest.of(0, 10);
        Page<TeacherProfile> response = new PageImpl<>(new ArrayList<>(), pageable, 1);

        given(teacherFilterRepository.findAll(
                request.getTechSpec().getLanguage(),
                request.getTechSpec().getSkills(),
                3,
                pageable)
        ).willReturn(response);

        // when
        final TeacherPaginationResponse teacherPaginationResponse = teacherService.findAll(request, pageable);

        // then
        TeacherPaginationResponse expectedResponse =
                new TeacherPaginationResponse(new ArrayList<>(), 1);

        assertThat(teacherPaginationResponse).usingRecursiveComparison()
                                             .isEqualTo(expectedResponse);
    }
}
