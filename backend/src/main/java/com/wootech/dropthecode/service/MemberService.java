package com.wootech.dropthecode.service;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.dto.response.MemberResponse;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public MemberResponse findByLoginMember(LoginMember loginMember) {
        validatesMember(loginMember);
        Member member = findById(loginMember.getId());
        return MemberResponse.of(member);
    }

    private void validatesMember(LoginMember loginMember) {
        if (loginMember.isAnonymous()) {
            throw new AuthorizationException("유효하지 않은 유저입니다.");
        }
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                               .orElseThrow(() -> new AuthorizationException("유효하지 않은 유저입니다."));
    }
}
