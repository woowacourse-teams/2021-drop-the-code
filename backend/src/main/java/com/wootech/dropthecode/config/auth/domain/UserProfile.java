package com.wootech.dropthecode.config.auth.domain;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;

public class UserProfile {
    private String oauthId;
    private String name;
    private String email;
    private String imageUrl;

    public UserProfile(String oauthId, String name, String email, String imageUrl) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public Member toMember() {
        return new Member(oauthId, name, email, imageUrl, Role.STUDENT, null);
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
}
