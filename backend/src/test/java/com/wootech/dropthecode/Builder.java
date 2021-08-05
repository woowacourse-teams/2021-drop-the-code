package com.wootech.dropthecode;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.domain.TeacherProfile;

public class Builder {
    public static Member dummyMember(String oauthId, String email, String name, String imageUrl, String githubUrl, Role role) {
        return Member.builder()
                     .oauthId(oauthId)
                     .email(email)
                     .name(name)
                     .imageUrl(imageUrl)
                     .githubUrl(githubUrl)
                     .role(role)
                     .build();
    }

    public static Member dummyMember(Long id, String oauthId, String email, String name, String imageUrl, String githubUrl, Role role, TeacherProfile teacherProfile) {
        return Member.builder()
                     .id(id)
                     .oauthId(oauthId)
                     .email(email)
                     .name(name)
                     .imageUrl(imageUrl)
                     .githubUrl(githubUrl)
                     .role(role)
                     .teacherProfile(teacherProfile)
                     .build();
    }

    public static Member dummyMember(String email, String name, String imageUrl, Role role) {
        return Member.builder()
                     .email(email)
                     .name(name)
                     .imageUrl(imageUrl)
                     .role(role)
                     .build();
    }
}
