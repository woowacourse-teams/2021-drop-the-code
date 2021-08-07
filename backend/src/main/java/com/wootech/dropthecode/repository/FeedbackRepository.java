package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.domain.Feedback;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
