package com.wootech.dropthecode.config.auth.controller;

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
    public ResponseEntity<LoginResponse> login(@ModelAttribute AuthorizationRequest authorizationRequest) {
        LoginResponse loginResponse = oauthService.login(authorizationRequest);
        return ResponseEntity.ok().body(loginResponse);
    }
}
