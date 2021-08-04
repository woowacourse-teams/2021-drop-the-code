package com.wootech.dropthecode.domain;

import javax.persistence.*;

@Entity
public class Feedback {
    @Id
    private Long id;

    @Column(nullable = false)
    private Integer star;

    @Lob
    @Column(nullable = false)
    private String comment;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", unique = true, foreignKey = @ForeignKey(name = "fk_feedback_to_review"))
    private Review review;

    protected Feedback() {
    }

    public Feedback(Review review, Integer star, String comment) {
        this.review = review;
        this.star = star;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Integer getStar() {
        return star;
    }

    public String getComment() {
        return comment;
    }

    public Review getReview() {
        return review;
    }
}
