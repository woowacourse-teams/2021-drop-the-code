package com.wootech.dropthecode.config.auth.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wootech.dropthecode.config.auth.dto.request.AuthorizationRequest;
import com.wootech.dropthecode.config.auth.dto.response.LoginResponse;
import com.wootech.dropthecode.config.auth.service.OauthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import static com.wootech.dropthecode.config.auth.util.UrlUtil.buildFullRequestUrl;

@RestController
public class OauthController {
    private final OauthService oauthService;

    public OauthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    /**
     * @title 로그인
     */
    @PostMapping("/login/oauth")
    public ResponseEntity<LoginResponse> login(HttpServletRequest request, HttpServletResponse response,
                                               @RequestBody AuthorizationRequest authorizationRequest) {
        LoginResponse loginResponse = oauthService.login(createRedirectUri(request), authorizationRequest);
        response.addCookie(createCookie("refreshToken", loginResponse.getRefreshToken()));
        return ResponseEntity.ok().body(loginResponse);
    }

    private String createRedirectUri(HttpServletRequest request) {
        return UriComponentsBuilder.fromHttpUrl(
                buildFullRequestUrl(
                        request.getScheme(),
                        request.getServerName(),
                        request.getServerPort(),
                        request.getRequestURI(),
                        request.getQueryString()
                )).replaceQuery(null).build().toUriString();
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }
}
