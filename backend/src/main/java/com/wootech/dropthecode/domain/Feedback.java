package com.wootech.dropthecode.domain;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Feedback(Long id, Integer star, String comment, Review review) {
        this.id = id;
        this.star = star;
        this.comment = comment;
        this.review = review;
    }
}
