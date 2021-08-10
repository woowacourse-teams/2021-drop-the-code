package com.wootech.dropthecode.domain.review;

public abstract class ProgressReview {

    protected final Review review;

    protected ProgressReview(Review review) {
        validateSelfProgress(review);
        this.review = review;
    }

    protected Review getReview() {
        return review;
    }

    protected abstract void validateSelfProgress(Review review);
}
