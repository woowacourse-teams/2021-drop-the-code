package com.wootech.dropthecode.service;

import com.wootech.dropthecode.domain.Feedback;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.dto.request.FeedbackRequest;
import com.wootech.dropthecode.repository.FeedbackRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Transactional
    public Feedback create(Review review, FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback(review, feedbackRequest.getStar(), feedbackRequest.getComment());
        return feedbackRepository.save(feedback);
    }
}
