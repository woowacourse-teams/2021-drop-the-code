package com.wootech.dropthecode.dto.response;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;

public class MemberResponse {
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
     * 로그인 한 유저의 Role
     */
    private final Role role;

    public MemberResponse(String name, String email, String imageUrl, Role role) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getName(), member.getEmail(), member.getImageUrl(), member.getRole());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Role getRole() {
        return role;
    }
}
