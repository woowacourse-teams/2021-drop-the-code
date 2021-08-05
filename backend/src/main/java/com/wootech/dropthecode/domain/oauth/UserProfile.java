package com.wootech.dropthecode.domain.oauth;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfile {
    private final String oauthId;
    private final String email;
    private final String name;
    private final String imageUrl;
    private final String githubUrl;

    @Builder
    public UserProfile(String oauthId, String email, String name, String imageUrl, String githubUrl) {
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.githubUrl = githubUrl;
    }

    public Member toMember() {
        return Member.builder()
                     .oauthId(oauthId)
                     .email(email)
                     .name(name)
                     .imageUrl(imageUrl)
                     .githubUrl(githubUrl)
                     .role(Role.STUDENT)
                     .build();
    }
}
