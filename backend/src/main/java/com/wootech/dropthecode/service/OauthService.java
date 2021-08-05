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
import com.wootech.dropthecode.repository.MemberRepository;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OauthService {
    private static final String BEARER_TYPE = "Bearer";

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

        Member member = saveOrUpdate(userProfile);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        redisUtil.setData(String.valueOf(member.getId()), refreshToken);

        return LoginResponse.builder()
                            .id(member.getId())
                            .name(member.getName())
                            .email(member.getEmail())
                            .imageUrl(member.getImageUrl())
                            .githubUrl(member.getGithubUrl())
                            .role(member.getRole())
                            .tokenType(BEARER_TYPE)
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
    }

    private Member saveOrUpdate(UserProfile userProfile) {
        Member member = memberRepository.findByOauthId(userProfile.getOauthId())
                                        .map(entity -> entity.update(
                                                userProfile.getEmail(), userProfile.getName(), userProfile.getImageUrl()))
                                        .orElseGet(userProfile::toMember);
        return memberRepository.save(member);
    }

    private UserProfile getUserProfile(AuthorizationRequest authorizationRequest, OauthProvider oauthProvider) {
        OauthTokenResponse oauthTokenResponse = getToken(authorizationRequest, oauthProvider);
        Map<String, Object> userAttributes = getUserAttributes(oauthProvider, oauthTokenResponse);
        return OauthAttributes.extract(authorizationRequest.getProviderName(), userAttributes);
    }

    private OauthTokenResponse getToken(AuthorizationRequest authorizationRequest, OauthProvider oauthProvider) {
        return WebClient.create()
                        .post()
                        .uri(oauthProvider.getTokenUrl())
                        .headers(header -> {
                            header.setBasicAuth(oauthProvider.getClientId(), oauthProvider.getClientSecret());
                            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                            header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                            header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                        })
                        .bodyValue(tokenRequest(authorizationRequest, oauthProvider))
                        .retrieve()
                        .bodyToMono(OauthTokenResponse.class)
                        .block();
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
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                        .block();
    }
}
