package com.wootech.dropthecode.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    /**
     * OAuth 인증 코드를 이용하여 인증 토큰을 반환한다.
     * <p>
     * 인증 토큰 반환에 성공하면 지정한 redirectUrl 로 리다이렉트 시킨다.
     *
     * @param code        Authorization code
     * @param redirectUrl Redirect URL
     * @title AccessToken 가져오기
     */
    @GetMapping("/login/oauth")
    public ResponseEntity<Void> oauth(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "redirectUrl") String redirectUrl,
            HttpServletResponse response) throws IOException {

        List<ResponseCookie> cookies = Arrays.asList(
                ResponseCookie.from("jwt", "aaa.bbb.ccc").build(),
                ResponseCookie.from("name", "fafi").build(),
                ResponseCookie.from("email", "test@email.com").build(),
                ResponseCookie.from("imageUrl", "s3://fafi.jpg").build()
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        cookies.forEach(cookie -> httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString()));

        response.sendRedirect(redirectUrl);

        return ResponseEntity.status(302).headers(httpHeaders).build();
    }

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
        techSpecs.add(new TechSpec("java", new String[]{"Spring", "Servlet"}));
        techSpecs.add(new TechSpec("javaScript", new String[]{"Vue", "Angular"}));
        techSpecs.add(new TechSpec("C", new String[]{"OpenGL", "Unity"}));
        return ResponseEntity.ok().body(techSpecs);
    }

    static class TechSpec {
        private final String language;
        private final String[] skills;

        public TechSpec(String language, String[] skills) {
            this.language = language;
            this.skills = skills;
        }

        public String getLanguage() {
            return language;
        }

        public String[] getSkills() {
            return skills;
        }
    }
}
