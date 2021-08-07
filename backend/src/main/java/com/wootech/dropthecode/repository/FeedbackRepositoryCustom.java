package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.dto.request.FeedbackSearchCondition;
import com.wootech.dropthecode.dto.response.FeedbackResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackRepositoryCustom {
    Page<FeedbackResponse> findAll(FeedbackSearchCondition condition, Pageable pageable);
}
