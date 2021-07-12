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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final Role role = Role.STUDENT;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_member_to_teacherProfile"))
    private TeacherProfile teacherProfile;

    protected Member() {
    }
}
