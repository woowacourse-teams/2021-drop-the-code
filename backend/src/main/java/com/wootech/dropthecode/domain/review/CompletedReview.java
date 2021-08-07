package com.wootech.dropthecode.domain.review;

import com.wootech.dropthecode.domain.Progress;

public class CompletedReview extends ProgressReview {

    public CompletedReview(Review review) {
        super(review);
    }

    @Override
    protected void validateSelfProgress(Review review) {
        review.validateReviewProgressIsTeacherCompleted();
    }

    public void finish(Long memberId) {
        review.validateAuthorityOfStudent(memberId);
        review.setProgress(Progress.FINISHED);
    }
}
