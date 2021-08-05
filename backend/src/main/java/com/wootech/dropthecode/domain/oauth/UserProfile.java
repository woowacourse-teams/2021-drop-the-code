package com.wootech.dropthecode.domain.oauth;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;

public class UserProfile {
    private final String oauthId;
    private final String email;
    private final String name;
    private final String imageUrl;
    private final String githubUrl;

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

    public String getOauthId() {
        return oauthId;
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

    public String getGithubUrl() {
        return githubUrl;
    }
}
