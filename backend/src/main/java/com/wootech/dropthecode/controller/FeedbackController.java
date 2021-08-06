package com.wootech.dropthecode.controller;

import com.wootech.dropthecode.dto.request.FeedbackSearchCondition;
import com.wootech.dropthecode.dto.response.FeedbackPaginationResponse;
import com.wootech.dropthecode.service.FeedbackService;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * @title Feedback 목록 조회
     */
    @GetMapping
    public ResponseEntity<FeedbackPaginationResponse> findFeedbacks(@ModelAttribute FeedbackSearchCondition condition, Pageable pageable) {
        return ResponseEntity.ok(feedbackService.findAll(condition, pageable));
    }
}
