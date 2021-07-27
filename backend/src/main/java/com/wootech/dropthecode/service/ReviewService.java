package com.wootech.dropthecode.service;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.repository.ReviewRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final TeacherService teacherService;

    private final ReviewRepository reviewRepository;

    public ReviewService(TeacherService teacherService, ReviewRepository reviewRepository) {
        this.teacherService = teacherService;
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public Review findById(Long id) {
        return reviewRepository.findById(id)
                               .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
    }

    @Transactional
    public void updateToCompleteReview(LoginMember loginMember, Long id) {
        Review review = findById(id);
        review.validateMemberIdAsTeacher(loginMember.getId());

        review.completeProgress();
        review.updateElapsedTime();
        reviewRepository.save(review);

        TeacherProfile teacher = teacherService.findById(loginMember.getId());
        teacher.updateReviewCountAndTime(review.calculateElapsedTime());
        teacherService.save(teacher);
    }

    @Transactional
    public void updateToFinishReview(LoginMember loginMember, Long id) {
        Review review = findById(id);
        review.validateMemberIdAsStudent(loginMember.getId());

        review.finishProgress();
        reviewRepository.save(review);
    }
}
