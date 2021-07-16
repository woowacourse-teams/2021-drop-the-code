package com.wootech.dropthecode.config.auth.domain;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;

public class UserProfile {
    private String email;
    private String id;
    private String imageUrl;
    private String name;

    public UserProfile(String id, String name, String email, String imageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public Member toMember() {
        return new Member(id, name, email, imageUrl, Role.STUDENT, null);
    }

    public String getId() {
        return id;
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
