package com.wootech.dropthecode.domain;

import javax.persistence.*;

import com.wootech.dropthecode.domain.review.Review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_notification_to_review"))
    private Review review;

    private String content;

    private String url;

    private boolean isRead;

    @Builder
    public Notification(Review review, String content, String url, boolean isRead) {
        this.review = review;
        this.content = content;
        this.url = url;
        this.isRead = isRead;
    }

}
