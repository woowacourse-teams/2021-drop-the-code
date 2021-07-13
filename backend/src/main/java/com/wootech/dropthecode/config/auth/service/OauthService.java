package com.wootech.dropthecode.config.auth.service;

import java.util.Optional;

import com.wootech.dropthecode.config.auth.JwtTokenProvider;
import com.wootech.dropthecode.config.auth.dto.LoginResponse;
import com.wootech.dropthecode.config.auth.dto.MemberProfileResponse;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.repository.MemberRepository;

import org.springframework.stereotype.Service;

@Service
public class OauthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public OauthService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(MemberProfileResponse profileResponse) {
        // TODO: DB에 존재하는 회원이면 조회, 아니면 insert
        Member member = memberRepository.findByOauthId(profileResponse.getId())
                                        .orElse(memberRepository.save(profileResponse.toMember()));

        // TODO: 회원 정보로 토큰(access, refresh) 생성
        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(member.getId()));

        return new LoginResponse(member.getName(), member.getEmail(), member.getImageUrl(), accessToken, refreshToken);
    }
}
