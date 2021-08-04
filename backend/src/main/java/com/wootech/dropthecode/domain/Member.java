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

    private String githubUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private TeacherProfile teacherProfile;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviewsAsTeacher;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviewsAsStudent;

    protected Member() {
    }

    public Member(String email, String name, String imageUrl, Role role) {
        this(null, null, email, name, imageUrl, null, role, null);
    }

    public Member(String oauthId, String email, String name, String imageUrl, String githubUrl, Role role) {
        this(null, oauthId, email, name, imageUrl, githubUrl, role, null);
    }

    public Member(Long id, String oauthId, String email, String name, String imageUrl, String githubUrl, Role role, TeacherProfile teacherProfile) {
        this.id = id;
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.githubUrl = githubUrl;
        this.role = role;
        this.teacherProfile = teacherProfile;
    }

    public boolean hasRole(Role role) {
        return this.role == role;
    }

    public boolean hasSameId(Long id) {
        return this.id.equals(id);
    }

    public Member update(String email, String name, String imageUrl) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        return this;
    }

    public void delete(String email, String name, String imageUrl) {
        if(this.role == Role.TEACHER) {
            this.teacherProfile.deleteWithMember();
        }

        this.email =email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.role = Role.DELETED;
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

    public String getGithubUrl() {
        return githubUrl;
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
}
