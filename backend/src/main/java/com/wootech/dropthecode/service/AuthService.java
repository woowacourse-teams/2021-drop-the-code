package com.wootech.dropthecode.service;

import com.wootech.dropthecode.controller.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.controller.auth.util.RedisUtil;
import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.dto.request.RefreshTokenRequest;
import com.wootech.dropthecode.dto.response.AccessTokenResponse;
import com.wootech.dropthecode.exception.AuthenticationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final RedisUtil redisUtil;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService, RedisUtil redisUtil) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.redisUtil = redisUtil;
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

        String newAccessToken = jwtTokenProvider.createAccessToken(id);

        return new AccessTokenResponse(newAccessToken);
    }

    @Transactional
    public void logout(String accessToken) {
        String id = jwtTokenProvider.getPayload(accessToken);
        redisUtil.deleteData(id);
    }
}
