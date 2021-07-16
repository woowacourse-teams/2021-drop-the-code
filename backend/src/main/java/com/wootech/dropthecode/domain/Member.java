package com.wootech.dropthecode.domain;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity {
    private String oauthId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_member_to_teacherProfile"))
    private TeacherProfile teacherProfile;

    protected Member() {
    }

    public Member(String oauthId, String name, String email, String imageUrl, Role role, TeacherProfile teacherProfile) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.teacherProfile = teacherProfile;
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Role getRole() {
        return role;
    }

    public TeacherProfile getTeacherProfile() {
        return teacherProfile;
    }
}
