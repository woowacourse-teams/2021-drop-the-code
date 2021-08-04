package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.Feedback;
import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.dto.ReviewSummary;
import com.wootech.dropthecode.dto.request.FeedbackRequest;
import com.wootech.dropthecode.dto.request.ReviewRequest;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;
import com.wootech.dropthecode.dto.response.ReviewResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.exception.NotFoundException;
import com.wootech.dropthecode.exception.ReviewException;
import com.wootech.dropthecode.repository.ReviewRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final MemberService memberService;
    private final TeacherService teacherService;
    private final FeedbackService feedbackService;
    private final ReviewRepository reviewRepository;

    public ReviewService(MemberService memberService, TeacherService teacherService, FeedbackService feedbackService, ReviewRepository reviewRepository) {
        this.memberService = memberService;
        this.teacherService = teacherService;
        this.feedbackService = feedbackService;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Long create(ReviewRequest reviewRequest) {
        Member teacher = memberService.findById(reviewRequest.getTeacherId());
        Member student = memberService.findById(reviewRequest.getStudentId());
        Review review = new Review
                (
                        teacher, student,
                        reviewRequest.getTitle(),
                        reviewRequest.getContent(),
                        reviewRequest.getPrUrl()
                );
        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    @Transactional(readOnly = true)
    public Review findById(Long id) {
        return reviewRepository.findById(id)
                               .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
    }

    @Transactional(readOnly = true)
    public ReviewsResponse findStudentReview(Long id, ReviewSearchCondition condition, Pageable pageable) {
        Page<ReviewSummary> pageReviews = reviewRepository.searchPageByStudentId(id, condition, pageable);

        List<ReviewResponse> reviews = pageReviews.stream()
                                                  .map(ReviewResponse::from)
                                                  .collect(Collectors.toList());
        return new ReviewsResponse(reviews, pageReviews.getTotalPages());
    }

    @Transactional(readOnly = true)
    public ReviewsResponse findTeacherReview(Long id, ReviewSearchCondition condition, Pageable pageable) {
        Page<ReviewSummary> pageReviews = reviewRepository.searchPageByTeacherId(id, condition, pageable);

        List<ReviewResponse> reviews = pageReviews.stream()
                                                  .map(ReviewResponse::from)
                                                  .collect(Collectors.toList());
        return new ReviewsResponse(reviews, pageReviews.getTotalPages());
    }

    @Transactional(readOnly = true)
    public ReviewResponse findReviewSummaryById(Long id) {
        ReviewSummary review = reviewRepository.findByReviewId(id)
                                               .orElseThrow(() -> new NotFoundException("존재하지 않는 리뷰입니다."));
        return ReviewResponse.from(review);
    }

    @Transactional
    public void updateToCompleteReview(LoginMember loginMember, Long id) {
        Review review = findById(id);
        review.completeProgress(loginMember.getId());
        reviewRepository.save(review);

        teacherService.updateAverageReviewTime(loginMember.getId(), review.calculateElapsedTime());
    }

    @Transactional
    public void updateToFinishReview(LoginMember loginMember, Long id, FeedbackRequest feedbackRequest) {
        Review review = findById(id);
        Feedback feedback = feedbackService.create(review, feedbackRequest);
        review.finishProgress(loginMember.getId(), feedback);
        reviewRepository.save(review);
    }
  
    @Transactional
    public void updateReview(LoginMember loginMember, Long id, ReviewRequest request) {
        Review review = findById(id);
        review.update(loginMember.getId(), request.getTitle(), request.getContent(), request.getPrUrl());
    }
  
    @Transactional
    public void cancelRequest(LoginMember loginMember, Long id) {
        Review review = findById(id);
        review.validatesOwnerByLoginId(loginMember.getId());
        if (!review.isPending()) {
            throw new ReviewException("취소할 수 없는 리뷰입니다!");
        }
        reviewRepository.delete(review);
    }
}
