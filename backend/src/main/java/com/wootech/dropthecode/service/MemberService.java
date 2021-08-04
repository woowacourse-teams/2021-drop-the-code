package com.wootech.dropthecode.service;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.dto.response.MemberResponse;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.repository.MemberRepository;
import com.wootech.dropthecode.repository.bridge.TeacherSkillRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final TeacherLanguageService teacherLanguageService;
    private final TeacherSkillService teacherSkillService;
    private final MemberRepository memberRepository;

    public MemberService(TeacherLanguageService teacherLanguageService, TeacherSkillService teacherSkillService, MemberRepository memberRepository) {
        this.teacherLanguageService = teacherLanguageService;
        this.teacherSkillService = teacherSkillService;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public MemberResponse findByLoginMember(LoginMember loginMember) {
        loginMember.validatesAnonymous();
        Member member = findById(loginMember.getId());
        return MemberResponse.of(member);
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                               .orElseThrow(() -> new AuthorizationException("유효하지 않은 유저입니다."));
    }

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public void deleteMember(LoginMember loginMember) {
        Member member = findById(loginMember.getId());
        member.delete("unknown@dropthecode.co.kr", "탈퇴한 사용자", "https://static.thenounproject.com/png/994628-200.png");
        save(member);

        teacherLanguageService.deleteAllWithTeacher(member.getTeacherProfile());
        teacherSkillService.deleteAllWithTeacher(member.getTeacherProfile());
    }
}
