package com.wootech.dropthecode.builder;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.TeacherProfile;

public class TeacherProfileBuilder {

    private TeacherProfileBuilder() {
    }

    public static TeacherProfile dummyTeacherProfile(String title, String content, Integer career, Member member) {
        return TeacherProfile.builder()
                             .title(title)
                             .content(content)
                             .career(career)
                             .member(member)
                             .build();
    }
}
