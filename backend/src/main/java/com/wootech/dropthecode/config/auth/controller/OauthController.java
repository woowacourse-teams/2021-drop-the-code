package com.wootech.dropthecode.config.auth.controller;

import com.wootech.dropthecode.config.auth.domain.OauthProvider;
import com.wootech.dropthecode.config.auth.dto.request.AuthorizationRequest;
import com.wootech.dropthecode.config.auth.dto.request.TokenRequest;
import com.wootech.dropthecode.config.auth.dto.response.LoginResponse;
import com.wootech.dropthecode.config.auth.dto.response.MemberProfileResponse;
import com.wootech.dropthecode.config.auth.dto.response.TokenResponse;
import com.wootech.dropthecode.config.auth.service.OauthService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class OauthController {
    private final OauthService oauthService;

    public OauthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @PostMapping("/login/oauth")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthorizationRequest authorizationRequest) {
        // TODO: client_id, client_secret, authorizationCode를 이용해서 토큰 요청하기
        OauthProvider oauthProvider = oauthService.findOauthProvider(authorizationRequest.getProviderName());

        TokenRequest tokenRequest = new TokenRequest(oauthProvider.getClientId(), oauthProvider.getClientSecret(),
                authorizationRequest.getAuthorizationCode());

        TokenResponse accessToken = WebClient.create()
                                             .post()
                                             .uri(oauthProvider.getTokenUrl())
                                             .contentType(MediaType.APPLICATION_JSON)
                                             .accept(MediaType.APPLICATION_JSON)
                                             .body(Mono.just(tokenRequest), TokenRequest.class)
                                             .retrieve()
                                             .bodyToMono(TokenResponse.class)
                                             .block();

        // TODO: access token으로 id, name, email, avatar_url 요청
        MemberProfileResponse profileResponse = WebClient.create()
                                                         .get()
                                                         .uri(oauthProvider.getUserInfoUrl())
                                                         .header("Authorization", accessToken.getTokenWithType())
                                                         .retrieve()
                                                         .bodyToMono(MemberProfileResponse.class)
                                                         .block();

        // TODO: 받아온 정보로 회원가입 or 로그인 -> JWT 토큰 생성
        LoginResponse loginResponse = oauthService.login(profileResponse);

        return ResponseEntity.ok().body(loginResponse);
    }
}
