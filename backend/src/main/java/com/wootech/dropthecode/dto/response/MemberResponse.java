package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {
    /**
     * 로그인 한 유저 id
     */
    private final Long id;

    /**
     * 로그인 한 유저 이름
     */
    private final String name;

    /**
     * 로그인 한 유저 이메일
     */
    private final String email;

    /**
     * 로그인 한 유저 이미지 url
     */
    private final String imageUrl;

    /**
     * 로그인 한 유저 github url
     */
    private final String githubUrl;

    /**
     * 로그인 한 유저의 Role
     */
    private final Role role;

    @Builder
    public MemberResponse(Long id, String name, String email, String imageUrl, String githubUrl, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.githubUrl = githubUrl;
        this.role = role;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail(), member.getImageUrl(), member.getGithubUrl(), member
                .getRole());
    }
}
