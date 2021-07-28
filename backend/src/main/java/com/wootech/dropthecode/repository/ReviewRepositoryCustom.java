package com.wootech.dropthecode.repository;

import java.util.Optional;

import com.wootech.dropthecode.dto.ReviewSummary;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<ReviewSummary> searchPageByStudentId(Long id, ReviewSearchCondition condition, Pageable pageable);

    Page<ReviewSummary> searchPageByTeacherId(Long id, ReviewSearchCondition condition, Pageable pageable);

    Optional<ReviewSummary> findByReviewId(Long id);
}
