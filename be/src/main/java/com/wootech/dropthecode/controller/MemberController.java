package com.wootech.dropthecode.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/login/oauth")
    public ResponseEntity<Void> oauth(@RequestParam String code, @RequestParam String redirectUrl, HttpServletResponse response) throws IOException {
        List<ResponseCookie> cookies = Arrays.asList(
                ResponseCookie.from("jwt", "aaa.bbb.ccc").build(),
                ResponseCookie.from("name", "fafi").build(),
                ResponseCookie.from("email", "test@email.com").build(),
                ResponseCookie.from("imageUrl", "s3://fafi.jpg").build()
        );

        HttpHeaders headers = new HttpHeaders();
        cookies.forEach(cookie -> headers.add(HttpHeaders.SET_COOKIE, cookie.toString()));

        response.sendRedirect(redirectUrl);

        return ResponseEntity.status(302).headers(headers).build();
    }
}
