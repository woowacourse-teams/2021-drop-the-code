package com.wootech.dropthecode.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.wootech.dropthecode.dto.TechSpec;
import com.wootech.dropthecode.dto.request.TeacherFilterRequest;
import com.wootech.dropthecode.dto.request.TeacherRegistrationRequest;
import com.wootech.dropthecode.dto.response.TeacherPaginationResponse;
import com.wootech.dropthecode.service.TeacherService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private final TeacherService teacherService;

    public MemberController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

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
     * @title 리뷰어 목록 조회
     * @see <a href="https://www.dropthecode.p-e.kr/docs/api.html/#paging">페이지네이션 문서</a>
     */
    @GetMapping("/teachers")
    public ResponseEntity<TeacherPaginationResponse> findAllTeacher(@ModelAttribute("filter") TeacherFilterRequest teacherFilterRequest, @PageableDefault Pageable pageable) {
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
