package com.wootech.dropthecode.config.auth.service;

import com.wootech.dropthecode.config.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.service.MemberService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    public void validatesAccessToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthorizationException("access token이 유효하지 않습니다.");
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
}
