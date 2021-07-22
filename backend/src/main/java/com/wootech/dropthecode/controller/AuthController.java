package com.wootech.dropthecode.controller;

import javax.servlet.http.HttpServletRequest;

import com.wootech.dropthecode.controller.auth.util.AuthorizationExtractor;
import com.wootech.dropthecode.dto.request.AuthorizationRequest;
import com.wootech.dropthecode.dto.request.RefreshTokenRequest;
import com.wootech.dropthecode.dto.response.AccessTokenResponse;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.service.AuthService;
import com.wootech.dropthecode.service.OauthService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final OauthService oauthService;
    private final AuthService authService;

    public AuthController(OauthService oauthService, AuthService authService) {
        this.oauthService = oauthService;
        this.authService = authService;
    }

    /**
     * @title 로그인
     */
    @GetMapping("/login/oauth")
    public ResponseEntity<LoginResponse> login(@ModelAttribute AuthorizationRequest authorizationRequest) {
        LoginResponse loginResponse = oauthService.login(authorizationRequest);
        return ResponseEntity.ok().body(loginResponse);
    }

    /**
     * @title access token 갱신
     */
    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<AccessTokenResponse> refreshAccessToken(HttpServletRequest request,
                                                                  @ModelAttribute RefreshTokenRequest refreshToken) {
        String accessToken = AuthorizationExtractor.extract(request);
        return ResponseEntity.ok().body(authService.refreshAccessToken(accessToken, refreshToken));
    }

    /**
     * @title 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        authService.logout(accessToken);
        return ResponseEntity.noContent().build();
    }
}
