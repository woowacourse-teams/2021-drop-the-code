package com.wootech.dropthecode.domain;

import java.util.List;
import javax.persistence.*;

@Entity
public class Member extends BaseEntity {
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final Role role = Role.STUDENT;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private TeacherProfile teacherProfile;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Review> reviewsAsTeacher;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Review> reviewsAsStudent;

    protected Member() {
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
}
