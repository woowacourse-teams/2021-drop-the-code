package com.wootech.dropthecode.config.auth.service;

import com.wootech.dropthecode.config.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberRepository memberRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
    }

    public void validatesAccessToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthorizationException("access token이 유효하지 않습니다.");
        }
    }

    @Transactional(readOnly = true)
    public LoginMember findMemberByToken(String accessToken) {
        Long id = Long.parseLong(jwtTokenProvider.getPayload(accessToken));

        Member member = memberRepository.findById(id)
                                        .orElseThrow(() -> new AuthorizationException("인증되지 않는 유저입니다."));

        return new LoginMember(member.getId());
    }
}
