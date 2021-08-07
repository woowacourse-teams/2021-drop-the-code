package com.wootech.dropthecode.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import com.wootech.dropthecode.domain.review.Review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Member(Long id, String oauthId, String email, String name, String imageUrl, String githubUrl, Role role, TeacherProfile teacherProfile, List<Review> reviewsAsTeacher, List<Review> reviewsAsStudent, LocalDateTime createdAt) {
        super(id, createdAt);
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.githubUrl = githubUrl;
        this.role = role;
        this.teacherProfile = teacherProfile;
        this.reviewsAsTeacher = reviewsAsTeacher;
        this.reviewsAsStudent = reviewsAsStudent;
    }

    public boolean hasRole(Role role) {
        return this.role == role;
    }

    public boolean hasSameId(Long id) {
        return this.id.equals(id);
    }

    public Member update(String email, String name, String imageUrl) {
        if (this.role == Role.DELETED) {
            this.role = Role.STUDENT;
        }
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        return this;
    }

    public void delete(String email, String name, String imageUrl) {
        if (this.role == Role.TEACHER) {
            this.teacherProfile.deleteWithMember();
        }

        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.role = Role.DELETED;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
