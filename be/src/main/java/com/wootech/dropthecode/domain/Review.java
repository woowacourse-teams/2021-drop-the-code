package com.wootech.dropthecode.domain;

import javax.persistence.*;

@Entity
public class Review extends BaseEntity {
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_review_to_teacher"))
    @ManyToOne
    private Member teacher;

    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_review_to_student"))
    @ManyToOne
    private Member student;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String prUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private final Progress progress = Progress.ON_GOING;

    protected Review() {
    }
}
