package com.wootech.dropthecode.service;

import javax.persistence.EntityNotFoundException;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.dto.response.MemberResponse;
import com.wootech.dropthecode.repository.MemberRepository;
import com.wootech.dropthecode.repository.TeacherProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final TeacherLanguageService teacherLanguageService;
    private final TeacherSkillService teacherSkillService;
    private final MemberRepository memberRepository;
    private final TeacherProfileRepository teacherProfileRepository;

    public MemberService(TeacherLanguageService teacherLanguageService, TeacherSkillService teacherSkillService, MemberRepository memberRepository, TeacherProfileRepository teacherProfileRepository) {
        this.teacherLanguageService = teacherLanguageService;
        this.teacherSkillService = teacherSkillService;
        this.memberRepository = memberRepository;
        this.teacherProfileRepository = teacherProfileRepository;
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
                               .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 멤버입니다."));
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
        member.delete();
        save(member);

        /**
         * COMMENT
         * member.delete()에서 항상 Role을 DELETED로 바꿔 로직을 타지 않는 것 같음
         */
        //        if (member.hasRole(Role.TEACHER)) {
//            teacherProfileRepository.delete(member.getTeacherProfile());
//        }

        teacherLanguageService.deleteAllWithTeacher(member.getTeacherProfile());
        teacherSkillService.deleteAllWithTeacher(member.getTeacherProfile());
    }
}
