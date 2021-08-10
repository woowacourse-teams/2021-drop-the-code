package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {
    /**
     * 로그인 한 유저 id
     */
    private Long id;

    /**
     * 로그인 한 유저 이름
     */
    private String name;

    /**
     * 로그인 한 유저 이메일
     */
    private String email;

    /**
     * 로그인 한 유저 이미지 url
     */
    private String imageUrl;

    /**
     * 로그인 한 유저 github url
     */
    private String githubUrl;

    /**
     * 로그인 한 유저의 Role
     */
    private Role role;

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
