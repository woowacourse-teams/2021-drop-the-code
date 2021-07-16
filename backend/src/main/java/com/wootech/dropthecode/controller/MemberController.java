package com.wootech.dropthecode.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    /**
     * @title 리뷰어 등록
     */
    @PostMapping(value = "/teachers")
    public ResponseEntity<Void> registerTeacher(@Valid @RequestBody TeacherRegistrationRequest teacherRegistrationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @param skills 선생님 기술 스택
     * @param career 선생님 경력 연차
     * @param limit  한 페이지에 보여줄 선생님 수
     * @param page   현재 페이지 번호
     * @title 리뷰어 목록 조회
     */
    @GetMapping("/teachers")
    public ResponseEntity<Void> findAllTeacher(
            @RequestParam(required = false) List<String> skills,
            @RequestParam(required = false) Integer career,
            @RequestParam Integer limit,
            @RequestParam int page) {
        return ResponseEntity.ok().build();
    }

    /**
     * @title 언어 및 기술 목록 조히
     */
    @GetMapping("/skills")
    public ResponseEntity<List<TechSpec>> skills() {
        List<TechSpec> techSpecs = new ArrayList<>();
        techSpecs.add(new TechSpec("java", Arrays.asList("Spring", "Servlet")));
        techSpecs.add(new TechSpec("javaScript", Arrays.asList("Vue", "Angular")));
        techSpecs.add(new TechSpec("C", Arrays.asList("OpenGL", "Unity")));
        return ResponseEntity.ok().body(techSpecs);
    }
}
