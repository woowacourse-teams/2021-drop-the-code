package com.wootech.dropthecode.service;

import java.util.Optional;

import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.dto.request.ReviewCreateRequest;
import com.wootech.dropthecode.exception.DropTheCodeException;
import com.wootech.dropthecode.repository.MemberRepository;
import com.wootech.dropthecode.repository.ReviewRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public ReviewService(ReviewRepository reviewRepository, MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
    }

    public Review create(ReviewCreateRequest reviewCreateRequest) {
        Optional<Member> teacher = memberRepository.findById(reviewCreateRequest.getTeacherId());
        Optional<Member> student = memberRepository.findById(reviewCreateRequest.getStudentId());

        validateMemberExists(teacher, student);

        Review review = new Review
                (
                        teacher.get(), student.get(),
                        reviewCreateRequest.getTitle(), reviewCreateRequest.getContent(),
                        reviewCreateRequest.getPrUrl(), Progress.ON_GOING
                );
        return reviewRepository.save(review);
    }

    private void validateMemberExists(Optional<Member> teacher, Optional<Member> student) {
        if (!teacher.isPresent() || !student.isPresent()) {
            throw new DropTheCodeException("존재하지 않는 회원이 포함되어 있습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
