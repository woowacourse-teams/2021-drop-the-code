package com.wootech.dropthecode.config.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberProfileResponse {
    private String name;
    private String email;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    public MemberProfileResponse() {
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

    @Override
    public String toString() {
        return "MemberProfileResponse{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
