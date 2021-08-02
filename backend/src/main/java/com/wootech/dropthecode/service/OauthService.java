package com.wootech.dropthecode.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import com.wootech.dropthecode.controller.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.controller.auth.util.RedisUtil;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.oauth.InMemoryProviderRepository;
import com.wootech.dropthecode.domain.oauth.OauthAttributes;
import com.wootech.dropthecode.domain.oauth.OauthProvider;
import com.wootech.dropthecode.domain.oauth.UserProfile;
import com.wootech.dropthecode.dto.request.AuthorizationRequest;
import com.wootech.dropthecode.dto.response.LoginResponse;
import com.wootech.dropthecode.dto.response.OauthTokenResponse;
import com.wootech.dropthecode.exception.OauthException;
import com.wootech.dropthecode.repository.MemberRepository;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OauthService {
    private final MemberRepository memberRepository;
    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    public OauthService(MemberRepository memberRepository, InMemoryProviderRepository inMemoryProviderRepository,
                        JwtTokenProvider jwtTokenProvider, RedisUtil redisUtil) {
        this.memberRepository = memberRepository;
        this.inMemoryProviderRepository = inMemoryProviderRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisUtil = redisUtil;
    }

    @Transactional
    public LoginResponse login(AuthorizationRequest authorizationRequest) {
        OauthProvider oauthProvider = inMemoryProviderRepository.findByProviderName(authorizationRequest.getProviderName());
        UserProfile userProfile = getUserProfile(authorizationRequest, oauthProvider);

        Member member = memberRepository.findByOauthId(userProfile.getOauthId())
                                        .orElseGet(() -> memberRepository.save(userProfile.toMember()));

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        redisUtil.setData(String.valueOf(member.getId()), refreshToken);

        return new LoginResponse(member.getId(), member.getName(), member.getEmail(), member.getImageUrl(), member.getRole(), "Bearer", accessToken, refreshToken);
    }

    private UserProfile getUserProfile(AuthorizationRequest authorizationRequest, OauthProvider oauthProvider) {
        OauthTokenResponse oauthTokenResponse = getToken(authorizationRequest, oauthProvider);
        Map<String, Object> userAttributes = getUserAttributes(oauthProvider, oauthTokenResponse);
        return OauthAttributes.extract(authorizationRequest.getProviderName(), userAttributes);
    }

    private OauthTokenResponse getToken(AuthorizationRequest authorizationRequest, OauthProvider oauthProvider) {
        OauthTokenResponse oauthTokenResponse = WebClient.create()
                                            .post()
                                            .uri(oauthProvider.getTokenUrl())
                                            .headers(header -> {
                                                header.setBasicAuth(oauthProvider.getClientId(), oauthProvider
                                                        .getClientSecret());
                                                header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                                                header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                                                header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                                            })
                                            .bodyValue(tokenRequest(authorizationRequest, oauthProvider))
                                            .retrieve()
                                            .bodyToMono(OauthTokenResponse.class)
                                            .block();
        validateOauthTokenResponse(oauthTokenResponse);
        return oauthTokenResponse;
    }

    private MultiValueMap<String, String> tokenRequest(AuthorizationRequest authorizationRequest, OauthProvider oauthProvider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", authorizationRequest.getCode());
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", oauthProvider.getRedirectUrl());
        return formData;
    }

    private Map<String, Object> getUserAttributes(OauthProvider oauthProvider, OauthTokenResponse accessToken) {
        return WebClient.create()
                        .get()
                        .uri(oauthProvider.getUserInfoUrl())
                        .headers(header -> header.setBearerAuth(accessToken.getAccessToken()))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                            throw new OauthException("유효하지 않은 Oauth 토큰입니다.");
                        })
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                        .block();
    }

    private void validateOauthTokenResponse(OauthTokenResponse oauthTokenResponse) {
        if (oauthTokenResponse.isNotValid()) {
            throw new OauthException("토큰 요청에 유효하지 않은 정보가 포함되어 있습니다.");
        }
    }
}
