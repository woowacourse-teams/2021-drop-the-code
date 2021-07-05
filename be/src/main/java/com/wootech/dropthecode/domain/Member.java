package com.wootech.dropthecode.domain;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity {
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private final boolean isTeacher = false;

    @JoinColumn(foreignKey = @ForeignKey(name = "fk_member_to_teacherProfile"))
    @OneToOne
    private TeacherProfile teacherProfile;

    protected Member() {
    }
}
