package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.domain.Feedback;
import com.wootech.dropthecode.dto.request.FeedbackSearchCondition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackRepositoryCustom {
    Page<Feedback> findAll(FeedbackSearchCondition condition, Pageable pageable);
}
