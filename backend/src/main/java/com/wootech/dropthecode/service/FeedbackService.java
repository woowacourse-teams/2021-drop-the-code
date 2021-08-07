package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.Feedback;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.dto.request.FeedbackRequest;
import com.wootech.dropthecode.dto.request.FeedbackSearchCondition;
import com.wootech.dropthecode.dto.response.FeedbackPaginationResponse;
import com.wootech.dropthecode.dto.response.FeedbackResponse;
import com.wootech.dropthecode.repository.FeedbackRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Feedback feedback = Feedback.builder()
                                    .review(review)
                                    .star(feedbackRequest.getStar())
                                    .comment(feedbackRequest.getComment())
                                    .build();
        return feedbackRepository.save(feedback);
    }

    @Transactional(readOnly = true)
    public FeedbackPaginationResponse findAll(FeedbackSearchCondition condition, Pageable pageable) {
        Page<FeedbackResponse> feedbackResponses = feedbackRepository.findAll(condition, pageable);

        return FeedbackPaginationResponse.builder()
                                  .feedbacks(feedbackResponses.getContent())
                                  .pageCount(feedbackResponses.getTotalPages())
                                  .build();
    }

    private FeedbackResponse feedbackToResponse(Feedback feedback) {
        return FeedbackResponse.builder()
                               .id(feedback.getId())
                               .star(feedback.getStar())
                               .comment(feedback.getComment())
                               .build();
    }
}
