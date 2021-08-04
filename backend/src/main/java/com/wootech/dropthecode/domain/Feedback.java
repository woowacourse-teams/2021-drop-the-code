package com.wootech.dropthecode.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Feedback extends BaseEntity {
    @Column(nullable = false)
    private Integer star;

    @Lob
    @Column(nullable = false)
    private String comment;

    protected Feedback() {
    }

    public Feedback(Integer star, String comment) {
        this.star = star;
        this.comment = comment;
    }

    public Integer getStar() {
        return star;
    }

    public String getComment() {
        return comment;
    }
}
