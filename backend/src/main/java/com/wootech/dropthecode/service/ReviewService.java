package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.dto.ReviewSummary;
import com.wootech.dropthecode.dto.request.ReviewCreateRequest;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;
import com.wootech.dropthecode.dto.response.ReviewResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.exception.NotFoundException;
import com.wootech.dropthecode.repository.ReviewRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final MemberService memberService;
    private final TeacherService teacherService;
    private final ReviewRepository reviewRepository;

    public ReviewService(MemberService memberService, TeacherService teacherService, ReviewRepository reviewRepository) {
        this.memberService = memberService;
        this.teacherService = teacherService;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Review create(ReviewCreateRequest reviewCreateRequest) {
        Member teacher = memberService.findById(reviewCreateRequest.getTeacherId());
        Member student = memberService.findById(reviewCreateRequest.getStudentId());
        Review review = new Review
                (
                        teacher, student,
                        reviewCreateRequest.getTitle(),
                        reviewCreateRequest.getContent(),
                        reviewCreateRequest.getPrUrl()
                );
        return reviewRepository.save(review);
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
    public void updateToFinishReview(LoginMember loginMember, Long id) {
        Review review = findById(id);
        review.finishProgress(loginMember.getId());
        reviewRepository.save(review);
    }
}
