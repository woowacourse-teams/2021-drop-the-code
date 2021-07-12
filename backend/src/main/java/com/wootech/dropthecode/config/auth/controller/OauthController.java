package com.wootech.dropthecode.config.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @PostMapping("/login/oauth")
    public ResponseEntity<LoginResponse> login(@RequestBody String authorizationCode) throws JsonProcessingException {
        // TODO: client_id, client_secret, authorizationCode를 이용해서 토큰 요청하기

        System.out.println("== authorization code");
        System.out.println(authorizationCode);

        TokenRequest tokenRequest = new TokenRequest("2df558c8c24539ce442e", "d0604607fe6f824754442fffbc8a2ab932ebd964", authorizationCode);

        WebClient client = WebClient.builder()
                                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                    .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(tokenRequest);
        String token2 = client.post()
                              .uri("https://github.com/login/oauth/access_token")
                              .accept(MediaType.APPLICATION_JSON)
                              .body(BodyInserters.fromValue(jsonString))
                              .retrieve()
                              .bodyToMono(String.class).block();

        System.out.println(token2);

        // TODO: access token으로 name, email, avatar_url 요청

        // TODO: 받아온 정보로 회원가입 or 로그인 -> JWT 토큰 생성

        // TODO: 유저 정보 & JWT 토큰 set 응답
        LoginResponse loginResponse = new LoginResponse();

        return ResponseEntity.ok().body(loginResponse);
    }
}
