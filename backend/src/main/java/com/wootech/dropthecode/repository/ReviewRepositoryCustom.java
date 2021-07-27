package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.dto.ReviewSummary;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<ReviewSummary> searchPageByStudentId(Long id, ReviewSearchCondition condition, Pageable pageable);
}
