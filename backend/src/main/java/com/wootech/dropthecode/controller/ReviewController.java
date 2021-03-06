package com.wootech.dropthecode.controller;

import javax.validation.Valid;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.oauth.Login;
import com.wootech.dropthecode.dto.request.FeedbackRequest;
import com.wootech.dropthecode.dto.request.ReviewRequest;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;
import com.wootech.dropthecode.dto.response.ReviewResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.service.ReviewService;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * @title 리뷰 생성
     */
    @PostMapping
    public ResponseEntity<Void> create(@Login LoginMember loginMember, @RequestBody @Valid ReviewRequest reviewRequest) {
        Long id = reviewService.create(loginMember, reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .header("Location", "/reviews/" + id)
                             .build();
    }

    /**
     * @param id 로그인한 멤버 id
     * @title 내가 받은 리뷰 목록 조회
     */
    @GetMapping("/student/{id}")
    public ResponseEntity<ReviewsResponse> showStudentReviews(@Login LoginMember loginMember, @PathVariable Long id,
                                                              @ModelAttribute ReviewSearchCondition condition, Pageable pageable) {
        ReviewsResponse reviewsResponse = reviewService.findStudentReview(loginMember, id, condition, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(reviewsResponse);
    }

    /**
     * @param id 선생님 id
     * @title 내가 리뷰한 리뷰 목록 조회
     */
    @GetMapping("/teacher/{id}")
    public ResponseEntity<ReviewsResponse> showTeacherReviews(@PathVariable Long id,
                                                              @ModelAttribute ReviewSearchCondition condition, Pageable pageable) {
        ReviewsResponse reviewsResponse = reviewService.findTeacherReview(id, condition, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(reviewsResponse);
    }

    /**
     * @param id 리뷰 id
     * @title 리뷰 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> showReviewDetail(@PathVariable Long id) {
        ReviewResponse reviewResponse = reviewService.findReviewSummaryById(id);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(reviewResponse);
    }


    /**
     * @param id 리뷰 id
     * @title 리뷰 상태 업데이트(PENDING -> DENIED)
     */
    @PatchMapping("/{id}/deny")
    public ResponseEntity<Void> denyReview(@Login LoginMember loginMember, @PathVariable Long id) {
        reviewService.denyReview(loginMember, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * @param id 리뷰 id
     * @title 리뷰 상태 업데이트(PENDING -> ON_GOING)
     */
    @PatchMapping("/{id}/accept")
    public ResponseEntity<Void> acceptReview(@Login LoginMember loginMember, @PathVariable Long id) {
        reviewService.acceptReview(loginMember, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @param id 리뷰 id
     * @title 리뷰 상태 업데이트(ON_GOING -> TEACHER_COMPLETE)
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> updateToCompleteReview(@Login LoginMember loginMember, @PathVariable Long id) {
        reviewService.updateToCompleteReview(loginMember, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @param id 리뷰 id
     * @title 리뷰 상태 업데이트(TEACHER_COMPLETE -> FINISHED)
     */
    @PatchMapping("/{id}/finish")
    public ResponseEntity<Void> updateToFinishReview(@Login LoginMember loginMember, @PathVariable Long id, @RequestBody @Valid FeedbackRequest feedbackRequest) {
        reviewService.updateToFinishReview(loginMember, id, feedbackRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @param id 리뷰 id
     * @title 리뷰 내용 수정
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateReview(@Login LoginMember loginMember, @PathVariable Long id, @RequestBody @Valid ReviewRequest request) {
        reviewService.updateReview(loginMember, id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @param id 리뷰 id
     * @title Pending 상태의 리뷰 요청을 취소
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReviewRequest(@Login LoginMember loginMember, @PathVariable Long id) {
        reviewService.cancelRequest(loginMember, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
