package com.wootech.dropthecode.controller;

import javax.validation.Valid;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.oauth.Login;
import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherFilterRequest;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.dto.response.MemberResponse;
import com.wootech.dropthecode.dto.response.TeacherPaginationResponse;
import com.wootech.dropthecode.dto.response.TeacherProfileResponse;
import com.wootech.dropthecode.service.MemberService;
import com.wootech.dropthecode.service.TeacherService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    private final MemberService memberService;
    private final TeacherService teacherService;

    public MemberController(MemberService memberService, TeacherService teacherService) {
        this.memberService = memberService;
        this.teacherService = teacherService;
    }

    /**
     * @title 로그인 한 유저 정보 조회
     */
    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> loginMemberInformation(@Login LoginMember loginMember) {
        MemberResponse memberResponse = memberService.findByLoginMember(loginMember);
        return ResponseEntity.ok().body(memberResponse);
    }

    /**
     * @title 유저 삭제
     */
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        // todo 유저 삭제는 관리자나 본인만 할 수 있도록 허용하는 기능 추가
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * @title 리뷰어 등록
     */
    @PostMapping(value = "/teachers")
    public ResponseEntity<Void> registerTeacher(@Login LoginMember loginMember, @Valid @RequestBody TeacherRegistrationRequest teacherRegistrationRequest) {
        teacherService.registerTeacher(loginMember, teacherRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @title 리뷰어 수정
     */
    @PutMapping(value = "/teachers")
    public ResponseEntity<Void> updateTeacher(@Login LoginMember loginMember, @Valid @RequestBody TeacherRegistrationRequest teacherRegistrationRequest) {
        teacherService.updateTeacher(loginMember, teacherRegistrationRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @title 리뷰어 목록 조회
     */
    @GetMapping("/teachers")
    public ResponseEntity<TeacherPaginationResponse> findAllTeacher(@ModelAttribute("filter") @Valid TeacherFilterRequest teacherFilterRequest, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(teacherService.findAll(teacherFilterRequest, pageable));
    }

    @ModelAttribute("filter")
    public TeacherFilterRequest filter(@ModelAttribute TechSpec techSpec, @RequestParam(defaultValue = "0") Integer career) {
        TeacherFilterRequest teacherFilterRequest = new TeacherFilterRequest();
        teacherFilterRequest.setTechSpec(techSpec);
        teacherFilterRequest.setCareer(career);
        return teacherFilterRequest;
    }

    /**
     * @title 리뷰어 단일 조회
     * @param id 리뷰어의 ID
     */
    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeacherProfileResponse> findTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.findTeacherResponseById(id));
    }
}
