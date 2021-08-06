package com.wootech.dropthecode.domain.review;

import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Review;

public class OnGoingReview extends ProgressReview {

    public OnGoingReview(Review review) {
        super(review);
        review.validateReviewProgressIsOnGoing();
    }

    public void complete(Long memberId) {
        review.validateAuthorityOfTeacher(memberId);
        review.setProgress(Progress.TEACHER_COMPLETED);
        review.updateElapsedTime();
    }
}
