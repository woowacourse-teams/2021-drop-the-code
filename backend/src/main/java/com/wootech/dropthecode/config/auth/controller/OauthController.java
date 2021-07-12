package com.wootech.dropthecode.config.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wootech.dropthecode.config.auth.dto.AuthorizationCode;
import com.wootech.dropthecode.config.auth.dto.LoginResponse;
import com.wootech.dropthecode.config.auth.dto.TokenRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class OauthController {

    @Value("${oauth2.github.client_id}")
    private String clientId;

    @Value("${oauth2.github.client_secret}")
    private String clientSecret;

    private final ObjectMapper objectMapper;

    public OauthController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/login/oauth")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthorizationCode authorizationCode) throws JsonProcessingException {
        // TODO: client_id, client_secret, authorizationCode를 이용해서 토큰 요청하기
        TokenRequest tokenRequest = new TokenRequest(clientId, clientSecret, authorizationCode.getAuthorizationCode());

        WebClient client = WebClient.builder()
                                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                    .build();

        String jsonString = objectMapper.writeValueAsString(tokenRequest);

        String accessToken = client.post()
                                   .uri("https://github.com/login/oauth/access_token")
                                   .accept(MediaType.APPLICATION_JSON)
                                   .body(BodyInserters.fromValue(jsonString))
                                   .retrieve()
                                   .bodyToMono(String.class).block();

        // TODO: access token으로 name, email, avatar_url 요청

        // TODO: 받아온 정보로 회원가입 or 로그인 -> JWT 토큰 생성

        // TODO: 유저 정보 & JWT 토큰 set 응답
        LoginResponse loginResponse = new LoginResponse();

        return ResponseEntity.ok().body(loginResponse);
    }
}
