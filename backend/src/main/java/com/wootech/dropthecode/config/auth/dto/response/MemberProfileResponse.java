package com.wootech.dropthecode.config.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;

public class MemberProfileResponse {
    private Long id;
    private String name;
    private String email;
    @JsonProperty("avatar_url")
    private String avatarUrl;

    public MemberProfileResponse() {
    }

    public Member toMember() {
        return new Member(id, email, name, avatarUrl, Role.STUDENT, null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
