package com.wootech.dropthecode.domain.review;

import lombok.Getter;

@Getter
public abstract class ProgressReview {

    protected final Review review;

    protected ProgressReview(Review review) {
        validateSelfProgress(review);
        this.review = review;
    }

    protected abstract void validateSelfProgress(Review review);
}
