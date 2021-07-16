package com.wootech.dropthecode.config.auth.service;

import com.wootech.dropthecode.config.auth.domain.InMemoryProviderRepository;
import com.wootech.dropthecode.config.auth.domain.OauthProvider;
import com.wootech.dropthecode.config.auth.domain.UserProfile;
import com.wootech.dropthecode.config.auth.dto.response.LoginResponse;
import com.wootech.dropthecode.config.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.config.auth.util.RedisUtil;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.repository.MemberRepository;

import org.springframework.stereotype.Service;

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

    public LoginResponse login(UserProfile userProfile) {
        Member member = memberRepository.findByOauthId(userProfile.getId())
                                        .orElse(memberRepository.save(userProfile.toMember()));

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        redisUtil.setData(String.valueOf(member.getId()), refreshToken);

        return new LoginResponse(member.getName(), member.getEmail(), member.getImageUrl(), accessToken, refreshToken);
    }

    public OauthProvider findOauthProvider(String providerName) {
        return inMemoryProviderRepository.findByProviderName(providerName);
    }
}
