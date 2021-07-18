package com.wootech.dropthecode.config.auth.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.wootech.dropthecode.config.auth.dto.request.AuthorizationRequest;
import com.wootech.dropthecode.config.auth.dto.response.LoginResponse;
import com.wootech.dropthecode.config.auth.service.OauthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {
    private final OauthService oauthService;

    public OauthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    /**
     * @title 로그인
     */
    @GetMapping("/login/oauth")
    public ResponseEntity<LoginResponse> login(@ModelAttribute AuthorizationRequest authorizationRequest,
                                               HttpServletResponse response) {
        LoginResponse loginResponse = oauthService.login(authorizationRequest);
        response.addCookie(createCookie("refreshToken", loginResponse.getRefreshToken()));
        return ResponseEntity.ok().body(loginResponse);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }
}
