package com.wootech.dropthecode.service;

import java.util.Optional;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.repository.MemberRepository;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public void save(Member member) {
        memberRepository.save(member);
    }
}
