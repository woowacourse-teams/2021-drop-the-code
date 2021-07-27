package com.wootech.dropthecode.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.dto.request.ReviewCreateRequest;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;
import com.wootech.dropthecode.dto.response.ProfileResponse;
import com.wootech.dropthecode.dto.response.ReviewResponse;
import com.wootech.dropthecode.dto.response.ReviewsResponse;
import com.wootech.dropthecode.service.ReviewService;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<Void> create(@RequestBody @Valid ReviewCreateRequest reviewCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .header("Location", "/reviews/1")
                             .build();
    }

    /**
     * @title 내가 받은 리뷰 목록 조회
     */
    @GetMapping("/student/{id}")
    public ResponseEntity<ReviewsResponse> showStudentReviews(@PathVariable Long id, @ModelAttribute ReviewSearchCondition condition, Pageable pageable) {
        ReviewsResponse reviewsResponse = reviewService.findStudentReview(id, condition, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(reviewsResponse);
    }

    /**
     * @param id 선생님 id
     * @title 내가 리뷰한 리뷰 목록 조회
     */
    @GetMapping("/teacher/{id}")
    public ResponseEntity<ReviewsResponse> showTeacherReviews(@PathVariable Long id) {
        ProfileResponse firstTeacher = new ProfileResponse(1L, "user1", "image1");
        ProfileResponse firstStudent = new ProfileResponse(2L, "user2", "image2");

        ProfileResponse secondTeacher = new ProfileResponse(1L, "user1", "image1");
        ProfileResponse secondStudent = new ProfileResponse(3L, "user3", "image3");

        ReviewResponse firstReviewResponse = new ReviewResponse(1L, "title1", "content1", Progress.ON_GOING,
                firstTeacher, firstStudent, "prUrl1", LocalDateTime.now());
        ReviewResponse secondReviewResponse = new ReviewResponse(2L, "title2", "content2", Progress.ON_GOING,
                secondTeacher, secondStudent, "prUrl2", LocalDateTime.now());

        List<ReviewResponse> data = new ArrayList<>();
        data.add(firstReviewResponse);
        data.add(secondReviewResponse);

        ReviewsResponse reviewsResponse = new ReviewsResponse(data, 0);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(reviewsResponse);
    }

    /**
     * @param id 리뷰 id
     * @title 리뷰 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> showReviewDetail(@PathVariable Long id) {
        ProfileResponse firstTeacher = new ProfileResponse(1L, "user1", "image1");
        ProfileResponse firstStudent = new ProfileResponse(2L, "user2", "image2");

        ReviewResponse reviewResponse = new ReviewResponse(1L, "title1", "content1", Progress.ON_GOING,
                firstTeacher, firstStudent, "prUrl1", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.OK)
                             .body(reviewResponse);
    }

    /**
     * @param id 리뷰 id
     * @title 리뷰 상태 업데이트
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> changeProgress(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
