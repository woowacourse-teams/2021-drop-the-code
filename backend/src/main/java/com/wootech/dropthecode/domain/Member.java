package com.wootech.dropthecode.domain;

import java.util.List;
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

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private TeacherProfile teacherProfile;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Review> reviewsAsTeacher;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Review> reviewsAsStudent;

    protected Member() {
    }

    public Member(String oauthId, String name, String email, String imageUrl, Role role, TeacherProfile teacherProfile) {
        this(null, oauthId, name, email, imageUrl, role, teacherProfile);
    }

    public Member(Long id, String oauthId, String name, String email, String imageUrl, Role role, TeacherProfile teacherProfile) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.teacherProfile = teacherProfile;
    }

    public Member(String oauthId, String email, String name, String imageUrl, Role role) {
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.role = role;
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

    public List<Review> getReviewsAsTeacher() {
        return reviewsAsTeacher;
    }

    public List<Review> getReviewsAsStudent() {
        return reviewsAsStudent;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean hasRole(Role role) {
        return this.role == role;
    }
}
