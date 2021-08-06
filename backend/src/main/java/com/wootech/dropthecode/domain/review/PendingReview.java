package com.wootech.dropthecode.domain.review;

import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Review;

public class PendingReview extends ProgressReview {

    public PendingReview(Review review) {
        super(review);
        review.validateReviewProgressIsPending();
    }

    public void cancel(Long memberId) {
        review.validateAuthorityOfStudent(memberId);
    }

    public void accept(Long memberId) {
        review.validateAuthorityOfTeacher(memberId);
        review.setProgress(Progress.ON_GOING);
    }

    public void deny(Long memberId) {
        review.validateAuthorityOfTeacher(memberId);
        review.setProgress(Progress.DENIED);
    }
}
