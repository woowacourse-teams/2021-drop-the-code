package com.wootech.dropthecode.domain.review;

import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Review;

public class CompletedReview extends ProgressReview {

    public CompletedReview(Review review) {
        super(review);
        review.validateReviewProgressIsTeacherCompleted();
    }

    public void finish(Long memberId) {
        review.validateAuthorityOfStudent(memberId);
        review.setProgress(Progress.FINISHED);
    }
}
