package com.wootech.dropthecode.service;

import com.wootech.dropthecode.controller.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.controller.auth.util.RedisUtil;
import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Token;
import com.wootech.dropthecode.dto.request.RefreshTokenRequest;
import com.wootech.dropthecode.dto.response.AccessTokenResponse;
import com.wootech.dropthecode.exception.AuthenticationException;
import com.wootech.dropthecode.repository.EmitterRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final RedisUtil redisUtil;
    private final EmitterRepository emitterRepository;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService, RedisUtil redisUtil, EmitterRepository emitterRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.redisUtil = redisUtil;
        this.emitterRepository = emitterRepository;
    }

    public void validatesAccessToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthenticationException("access token이 유효하지 않습니다.");
        }
    }

    @Transactional(readOnly = true)
    public LoginMember findMemberByToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            return LoginMember.anonymous();
        }

        Long id = Long.parseLong(jwtTokenProvider.getPayload(accessToken));
        Member member = memberService.findById(id);
        return new LoginMember(member.getId());
    }

    @Transactional
    public AccessTokenResponse refreshAccessToken(String accessToken, RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AuthenticationException("refresh token이 유효하지 않습니다.");
        }

        String id = jwtTokenProvider.getPayload(accessToken);
        String existingRefreshToken = redisUtil.getData(id);

        if (!existingRefreshToken.equals(refreshToken)) {
            throw new AuthenticationException("refresh token이 유효하지 않습니다.");
        }

        Token newAccessToken = jwtTokenProvider.createAccessToken(id);

        return new AccessTokenResponse(newAccessToken.getValue());
    }

    @Transactional
    public void logout(String accessToken) {
        String id = jwtTokenProvider.getPayload(accessToken);
        redisUtil.deleteData(id);
        emitterRepository.deleteAllStartWithId(id);
        emitterRepository.deleteAllEventCacheStartWithId(id);
    }

    @Transactional
    public AccessTokenResponse createChattingToken(String accessToken) {
        String id = jwtTokenProvider.getPayload(accessToken);
        String token = jwtTokenProvider.createToken(id, 30000);
        return new AccessTokenResponse(token);
    }
}
