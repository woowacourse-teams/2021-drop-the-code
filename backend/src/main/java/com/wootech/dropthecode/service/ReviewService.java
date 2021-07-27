package com.wootech.dropthecode.service;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.dto.request.ReviewCreateRequest;
import com.wootech.dropthecode.repository.ReviewRepository;

import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberService memberService;

    public ReviewService(ReviewRepository reviewRepository, MemberService memberService) {
        this.reviewRepository = reviewRepository;
        this.memberService = memberService;
    }

    public Review create(ReviewCreateRequest reviewCreateRequest) {
        Member teacher = memberService.findById(reviewCreateRequest.getTeacherId());
        Member student = memberService.findById(reviewCreateRequest.getStudentId());
        Review review = new Review
                (
                        teacher, student,
                        reviewCreateRequest.getTitle(), reviewCreateRequest.getContent(),
                        reviewCreateRequest.getPrUrl(), Progress.ON_GOING
                );
        return reviewRepository.save(review);
    }
}
